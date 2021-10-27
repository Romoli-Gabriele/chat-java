package it.meucci;

import java.net.*;
import java.io.*;

public class ServerThread extends Thread {
    ServerSocket server = null;
    Socket client = null;
    String StringRV = null;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;
    MultiSrv allThread;
    String Nome;

    public ServerThread(String Nome, Socket socket, ServerSocket server, MultiSrv gestore) {
        this.client = socket;
        this.server = server;
        this.allThread = gestore;
        this.Nome = Nome;
        System.out.println("Membro entrato nella chat: Benvenuto "+Nome);
    }

    public void run() {
        try {
            comunica();
        } catch (Exception e) {
            System.out.println("Errore comunicazine");
            System.exit(1);
        }

    }
    public void close(){
        try {
            System.out.println(Nome+" ha abbandonato il gruppo");
            outVersoClient.writeBytes("close");// invia segnale al client di chiudersi
            outVersoClient.close();
            inDalClient.close();
            client.close();
        } catch (IOException e) {
            System.out.println("Errore invio messaggio di chiusura");
        }
    }

    public void comunica() throws Exception {
        inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        outVersoClient = new DataOutputStream(client.getOutputStream());
        for (;;) {
            StringRV = inDalClient.readLine();//Lettura dal client
            
            
            if (StringRV.equals("fine")) { //chiusura thread 
                System.out.println(Nome+" ha abbandonato il gruppo");
                outVersoClient.close();
                inDalClient.close();
                client.close();
                break;
            } else {
                allThread.broadCast(StringRV, Nome);
            }
            if (StringRV.equals("stop")) {
            allThread.close();//chiama la chiusura di tutti i thread
            outVersoClient.close();
            inDalClient.close();
            client.close();
            server.close();
            System.out.println("Server in chiusura");
            System.exit(1);
            }
        }
        
        
    }
    public void scrivi(String messaggio, String mittente) throws IOException{
        outVersoClient.writeBytes(mittente+"@g>  "+messaggio+ '\n');
    }

}