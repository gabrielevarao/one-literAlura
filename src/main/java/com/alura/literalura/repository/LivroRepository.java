package com.alura.literalura.repository;

import com.alura.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    Optional<Livro> findByTituloIgnoreCaseContaining(String titulo);

    @Query("""
    SELECT l.titulo
    FROM Livro l
    JOIN l.autor a
    WHERE a.nome ILIKE %:nomeAutor%
    """)
    List<String> titulosLivroPorAutor(String nomeAutor);

    @Query("""
    SELECT l
    FROM Livro l
    WHERE l.idioma LIKE :idioma
    """)
    List<Livro> livrosPorIdioma(String idioma);

}
