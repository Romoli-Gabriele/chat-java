package it.meucci;

import java.io.*;

public class InputControl extends Thread {

    DataInputStream in;
    client cli;
    String reply;
    gui Grafica;

    public InputControl(DataInputStream in, client cli) {
        this.in = in;
        this.cli = cli;
    }

    public void run() {
        for (;;) {
            try {
                reply = in.readLine();// Lettura e controllo se bisogna chiudere il client
                if (reply.equals("close")) {
                    Grafica.TextArea.append("Abbandono il gruppo..\n");
                    System.exit(1);
                }else {
                    Grafica.TextArea.append(reply + '\n');
                } 
                if(reply.equals("G> Ora sei amministratore")){
                    cli.amministratore = true;
                    Grafica.revalidate();
                }
            } catch (IOException e) {
                Grafica.TextArea.append("Chiusura server...");
                System.exit(1);
            }
        }
    }

}