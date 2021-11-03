package it.meucci;

import java.io.IOException;

public class AppCLI 
{
    public static void main( String[] args ) throws IOException
    {
        client client1 = new client();
        gui gui=new gui();
        client1.connetti();
        client1.comunica();
    }
}
