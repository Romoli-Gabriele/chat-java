package it.meucci;

/**
 * Hello world!
 *
 */
public class AppSRV 
{
    public static void main( String[] args )
    {
        MultiSrv topServer = new MultiSrv();
        topServer.start("nome già utilizzato");
    }
}
