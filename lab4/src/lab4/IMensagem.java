/**
 * Laboratorio 4  
 * Autores: Bruno Uhlmann Marcato e Thomas Oliveira Rocha Sampaio Silva
 * Ultima atualizacao: 11/10/2023
 */
package lab4;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface IMensagem extends Remote {
    
    public Mensagem enviar(Mensagem mensagem) throws RemoteException;
    
}
