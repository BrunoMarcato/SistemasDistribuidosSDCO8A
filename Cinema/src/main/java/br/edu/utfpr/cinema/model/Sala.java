package br.edu.utfpr.cinema.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Sala {
    
    private Lock salaLock;
    private Map<String, List<Cadeira>> fileiras;
    private Filme filme;
    private String nome;
    
    public Sala(String nome) {
        this.salaLock = new ReentrantLock();
        this.setNome(nome);

        fileiras = new HashMap<>();

        // Adiciona as letras do alfabeto e a lista de cadeiraIds associadas
        for (char letra = 'A'; letra <= 'Z'; letra++) {
            List<Cadeira> cadeiras = new ArrayList<>();
            for (int i = 1; i <= 15; i++) {
                cadeiras.add(new Cadeira(String.valueOf(i), true)); //Todos lugares vagos inicialmente
            }
            fileiras.put(String.valueOf(letra), cadeiras);
        }
    }

        public Lock getSalaLock() {
            return salaLock;
        }

        public List<String> getNomeCadeiras(String fileira) {
            List<String> nomesCadeiras = new ArrayList<>();

            if (fileiras.containsKey(fileira)) {
                List<Cadeira> cadeiras = fileiras.get(fileira);

                // Verifica se a lista de cadeiras não é nula antes de iterar sobre ela
                if (cadeiras != null) {
                    for (Cadeira cadeira : cadeiras) {
                        if (cadeira.isDisponivel()) {
                            nomesCadeiras.add(String.valueOf(cadeira.getCadeiraId()));
                        }
                    }
                }
            }

            return nomesCadeiras;
        }

        public void marcarCadeiraComoIndisponivel(String fileira, String cadeiraId) {
            List<Cadeira> cadeiras = fileiras.get(fileira);
            if (cadeiras != null) {
                for (Cadeira info : cadeiras) {
                    if (info.getCadeiraId().equals(cadeiraId)) {
                        info.setDisponivel(false);
                        break;
                    }
                }
            }
        }

        public Filme getFilme() {
            return filme;
        }

        public void setFilme(Filme filme) {
            this.filme = filme;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public List<String> getNomesFileiras() {

            Set<String> chaves = fileiras.keySet();

            List<String> listaNomeFileiras = new ArrayList<>(chaves);
            return listaNomeFileiras;
        }

        private static class Cadeira {

            private String cadeiraId;
            private boolean disponivel;

            public Cadeira(String cadeiraId, boolean disponivel) {
                this.cadeiraId = cadeiraId;
                this.disponivel = disponivel;
            }

            public String getCadeiraId() {
                return cadeiraId;
            }

            public boolean isDisponivel() {
                return disponivel;
            }

            public void setDisponivel(boolean disponivel) {
                this.disponivel = disponivel;
            }
        }
    }
