package it.meucci;
import java.io.*;
import java.net.*;

import javax.swing.JOptionPane;

public class client {
    String nomeServer = "localhost";
    int portaServer = 6789;
    Socket socket;
    BufferedReader tastiera;
    String messaggio = " ";
    String StringReply;
    DataInputStream in;
    DataOutputStream out;
    String Nome;
    String destinatario;

    protected Socket connetti() {
        try {
            String change = "nome già utilizzato";
            tastiera = new BufferedReader(new InputStreamReader(System.in)); // creazione Buffer
            socket = new Socket(nomeServer, portaServer); // creazione nuovo Socket
            out = new DataOutputStream(socket.getOutputStream()); // gestione input e output
            in = new DataInputStream(socket.getInputStream());
            while (change.equals("nome già utilizzato")) {
                Nome=JOptionPane.showInputDialog("inserisci nome");
                out.writeBytes(Nome + '\n');
                change = in.readLine();
                System.out.println(change);
            }
        } catch (Exception e) {
            System.err.println("Errore creazione Socket o Buffer");
            System.exit(1);
        }
        try {
            System.out.println(InetAddress.getLocalHost());// Stampa ind IP client
        } catch (Exception e) {
            System.err.println("Ipossibile trovare IP");
        }
        InputControl controllo = new InputControl(in, this); // Creazione thread controllo chiusura da remoto
        controllo.start();
        return socket;
    }

    public void comunica() throws IOException {
        try {
                out.writeBytes(destinatario + '\n');//invio destinatario
                System.out.println(destinatario);
                out.writeBytes(messaggio + '\n');//invio messaggio
                System.out.println(messaggio);
                System.out.println("Messaggio inviato..."+'\n');
        } catch (Exception e) {
            socket.close();
            System.exit(1);
        }
    }
}
