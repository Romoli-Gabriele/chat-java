package it.meucci;

import java.net.*;
import java.io.*;

public class threadClose extends Thread {

    DataInputStream in;
    client cli;
    String reply;

    public threadClose(DataInputStream in, client cli) {
        this.in = in;
        this.cli = cli;
    }

    public void run() {
        for (;;) {
            try {
                reply = in.readLine();//Lettura e controllo se bisogna chiudere il  client
                if (reply.equals("close")||cli.messaggio.equals("FINE")||cli.messaggio.equals("STOP")) {
                    System.out.println("Server disconnesso, chiusura client..");
                    System.exit(1);
                }else{
                    System.out.println(""+ '\n'+reply);
                }
            } catch (IOException e) {
                System.out.println("Errore lettura dal Server");
            }
        }
    }

}