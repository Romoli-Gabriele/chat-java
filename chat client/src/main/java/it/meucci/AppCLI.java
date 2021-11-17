package it.meucci;

import java.io.IOException;

public class AppCLI 
{
    public static void main( String[] args ) throws IOException
    {
        client client1 = new client();
        gui Interfaccia=new gui(client1);
        client1.connetti();
        client1.Grafica=Interfaccia;
        client1.controllo.Grafica=Interfaccia;
        client1.comunica();
    }
}
