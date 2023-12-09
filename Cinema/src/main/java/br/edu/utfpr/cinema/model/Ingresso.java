package br.edu.utfpr.cinema.model;

import java.io.Serializable;
import java.util.UUID;

public class Ingresso implements Serializable {
    
    private Filme filme;
    private Sala sala;
    private String fileira;
    private String cadeira;
    private String horario;
    private double preco;
    
    private String transactionId;
    
    public Ingresso() {
        this.transactionId = UUID.randomUUID().toString();
        this.setPreco(10);
    }

    // getters e setters

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public String getFileira() {
        return fileira;
    }

    public void setFileira(String fileira) {
        this.fileira = fileira;
    }

    public String getCadeira() {
        return cadeira;
    }

    public void setCadeira(String cadeira) {
        this.cadeira = cadeira;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getTransactionId() {
        return transactionId;
    }
}