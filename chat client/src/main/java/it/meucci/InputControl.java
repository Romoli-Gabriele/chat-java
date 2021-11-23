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
                } else {
                    Grafica.TextArea.append(reply + '\n');
                }
                if (reply.startsWith("G.@G> Membro entrato nella chat: Benvenuto ")) {
                    String[] newUser = reply.split("G.@G> Membro entrato nella chat: Benvenuto ");
                    cli.lUser.add(newUser[1]);
                    //Grafica.TextArea.append(newUser[1]);

                } else if (reply.endsWith(" ha abbandonato il gruppo")) {
                    String[] oldUser = reply.split(" ha abbandonato il gruppo");
                    oldUser = oldUser[0].split("G.@G> ");
                    cli.lUser.remove(oldUser[1]);
                    //Grafica.TextArea.append(oldUser[1]);

                } else if (reply.equals("G> Ora sei amministratore")) {
                    Grafica.nuovoAmministratore();
                    cli.amministratore = true;
                }
            } catch (IOException e) {
                Grafica.TextArea.append("Chiusura server...");
                System.exit(1);
            }
        }
    }

}