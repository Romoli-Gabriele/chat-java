package it.meucci;
import java.io.*;
import java.net.*;
import java.util.Vector;

import javax.swing.JOptionPane;

public class client {
    String nomeServer = "localhost";
    int portaServer = 6789;
    Socket socket;
    BufferedReader tastiera;
    String messaggio = "";
    String StringReply;
    DataInputStream in;
    DataOutputStream out;
    String Nome;
    String destinatario;
    gui Grafica;
    InputControl controllo;
    boolean amministratore = false;
    String change;
    Vector<String> lUser = new Vector<String>();


    protected Socket connetti() {
        try {
            change = "nome già utilizzato";
            tastiera = new BufferedReader(new InputStreamReader(System.in)); // creazione Buffer
            socket = new Socket(nomeServer, portaServer); // creazione nuovo Socket
            out = new DataOutputStream(socket.getOutputStream()); // gestione input e output
            in = new DataInputStream(socket.getInputStream());
            while (change.equals("nome già utilizzato")) {
                Nome=JOptionPane.showInputDialog("inserisci nome");
                out.writeBytes(Nome + '\n');
                change = in.readLine();
            }
            if (change.equals("Utenti collegati: Sei il primo utente ad accedere, sei amministratore del gruppo")) {
                lUser.add(Nome);
                amministratore = true;
            }else{
                String[] nU = change.split("Utenti collegati: ");
                nU = nU[1].split(", ");
                for(int i =0; i < nU.length;i++ ){
                    lUser.add(nU[i]);
                }
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
        controllo = new InputControl(in, this); // Creazione thread controllo chiusura da remoto e stampa messaggi in arrivo
        controllo.start();
        return socket;
    }

    public void comunica() throws IOException {
        try {
                out.writeBytes(destinatario + '\n');//invio destinatario
                Grafica.TextArea.append(Nome+"> "+messaggio+"\n");
                out.writeBytes(messaggio + '\n');//invio messaggio
        } catch (Exception e) {
            socket.close();
            System.exit(1);
        }
    }
}
