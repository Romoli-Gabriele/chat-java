package it.meucci;

import java.io.IOException;

public class AppCLI 
{
    public static void main( String[] args ) throws IOException
    {
        client client1 = new client();
        client1.connetti();
        client1.comunica();
    }
}
