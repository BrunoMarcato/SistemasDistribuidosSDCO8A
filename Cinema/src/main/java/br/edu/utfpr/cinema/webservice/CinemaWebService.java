package br.edu.utfpr.cinema.webservice;

import br.edu.utfpr.cinema.model.Filme;
import br.edu.utfpr.cinema.model.Ingresso;
import br.edu.utfpr.cinema.model.Sala;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface CinemaWebService {

    @WebMethod
    List<Filme> listarFilmes();

    @WebMethod
    Filme obterDetalhesFilme(int id);
    
    @WebMethod
    public void realizarCompra(Filme filme, Ingresso ingresso);
    
    @WebMethod
    public String voltar();
    
    @WebMethod
    public void marcarIngressoComoIndisponivel(Filme filme, Ingresso ingresso);
    
    @WebMethod
    public List<Sala> obterSalasDisponiveisParaFilme(Filme filme);
}
