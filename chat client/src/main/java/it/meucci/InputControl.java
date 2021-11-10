package it.meucci;

import java.io.*;

public class InputControl extends Thread {

    DataInputStream in;
    client cli;
    String reply;

    public InputControl(DataInputStream in, client cli) {
        this.in = in;
        this.cli = cli;
    }

    public void run() {
        for (;;) {
            try {
                reply = in.readLine();//Lettura e controllo se bisogna chiudere il  client
                if (reply.equals("close")||cli.messaggio.equals("fine")||cli.messaggio.equals("stop")) {
                    System.out.println("Abbandono il gruppo..");
                    System.exit(1);
                }else{
                    System.out.println(reply);
                }
            } catch (IOException e) {
                System.out.println("Chiusura del Server...");
                System.exit(1);
            }
        }
    }

}