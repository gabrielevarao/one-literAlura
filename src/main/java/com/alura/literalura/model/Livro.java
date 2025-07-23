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

    public Livro(){}

    public Livro(LivroDTO livroDTO) {
        this.titulo = livroDTO.titulo();
        this.idioma = livroDTO.idiomas().isEmpty() ? null : livroDTO.idiomas().get(0);
        this.downloads = livroDTO.downloads();

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "\n-------------LIVRO------------------" +
                "\n-> Título: " + titulo +
                " \n-> Autor: " + autor.getNome() +
                " \n-> Idioma = " + idioma +
                " \n-> Número de downloads = " + downloads +
                "\n -----------------------------------";
    }
}
