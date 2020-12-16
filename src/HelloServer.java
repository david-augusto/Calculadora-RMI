/**

 */
import java.rmi.*;
import java.util.Scanner;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class HelloServer implements Runnable {
    public Socket client;
    private static List<Socket> listaDeClientes = new ArrayList<Socket>();

    public HelloServer(Socket client){
        this.client = client;        
    }
    
    public static void main(String[] args)  throws IOException, NotBoundException {
        //Cria um socket na porta 12345
        ServerSocket servidor = new ServerSocket (12345);
        System.out.println("Porta 12345 aberta!");
        java.rmi.registry.LocateRegistry.createRegistry(1099);
        HelloImpl obj = new HelloImpl() {};
        Naming.rebind("HelloServer", obj);
        
        // Aguarda alguém se conectar. A execução do servidor
        // fica bloqueada na chamada do método accept da classe
        // ServerSocket. Quando alguém se conectar ao servidor, o
        // método desbloqueia e retorna com um objeto da classe
        // Socket, que é uma porta da comunicação.
        System.out.println("Aguardando conexão do cliente...");
        
        while (true) {
            Socket client = servidor.accept();

            listaDeClientes.add(client);
            // Cria uma thread do servidor para tratar a conexão
            HelloServer tratamento = new HelloServer(client);
            Thread t = new Thread(tratamento);
            // Inicia a thread para o cliente conectado
            t.start();
        }
    }
    
    /* A classe Thread, que foi instancia no servidor, implementa Runnable.
       Então você terá que implementar sua lógica de troca de mensagens dentro deste método 'run'.
    */
    public void run(){
        System.out.println("Nova conexao com o cliente " + this.client.getInetAddress().getHostAddress());    
        String str;
        try {
            InputStream i = this.client.getInputStream();// Objeto responsavel por coletar a entrada do cliente
            OutputStream o = this.client.getOutputStream(); // Objeto responsavel pelo envio para o cliente
            Hello obj2 = (Hello)Naming.lookup("rmi://localhost/HelloServer");
            //System.out.println(obj2.sayHello());
            do {
            	 //Cria HelloImpl
                
                byte[] line = new byte[100];
                i.read(line);
                str = new String(line);
                String[] separador = str.split(" "); //Quebra a string a cada "Espaco" e armazena cada palavra em uma posicao do vetor separador
                listaDeClientes.forEach(e -> {
                    try {
                    	String aux = null;
                    	byte[] l = new byte[100];
                        if("+".equals(separador[1])){ //separador[1] armazena o operador
                        	aux = String.valueOf(obj2.getsoma(Float.parseFloat(separador[0]),Float.parseFloat(separador[2]))) + "       "; //Mostra o resultado do metodo getsoma para os paramentros extraido do vetor
                        } else
                        	if("-".equals(separador[1])){ //separador[1] armazena o operador
                        		aux = String.valueOf(obj2.getsub(Float.parseFloat(separador[0]),Float.parseFloat(separador[2]))) + "       "; //Mostra o resultado do metodo getsub para os paramentros extraido do vetor
                        	} else
                        		if("/".equals(separador[1])){ //separador[1] armazena o operador
                        			aux = String.valueOf(obj2.getdiv(Float.parseFloat(separador[0]),Float.parseFloat(separador[2]))) + "       "; //Mostra o resultado do metodo getdiv para os paramentros extraido do vetor
                        		} else
                        			if("*".equals(separador[1])){ //separador[1] armazena o operador
                        				aux = String.valueOf(obj2.getmult(Float.parseFloat(separador[0]),Float.parseFloat(separador[2]))) + "       "; //Mostra o resultado do metodo getmult para os paramentros extraido do vetor
                        			}  else
                           				if("^".equals(separador[1])){ //separador[1] armazena o operador
                           					aux = String.valueOf(obj2.getpot(Float.parseFloat(separador[0]),Float.parseFloat(separador[2]))) + "       "; //Mostra o resultado do metodo getmult para os paramentros extraido do vetor
                           				}  else
                               				if("r".equals(separador[0])){ //separador[1] armazena o operador
                               					aux = String.valueOf(obj2.getraiz(Float.parseFloat(separador[1]))) + "       "; //Mostra o resultado do metodo getmult para os paramentros extraido do vetor
                               				}  else
                                   				if("%".equals(separador[1])){ //separador[1] armazena o operador
                                   					aux = String.valueOf(obj2.getporcent(Float.parseFloat(separador[0]),Float.parseFloat(separador[2]))) + "       "; //Mostra o resultado do metodo getmult para os paramentros extraido do vetor
                                   				}
                        l = aux.getBytes();
                        o.write(l);
                    } catch(Exception ex) {
                		System.out.println("HelloServer erro"+ ex.getMessage());
                	}
                });
            } while (!str.trim().equals("bye"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
