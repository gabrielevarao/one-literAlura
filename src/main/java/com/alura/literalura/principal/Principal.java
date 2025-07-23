package com.alura.literalura.principal;

import com.alura.literalura.dto.ResultsDTO;
import com.alura.literalura.model.Livro;
import com.alura.literalura.repository.LivroRepository;
import com.alura.literalura.service.ConsumoApi;
import com.alura.literalura.service.ConversorDeDados;

import java.util.Scanner;

public class Principal {

    private Scanner scanner = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConversorDeDados conversorDeDados = new ConversorDeDados();
    private final String ENDERECO = "gutendex.com/books?search=";


//    private void cadastroLivro {
//        ResultsDTO results = conversorDeDados.obterDadosConvertidos(json, ResultsDTO.class);
//        Livro livro = new Livro(results.results().get(0));
//        livroRepository.save(livro);
//    }


}
