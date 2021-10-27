package it.meucci;

import java.net.*;
import java.io.*;

public class ServerThread extends Thread {
    ServerSocket server = null;
    Socket client = null;
    String StringRV = null;
    String StringMD = null;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;
    MultiSrv allThread;
    String Nome;

    public ServerThread(String Nome, Socket socket, ServerSocket server, MultiSrv gestore) {
        this.client = socket;
        this.server = server;
        this.allThread = gestore;
    }

    public void run() {
        try {
            comunica();
        } catch (Exception e) {
            System.out.println("Errore comunicazine");
        }

    }
    public void close(){
        try {
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
            if (StringRV == null || StringMD.equals("FINE") || StringMD.equals("STOP")) { //chiusura thread 
                outVersoClient.writeBytes(StringRV + " (=>server dedicato in chiusura..)" + '\n');
                System.out.println("Echo sul server in chiusura : " + StringRV);
                outVersoClient.close();
                inDalClient.close();
                client.close();
                break;
            } else {
                allThread.broadCast(StringRV, Nome);
            }
        }
        outVersoClient.close();
        inDalClient.close();
        System.out.println("9 Chiusura socket ..." + client);
        client.close();
        if (StringRV.equals("STOP")) {
            allThread.close();//chiama la chiusura di tutti i thread
            server.close();
            System.out.println("Server in chiusura");
            System.exit(1);
        }
    }
    public void scrivi(String messaggio, String mittente) throws IOException{
        outVersoClient.writeBytes(mittente+"@g"+messaggio+ '\n');
    }

}