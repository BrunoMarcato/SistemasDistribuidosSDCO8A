package br.edu.utfpr.cinema.bean;

import br.edu.utfpr.cinema.model.Filme;
import br.edu.utfpr.cinema.model.Ingresso;
import br.edu.utfpr.cinema.model.Sala;
import br.edu.utfpr.cinema.webservice.CinemaWebService;
import javax.ejb.EJB;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;

@SessionScoped
@Named
public class CinemaBean implements Serializable {

    @EJB
    private CinemaWebService cinemaWebService;

    private List<Filme> filmes;
    private Filme filmeSelecionado;
    private Ingresso ingresso;
    Sala sala;
    Lock salaLock;

    @PostConstruct
    public void init() {
        filmes = cinemaWebService.listarFilmes();

        ingresso = new Ingresso();
    }

    public String verDetalhes(int filmeId) {
        this.setFilmeSelecionado(cinemaWebService.obterDetalhesFilme(filmeId));
        return "detalhes.xhtml?faces-redirect=true";
    }

    public String irParaCompra(int filmeId) {
        this.setFilmeSelecionado(cinemaWebService.obterDetalhesFilme(filmeId));

        //apenas para definir um valor padrão de sala
        if (!getSalasDisponiveis().isEmpty()) {
            this.ingresso.setSala(getSalasDisponiveis().get(0));
        }

        this.ingresso.setFileira("A");

        return "compra.xhtml?faces-redirect=true";
    }

    public String Comprar() {
        Sala sala = this.ingresso.getSala();
        Lock salaLock = sala.getSalaLock();

        if (salaLock.tryLock()) {
            try {
                System.out.println("Tranca adquirida");
                cinemaWebService.realizarCompra(this.filmeSelecionado, this.ingresso);
                return "confirmarCompra.xhtml?faces-redirect=true";
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            System.out.println("Não foi possivel adquirir a tranca");
            return "compraNaoFeita.xhtml?faces-redirect=true";
        }
    }

    public String confirmarCompra() {
        cinemaWebService.marcarIngressoComoIndisponivel(this.filmeSelecionado, this.ingresso);

        // Libera o lock da sala após a confirmação da compra
        salaLock.unlock();
        System.out.println("Lock da sala liberado");

        return this.voltar();
    }

    public String voltar() {
        return cinemaWebService.voltar();
    }

    public List<Sala> getSalasDisponiveis() {
        return cinemaWebService.obterSalasDisponiveisParaFilme(filmeSelecionado);
    }

    public List<String> getNomesSalasDisponiveis() {
        List<Sala> listaSalas = getSalasDisponiveis();
        List<String> listaNomes = new ArrayList<>();
        for (Sala sala : listaSalas) {
            listaNomes.add(sala.getNome());
        }

        return listaNomes;
    }

    public List<String> getNomeFileiras() {
        return ingresso.getSala().getNomesFileiras();
    }

    public List<String> getNomeCadeiras() {
        return ingresso.getSala().getNomeCadeiras(ingresso.getFileira());
    }

    // Métodos getter e setter
    public List<Filme> getFilmes() {
        return filmes;
    }

    public Filme getFilmeSelecionado() {
        return filmeSelecionado;
    }

    public void setFilmeSelecionado(Filme filmeSelecionado) {
        this.filmeSelecionado = filmeSelecionado;
    }

    public Ingresso getIngresso() {
        return ingresso;
    }

    public void setIngresso(Ingresso ingresso) {
        this.ingresso = ingresso;
    }
}
