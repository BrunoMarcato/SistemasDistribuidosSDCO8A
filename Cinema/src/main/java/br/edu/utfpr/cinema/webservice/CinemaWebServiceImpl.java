package br.edu.utfpr.cinema.webservice;

import br.edu.utfpr.cinema.model.Filme;
import br.edu.utfpr.cinema.model.Ingresso;
import br.edu.utfpr.cinema.model.Sala;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import javax.ejb.Stateless;

@Stateless
public class CinemaWebServiceImpl implements CinemaWebService {

    private List<Filme> filmes;
    private List<Sala> salas;
    private static final ConcurrentHashMap<String, ReentrantLock> cadeiraLocks = new ConcurrentHashMap<>();

    public CinemaWebServiceImpl() {
        criarListaFilmes();

        salas = new ArrayList<>();

        int i = 1;
        for (Filme filme : filmes) {
            Sala sala = new Sala("Sala 0" + i);
            sala.setFilme(filme);
            salas.add(sala);
            i++;
        }
    }

    private void criarListaFilmes() {
        filmes = new ArrayList<>();

        filmes.add(new Filme(1, "Jogos Vorazes", "Num futuro distante, boa parte da população é controlada por um regime totalitário, "
                + "que relembra esse domínio realizando um evento anual - e mortal - entre os 12 distritos sob sua tutela.", "9h, 17h, 21h"));
        filmes.add(new Filme(2, "Filme 2", "Descrição do filme 2", "10h, 16h, 22h"));
        filmes.add(new Filme(3, "Filme 3", "Descrição do filme 3", "11h, 18h, 23h"));
    }

    @Override
    public List<Sala> obterSalasDisponiveisParaFilme(Filme filme) {
        List<Sala> salasDisponiveis = new ArrayList<>();
        for (Sala sala : salas) {
            // Verifica se a sala está associada ao filme
            if (sala.getFilme().getId() == filme.getId()) {
                salasDisponiveis.add(sala);
            }
        }
        return salasDisponiveis;
    }

    @Override
    public List<Filme> listarFilmes() {
        return filmes;
    }

    @Override
    public Filme obterDetalhesFilme(int id) {
        for (Filme filme : filmes) {
            if (filme.getId() == id) {
                return filme;
            }
        }
        return new Filme(999, "Não encontrado", "Não encontrado", "Não encontrado");
    }

    @Override
    public void realizarCompra(Filme filme, Ingresso ingresso) {
        ingresso.setFilme(filme);
    }

    @Override
    public String voltar() {
        return "index.xhtml?faces-redirect=true";
    }

    @Override
    public void marcarIngressoComoIndisponivel(Filme filme, Ingresso ingresso) {
        ingresso.getSala().marcarCadeiraComoIndisponivel(ingresso.getFileira(), ingresso.getCadeira());
    }
}