package it.meucci;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Vector;
public class MultiSrv {
    
    //HashMap<String,ServerThread> allThread = new HashMap();
    Vector<ServerThread> threadList = new Vector<ServerThread>(); //Creazione lista dei gestori dei client
    String nome;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;
    public void start() {
        try {
            ServerSocket server = new ServerSocket(6789); //Apertura porta
            String ind = InetAddress.getLocalHost().getHostAddress();//IP server
            System.out.println("Server partito in esecuzione..." + ind);
            for (;;) {
                Socket socket = server.accept();//accetta client e libera la porta
                inDalClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                outVersoClient = new DataOutputStream(socket.getOutputStream());
                String listaUtenti ="";
                nome = null;
                for(int i = 0; i<threadList.size(); i++){
                    listaUtenti = (listaUtenti+threadList.get(i).Nome+", ");
                }
                while(nome == null){
                    nome = inDalClient.readLine();
                    for(int i =0; i< threadList.size(); i++){
                        if(threadList.get(i).Nome.equals(nome)){
                            nome = null;
                            break;
                        }
                    }
                }
                
                boolean amministratore = false;
                if(threadList.size() == 0){
                    listaUtenti = "Sei il primo utente ad accedere, sei amministratore del gruppo";
                    amministratore = true;
                }
                listaUtenti = ("Utenti collegati: "+listaUtenti+'\n');
                outVersoClient.writeBytes(listaUtenti);//invia i nomi di tutti gli utenti collegati
                ServerThread serverthread = new ServerThread(nome, socket,server,this,amministratore);//creazione thread per gestire il client
                threadList.add(serverthread);//aggiungi il gestore appena creato alla lista
                serverthread.start();
            }
        } catch (Exception e) {
            System.out.println("Errore connessione client o creazione thread");
            System.exit(1);
        }
    }
    public void close(){
        for(int i = 0;i < threadList.size(); i++){
            threadList.get(i).close();//chiusura di tutti i socket 
        }
    }

    public void broadCast(String messaggio,String mittente){
        for(int i = 0;i < threadList.size(); i++){
            if(!threadList.get(i).Nome.equals(mittente)){
            try {
				threadList.get(i).scrivi(messaggio, mittente, true);
			} catch (IOException e) {
				System.out.println("Errore scrittura in broadcast");
                System.exit(1);
			    }
            }
        }
    }

}
