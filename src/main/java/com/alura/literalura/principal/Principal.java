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

import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private Scanner scanner = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConversorDeDados conversorDeDados = new ConversorDeDados();
    private final String ENDERECO = "https://gutendex.com/books?search=";

    @Autowired
    LivroRepository livroRepository;

    @Autowired
    AutorRepository autorRepository;

    public Principal (LivroRepository livroRepository, AutorRepository autorRepository){
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void menu(){
        int opcao = -1;

        while (opcao != 0) {
            String menu = """
                    
                    ------------------------------------------------------
                                        NOVA CONSULTA
                    ------------------------------------------------------
                    ESCOLHA UMA OPÇÃO
                    
                    [1] -> Cadastrar um livro pelo título.
                    [2] -> Buscar um livro cadastrado pelo título.
                    [3] -> Listar livros cadastrados.
                    [4] -> Listar autores cadastrados.
                    [5] -> Listar autores vivos em determinado ano.
                    [6] -> Listar livros em determinado idioma.
                                        
                    [0] -> Sair""";

            System.out.println(menu);
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao){
                case 1:
                    cadastrarNovoLivro();
                    break;

                case 2:
                    buscarLivroCadastrado();
                    break;


//                case 3:
//                    listarLivrosCadastrados();
//                    break;
//
//                case 4:
//                    listarAutoresCadastrados();
//                    break;
//
//                case 5:
//                    listarAutoresPorAnoNascimento();
//                    break;
//
//                case 6:
//                    listarLivrosPorIdioma();
//                    break;

                case 0:
                    System.out.println("Obrigada por utilizar o LiterAlura!");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

        }
    }


    private void cadastrarNovoLivro() {
        LivroDTO livroDTO = getResultsApi().livros().get(0);
        AutorDTO autorDTO = livroDTO.autores().get(0);
        Autor autor = autorRepository.findByNome(autorDTO.nome())
                .orElseGet(() -> autorRepository.save(new Autor(autorDTO)));

        Livro livro = new Livro(livroDTO);
        livro.setAutor(autor);
        livroRepository.save(livro);

    }

    private ResultsDTO getResultsApi(){
        System.out.println("Digite o título do livro que deseja cadastrar.");
        String tituloLivro = scanner.nextLine().replace(" ", "%20");
        System.out.println(ENDERECO + tituloLivro);
        var json = consumoApi.obterDadosApi(ENDERECO + tituloLivro);
        ResultsDTO results = conversorDeDados.obterDadosConvertidos(json, ResultsDTO.class);
        return results;
    }

    private void buscarLivroCadastrado(){
        System.out.println("Digite o título do livro para busca.");
        String titulo = scanner.nextLine();

        livroRepository.findByTitulo(titulo)
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("Não há livros cadastrados com esse título.")
                );




    }


}
