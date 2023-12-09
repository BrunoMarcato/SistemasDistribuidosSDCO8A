/**
 * Laboratorio 4  
 * Autores: Bruno Uhlmann Marcato e Thomas Oliveira Rocha Sampaio Silva
 * Ultima atualizacao: 11/10/2023
 */
package lab4;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ServidorImpl implements IMensagem {
    
    private Principal p;
    private List<String> peers; // Adicione esta linha
    
    public ServidorImpl() {
        this.p = new Principal();
        
        try {
            // Nova implementação para obter a lista de pares do arquivo Peer.java
            this.peers = Files.readAllLines(Paths.get("src\\Peer.java"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //Cliente: invoca o metodo remoto 'enviar'
    //Servidor: invoca o metodo local 'enviar'
    @Override
    public Mensagem enviar(Mensagem mensagem) throws RemoteException {
        Mensagem resposta;
        
        try {
            
            System.out.println("Mensagem recebida: " + mensagem.getMensagem());
            resposta = new Mensagem(parserJSON(mensagem.getMensagem()));
                
        } catch (Exception e) {
            e.printStackTrace();
            resposta = new Mensagem("""
                                    {
                                    "result": "false"
                                    }""");
            
        }
        
        return resposta;
    }
    
    public String parserJSON(String json) throws Exception {
        String result="";
        
        //processamento do json
        json = json.substring(1, json.length() - 1);
        String[] campos = json.split(",");
        String method = null;
        String args = null;

        for(String campo: campos) {
                String[] partes = campo.split(":");
                String chave = partes[0];
                String valor = partes[1].trim();

                if(chave.equals("\"method\"")) {
                        method = valor.substring(1, valor.length() - 1);
                } else if(chave.equals("\"args\"")) {
                        args = valor.substring(2, valor.length() - 2);
                }
        }
        
        //executar o comando
        if(method.equals("read")) {
            String fortuna = this.p.read();
            result += "{\n"
                        + "\"result\": \"" + fortuna.trim() + "\""
                        + "\n}";
            
        } else if(method.equals("write")) {
            
            if(args.endsWith("\\n")) {
                args = args.substring(0, args.length() - 2);
                args += "\n";
                
                this.p.write(args);
                System.out.println(args);
                result += "{\n"
                            + "\"result\": \"" + args + "\""
                            + "\n}";
            } else {
                
            throw new Exception("Formatacao incorreta!");
            
            }
            
        }

        return result;
    }
    
    public void iniciar(){

        try {
            Registry servidorRegistro = LocateRegistry.createRegistry(1099);            
            IMensagem skeleton  = (IMensagem) UnicastRemoteObject.exportObject(this, 0); //0: sistema operacional indica a porta (porta anonima)
            servidorRegistro.rebind("servidorFortunes", skeleton);
            System.out.print("Servidor RMI: Aguardando conexoes...\n");

        } catch(Exception e) {
            e.printStackTrace();
        }        

    }
    
    public static void main(String[] args) {
        ServidorImpl servidor = new ServidorImpl();
        servidor.iniciar();
    }
}

