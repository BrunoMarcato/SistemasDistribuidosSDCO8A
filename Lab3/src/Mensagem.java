/**
 * Laboratorio 3  
 * Autores: Bruno Uhlmann Marcato e Thomas Oliveira Rocha Sampaio Silva
 * Ultima atualizacao: 11/10/2023
 */

import java.io.Serializable;

public class Mensagem implements Serializable {
    
    String mensagem;
	
    //Cliente -> Servidor
    public Mensagem(String mensagem, String opcao){    	     
        setMensagem(mensagem, opcao);
    }
    
    //Servidor -> Cliente
    public Mensagem(String mensagem){
    	this.mensagem = new String(mensagem);
    }
    
    public String getMensagem(){
    	return this.mensagem;
    }
    
    public void setMensagem(String fortune, String opcao){
    	String mensagem="";
    	
    	switch(opcao){
            case "1" ->  {
                mensagem += "{"
                            + "\"method\": \"read\","
                            + "\"args\": [\"\"]"
                            + "}";
            }

            case "2" ->  {   		                		
                mensagem += "{"
                            + "\"method\": \"write\","
                            + "\"args\": [\"" + fortune + "\"]"
                            + "}";    
            }
    	}//fim switch
        
    	this.mensagem = mensagem;
    }
    
}