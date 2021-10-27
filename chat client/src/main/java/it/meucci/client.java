package cli.client;

import java.io.*;
import java.net.*;

public class client {
    String nomeServer = "localhost";
    int portaServer = 6789;
    Socket socket;
    BufferedReader tastiera;
    String StringUser = " ";
    String StringReply;
    DataInputStream in;
    DataOutputStream out;

    protected Socket connetti() {
        try {
            tastiera = new BufferedReader(new InputStreamReader(System.in)); // creazione Buffer
            socket = new Socket(nomeServer, portaServer); // creazione nuovo Socket
            out = new DataOutputStream(socket.getOutputStream()); // gestione input e output
            in = new DataInputStream(socket.getInputStream());
        } catch (Exception e) {
            System.err.println("Errore creazione Socket o Buffer");
            System.exit(1);
        }
        try {
            System.out.println(InetAddress.getLocalHost());// Stampa ind IP client
        } catch (Exception e) {
            System.err.println("Ipossibile trovare IP");
        }
        return socket;
    }

    public void comunica() throws IOException {
        threadClose controllo = new threadClose(in, this); // Creazione thread controllo chiusura da remoto
        controllo.start();
        try {
            for (;;) {
                System.out.println("4 Inserisci stringa da modificare: " + '\n');
                StringUser = tastiera.readLine();//Lettura linea dal Buffer
                System.out.println("5 Invio stringa al server e attendo...");
                out.writeBytes(StringUser + '\n');//Invio stringa al server

            }
        } catch (Exception e) {
            socket.close();
            System.exit(1);
        }
    }
}
