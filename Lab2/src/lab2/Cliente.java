package lab2;

/**
 * Laboratorio 2 de Sistemas Distribuidos
 * 
 * Autor: Bruno Uhlmann Marcato e Thomas Oliveira Rocha Sampaio Silva
 * Ultima atualizacao: 01/09/2023
 */

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Cliente {
    
    private static Socket socket;
    private static DataInputStream entrada;
    private static DataOutputStream saida;
    
    private int porta=1025;
    
    public void iniciar(){
    	System.out.println("Cliente iniciado na porta: "+porta);
    	
    	try {
            
            socket = new Socket("127.0.0.1", porta);
            
            entrada = new DataInputStream(socket.getInputStream());
            saida = new DataOutputStream(socket.getOutputStream());
            
            //Recebe do usuario algum valor
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            
            System.out.println("Digite:\n\n\"write\" para escrever uma nova fortuna\n\"read\" para ler uma fortuna aleatoria");
            
            String opcao = br.readLine();
            String args = "";
            
            if(opcao.equals("write")) {
            	System.out.print("Digite a fortuna: ");
            	args = br.readLine();
            }
            
            //formatacao da opcao e args para json
            String json_opcao = "{"
            		+ "\"method\": \"" + opcao + "\","
            		+ "\"args\": [\"" + args + "\"]"
            		+ "}";
            
            
            
            //O valor eh enviado ao servidor
            saida.writeUTF(json_opcao);
            
            //Recebe-se o resultado do servidor
            String resultado = entrada.readUTF();
            
            //Mostra o resultado na tela
            System.out.println(resultado);
            
            br.close();
            socket.close();
            
        } catch(Exception e) {
        	e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        new Cliente().iniciar();
    }
    
}
