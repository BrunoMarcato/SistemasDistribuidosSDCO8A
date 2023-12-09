/**
 * Laboratorio 4  
 * Autores: Bruno Uhlmann Marcato e Thomas Oliveira Rocha Sampaio Silva
 * Ultima atualizacao: 11/10/2023
 */

package lab4;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;


public class ClienteRMI {
    
    public static void main(String[] args) {
                
        try {
            
            // Nova implementação para obter a lista de pares do arquivo Peer.java
            List<String> peers = Files.readAllLines(Paths.get("src\\lab4\\Peer.java"));
            System.out.println("Pares disponíveis:");
            for (String peer : peers) {
                System.out.println(peer);
            }
            
            Registry registro = LocateRegistry.getRegistry("127.0.0.1", 1099);
            
            IMensagem stub = (IMensagem) registro.lookup("servidorFortunes");  
                        
            String opcao="";
            Scanner leitura = new Scanner(System.in);
            do {
            	System.out.println("1) Read");
            	System.out.println("2) Write");
            	System.out.println("x) Exit");
            	System.out.print(">> ");
            	opcao = leitura.next();
                
            	switch(opcao){
            	case "1" ->  { //read
                   Mensagem mensagem = new Mensagem("", opcao);
                   Mensagem resposta = stub.enviar(mensagem);

                   // Adicione o código para exibir a fortuna recebida
                   System.out.println("\nFortuna lida do servidor:\n" + resposta.getMensagem() + "\n");
                }
                
            	case "2" ->  { //write
                        leitura.nextLine(); //consumir o '\n'

                        // Monta a mensagem
                        System.out.print("Adicionar fortuna: ");
                        String fortune = leitura.nextLine();

                        Mensagem mensagem = new Mensagem(fortune, opcao);
                        Mensagem resposta = stub.enviar(mensagem);

                        // Adicione o código para exibir a resposta do servidor
                        System.out.println("\nResposta do servidor:\n" + resposta.getMensagem() + "\n");
            	}
            	}
            } while(!opcao.equals("x"));
                        
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }
    
}

