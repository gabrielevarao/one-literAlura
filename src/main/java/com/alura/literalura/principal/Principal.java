package com.alura.literalura.principal;

import com.alura.literalura.dto.AutorDTO;
import com.alura.literalura.dto.LivroDTO;
import com.alura.literalura.dto.ResultsDTO;
import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Livro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LivroRepository;
import com.alura.literalura.service.ConsumoApi;
import com.alura.literalura.service.ConversorDeDados;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private Scanner scanner = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConversorDeDados conversorDeDados = new ConversorDeDados();
    private final String ENDERECO = "https://gutendex.com/books?search=";
    private final String MENU = """
                            
                            ------------------------------------------------------
                                                NOVA CONSULTA
                            ------------------------------------------------------
                            >>> ESCOLHA UMA OPÇÃO
                            
                            [1] -> Cadastrar um livro pelo título.
                            [2] -> Buscar um livro cadastrado pelo título.
                            [3] -> Consultar a lista dos livros cadastrados.
                            [4] -> Consultar a lista dos autores cadastrados.
                            [5] -> Buscar autores vivos em determinado ano.
                            [6] -> Buscar livros em determinado idioma.
                                                
                            [0] -> Sair""";
    private final String EXIT = """
                            ------------------------------------------------------
                                    Obrigada por utilizar o LiterAlura!
                            ------------------------------------------------------""";

    @Autowired
    LivroRepository livroRepository;

    @Autowired
    AutorRepository autorRepository;

    public Principal (LivroRepository livroRepository, AutorRepository autorRepository){
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void menu(){
        String opcao = "";

        while (!opcao.equals("0")) {

            System.out.println(MENU);
            opcao = scanner.nextLine();

            switch (opcao){
                case "1": cadastrarNovoLivro(); break;
                case "2": buscarLivroCadastrado(); break;
                case "3": listarLivrosCadastrados(); break;
                case "4": listarAutoresCadastrados(); break;
                case "5": listarAutoresVivosPorAno(); break;
                case "6": listarLivrosPorIdioma(); break;
                case "0": System.out.println(EXIT); break;

                default: System.out.println("Opção inválida.");
            }
        }
    }

    private void cadastrarNovoLivro() {
        System.out.println("\n>> Que livro deseja cadastrar? Digite o título abaixo.");
        String tituloLivro = scanner.nextLine();

        Optional<Livro> livroExistente = livroRepository.findByTituloIgnoreCaseContaining(tituloLivro);
        if (livroExistente.isPresent()) {
            System.out.println("\nAVISO: Esse livro já está cadastrado no banco.");
            return;
        }

        ResultsDTO results = getResultsApi(tituloLivro.replace(" ", "%20"));

        if (results == null){return;}

        LivroDTO livroDTO = results.livros().get(0);
        AutorDTO autorDTO = livroDTO.autores().get(0);

        Autor autor = autorRepository.findByNome(autorDTO.nome())
                .orElseGet(() -> autorRepository.save(new Autor(autorDTO)));

        Livro livro = new Livro(livroDTO);
        livro.setAutor(autor);
        livroRepository.save(livro);

        System.out.println("\n CADASTRO EFETUADO COM SUCESSO!\n" + livro);
    }

    private ResultsDTO getResultsApi(String tituloLivro) {
        var json = consumoApi.obterDadosApi(ENDERECO + tituloLivro);
        ResultsDTO resultsDTO = conversorDeDados.obterDadosConvertidos(json, ResultsDTO.class);

        if (resultsDTO.livros() == null || resultsDTO.livros().isEmpty()) {
            System.out.println("\nAVISO: Livro não encontrado.");
            return null;
        }
        return resultsDTO;
    }

    private void buscarLivroCadastrado() {
        System.out.println("\n>> Que livro deseja buscar? Digite o título abaixo.");
        String titulo = scanner.nextLine();

        livroRepository.findByTituloIgnoreCaseContaining(titulo)
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("\nAVISO: Não há livros cadastrados com esse título."));
    }

    private void listarLivrosCadastrados() {
        List<Livro> livrosCadastrados = livroRepository.findAll();
        if(livrosCadastrados.isEmpty()){
            System.out.println("\nAVISO: Não há livros cadastrados.");
        } else {
            System.out.println("\n------------------LIVROS CADASTRADOS------------------");
            livrosCadastrados.forEach(System.out::println);
        }
    }

    private void listarAutoresCadastrados() {
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()){
            System.out.println("\nAVISO: Não há autores cadastrados.");
        } else {
            System.out.println("\n------------------AUTORES CADASTRADOS------------------");
            toFormattedString(autores);
        }
    }

    private void listarAutoresVivosPorAno() {
        System.out.println("\nDigite o ano para a busca.");
        int anoLimite = scanner.nextInt();
        scanner.nextLine();

        List<Autor> autoresVivos = autorRepository.autoresVivosPorAno(anoLimite);
        if(autoresVivos.isEmpty()){
            System.out.println("\nAVISO: Não há autores cadastrados que estavam vivos em "+ anoLimite + ".");
        } else {
            toFormattedString(autoresVivos);
        }
    }

    private void listarLivrosPorIdioma(){
        System.out.println("""
                Quer buscar livros cadastrados em qual dos idiomas disponíveis? 
                Digite a sigla correspondente:
                
                [es] -> espanhol
                [en] -> inglês
                [fr] -> francês
                [pt] -> português""");
        String idioma = scanner.nextLine();
        List<Livro> livrosIdioma = livroRepository.livrosPorIdioma(idioma.toLowerCase());
        if(livrosIdioma.isEmpty()){
            System.out.println("\nAVISO: Não há livros cadastrados nesse idioma.");
        } else {
            livrosIdioma.forEach(System.out::println);
        }
    }

    private void toFormattedString(List<Autor> autores){
        autores.stream()
                .map(a -> a.toString().concat("\n-> Livro(s): ")
                        .concat(String.join(", ",
                                livroRepository.titulosLivroPorAutor(a.getNome())))
                        .concat("\n-------------------------------------"))
                .forEach(System.out::println);
    }

}
