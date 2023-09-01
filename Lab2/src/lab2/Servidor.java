package lab2;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Servidor {

	private static Socket socket;
	private static ServerSocket server;

	private static DataInputStream entrada;
	private static DataOutputStream saida;

	private int porta = 1025;
	
	public final static Path arquivo = Paths.get("fortune-br.txt");

	public void iniciar() {
		System.out.println("Servidor iniciado na porta: " + porta);
		try {

			// Criar porta de recepcao
			server = new ServerSocket(porta);
			socket = server.accept();  //Processo fica bloqueado, ah espera de conexoes

			// Criar os fluxos de entrada e saida
			entrada = new DataInputStream(socket.getInputStream());
			saida = new DataOutputStream(socket.getOutputStream());

			// Recebimento do json
			String json_opcao = entrada.readUTF();
			
			//processamento do json
			json_opcao = json_opcao.substring(1, json_opcao.length() - 1);
			String[] campos = json_opcao.split(",");
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
				String fortuna = readFortuna();
				saida.writeUTF("{\n"
						+ "\"result\": \"" + fortuna.trim() + "\""
						+ "\n}");
			} else if(method.equals("write")) {
				// Envio dos dados (resultado)
				if(args.endsWith("\\n")) {
					args = args.substring(0, args.length() - 2);
					args += "\n";
					escreverFortuna(args);
					saida.writeUTF("{\n"
							+ "\"result\": \"" + args + "\""
							+ "\n}");
				} else {
					saida.writeUTF("{"
							+ "\"result\": \"false\""
							+ "}");
				}
			}
			
			socket.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String readFortuna() {
		
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo.toString()))) {
            List<String> fortunes = new ArrayList<>();
            String line;
            StringBuilder currentFortune = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                if (line.trim().equals("%")) {
                    if (currentFortune.length() > 0) {
                        fortunes.add(currentFortune.toString());
                        currentFortune.setLength(0); // Limpa o buffer
                    }
                } else {
                    currentFortune.append(line).append("\n");
                }
            }

            if (!fortunes.isEmpty()) {
                String randomFortune = getFortunaAleatoria(fortunes);
                reader.close();
                return randomFortune;
            } else {
                System.out.println("O arquivo de fortunas está vazio.");
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return "";
	}
	
	private static String getFortunaAleatoria(List<String> fortunes) {
		Random random = new Random();
        int randomIndex = random.nextInt(fortunes.size());
        return fortunes.get(randomIndex);
    }
	
	private void escreverFortuna(String novaFortuna) {
        try (FileWriter writer = new FileWriter(arquivo.toString(), true)) {
            writer.write(novaFortuna + "%\n");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public static void main(String[] args) {

		new Servidor().iniciar();

	}
}