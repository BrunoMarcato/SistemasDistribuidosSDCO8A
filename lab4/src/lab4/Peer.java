/**
 * Laboratorio 4  
 * Autores: Bruno Uhlmann Marcato e Thomas Oliveira Rocha Sampaio Silva
 * Ultima atualizacao: 11/10/2023
 */
package lab4;
public enum Peer {
    
    PEER1 {
        @Override
        public String getNome() {
            return "PEER1";
        }        
    },
    PEER2 {
        public String getNome() {
            return "PEER2";
        }        
    },
    PEER3 {
        public String getNome() {
            return "PEER3";
        }        
    };
    public String getNome(){
        return "NULO";
    }    
}