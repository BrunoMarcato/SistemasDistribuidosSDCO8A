package br.edu.utfpr.cinema.model;

import java.io.Serializable;
import java.util.Objects;

public class Filme implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String titulo;
    private String descricao;
    private String horarios;

    public Filme() {
    }

    public Filme(int id, String titulo, String descricao, String horarios) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.horarios = horarios;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Filme filme = (Filme) obj;
        return Objects.equals(titulo, filme.titulo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo);
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getHorarios() {
        return this.horarios;
    }

    public void setHorarios(String horarios) {
        this.horarios = horarios;
    }
    
    
}
