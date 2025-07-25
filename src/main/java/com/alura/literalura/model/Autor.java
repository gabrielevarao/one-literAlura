package com.alura.literalura.model;

import com.alura.literalura.dto.AutorDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Integer anoNascimento;
    private Integer anoFalecimento;

    @OneToMany(mappedBy = "autor")
    private List<Livro> livros = new ArrayList<>();

    public Autor (){}

    public Autor (AutorDTO autor){
        this.nome = autor.nome();
        this.anoNascimento = autor.anoNascimento();
        this.anoFalecimento = autor.anoFalecimento();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(Integer anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public Integer getAnoFalecimento() {
        return anoFalecimento;
    }

    public void setAnoFalecimento(Integer anoFalecimento) {
        this.anoFalecimento = anoFalecimento;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    @Override
    public String toString() {
        return "\n--------------AUTOR------------------" +
                " \n-> Autor = " + nome +
                " \n-> Ano de nascimento = " + anoNascimento +
                " \n-> Ano de falecimento = " + anoFalecimento;
    }
}
