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
    String destinatario; // destinatario messaggio
    boolean globale; // se il messaggio è indirizzato a tutti = true
    boolean amministratore;// true se l'utente ha il permesso di chiudere la chat di tutto il gruppo
    private boolean remove;

    public ServerThread(String Nome, Socket socket, ServerSocket server, MultiSrv gestore, boolean amministratore) {
        this.client = socket;
        this.server = server;
        this.allThread = gestore;
        this.Nome = Nome;
        this.amministratore = amministratore;
        allThread.broadCast("Membro entrato nella chat: Benvenuto " + Nome + '\n',"G");
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
            allThread.broadCast(Nome + " ha abbandonato il gruppo" + '\n', "G");
            remove = allThread.threadList.remove(this);
            outVersoClient.writeBytes("close\n");// invia segnale al client di chiudersi
            outVersoClient.close();
            inDalClient.close();
            client.close();
            this.stop();
        } catch (IOException e) {
            System.out.println("Errore di chiusura");
        }
    }

    public void comunica() throws Exception {
        inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        outVersoClient = new DataOutputStream(client.getOutputStream());
        for (;;) {
            destinatario = inDalClient.readLine();// Lettura destinatario o tipo messaggio
            if (destinatario.equals("fine")) { // chiusura thread
                if(amministratore){
                    allThread.newAdministrator();
                }
                this.close();
                break;
            }else if (destinatario.equals("stop") && amministratore) {
                allThread.close();
            }
            StringRV = inDalClient.readLine();// Lettura messaggio
            if (destinatario.equals("G")){
                allThread.broadCast(StringRV, Nome);
            } else {
                for (int i = 0; i < allThread.threadList.size(); i++) {
                    if (allThread.threadList.get(i).Nome.equals(destinatario)) {
                        allThread.threadList.get(i).scrivi(StringRV, Nome, globale);
                    }

                }
            }

        }

    }

    public void scrivi(String messaggio, String mittente, boolean globale) throws IOException {
        if (globale) {
            // System.out.println(mittente + ".@G> " + messaggio + '\n');
            outVersoClient.writeBytes(mittente + ".@G> " + messaggio + '\n');
        } else {
            // System.out.println(mittente + "> " + messaggio + '\n');
            outVersoClient.writeBytes(mittente + "> " + messaggio + '\n');
        }
    }

}