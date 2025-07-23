package com.alura.literalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record ResultsDTO(@JsonAlias("results") List<LivroDTO> livros) {
}
