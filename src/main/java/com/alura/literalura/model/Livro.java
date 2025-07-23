package com.alura.literalura.model;

import com.alura.literalura.dto.LivroDTO;
import jakarta.persistence.*;

@Entity
@Table(name="livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;
    private String idioma;
    private Integer downloads;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public void Livro(LivroDTO livro){
        this.titulo = livro.titulo();
//        this.autor = new Autor(livro.autores().getFirst());
//        this.idioma = livro.idiomas().getFirst();
        this.downloads = livro.downloads();

    }
}
