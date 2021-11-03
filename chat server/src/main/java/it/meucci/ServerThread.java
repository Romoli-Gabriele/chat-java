package it.meucci;

import java.net.*;
import java.util.concurrent.TimeUnit;
import java.io.*;

public class ServerThread extends Thread {
    ServerSocket server = null;
    Socket client = null;
    String StringRV = null;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;
    MultiSrv allThread;
    String Nome;
    String destinatario; // destinatario messaggio
    boolean globale; // se il messaggio è indirizzato a tutti = true

    public ServerThread(String Nome, Socket socket, ServerSocket server, MultiSrv gestore) {
        this.client = socket;
        this.server = server;
        this.allThread = gestore;
        this.Nome = Nome;
        System.out.println("Membro entrato nella chat: Benvenuto " + Nome);
    }

    public void run() {
        try {
            comunica();
        } catch (Exception e) {
            System.out.println("Errore comunicazine");
            System.exit(1);
        }

    }

    public void close() {
        try {
            System.out.println(Nome + " ha abbandonato il gruppo");
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
            destinatario = inDalClient.readLine();// Lettura destinatario
            //System.out.println("Invio messaggio a "+destinatario+'\n');
            StringRV = inDalClient.readLine();// Lettura messaggio
            //System.out.println("Il messaggio è "+StringRV+'\n');
            if (destinatario.equals("G")) {
                allThread.broadCast(StringRV, Nome);
            } else {
                for (int i = 0; i < allThread.threadList.size(); i++) {
                    if (allThread.threadList.get(i).Nome.equals(destinatario)) {
                        allThread.threadList.get(i).scrivi(StringRV, Nome, globale);
                    }

                }
            }
            if (StringRV.equals("fine")) { // chiusura thread
                System.out.println(Nome + " ha abbandonato il gruppo");
                outVersoClient.close();
                inDalClient.close();
                client.close();
                break;
            }
            if (StringRV.equals("stop")) {
                allThread.close();// chiama la chiusura di tutti i thread
                outVersoClient.close();
                inDalClient.close();
                client.close();
                server.close();
                System.out.println("Server in chiusura");
                System.exit(1);
            }
        }

    }

    public void scrivi(String messaggio, String mittente, boolean globale) throws IOException {
        if (globale) {
            //System.out.println(mittente + ".@G> " + messaggio + '\n');
            outVersoClient.writeBytes(mittente + ".@G> " + messaggio + '\n');
        } else{
            //System.out.println(mittente + "> " + messaggio + '\n');
            outVersoClient.writeBytes(mittente + "> " + messaggio + '\n');
        }
    }

}