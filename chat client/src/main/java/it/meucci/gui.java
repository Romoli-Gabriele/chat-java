package it.meucci;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

public class gui extends JFrame implements ActionListener{
    //chat 
    public Container c=new Container();
    public static final String titolo="Chat";
    public JPanel p=new JPanel();
    public final JTextField destinatario=new JTextField();
    public final JTextField messaggio=new JTextField();
    public final int larghezza=800;
    public final int altezza=600;
    public final JScrollPane scrollpane=new JScrollPane();
    public final JButton invio=new JButton("Invio");
    public final JLabel Destinatario=new JLabel("Destinatario:");
    public final JLabel Messaggio=new JLabel("Messaggio:");
    public String nome="";
    //richiesta nome
    public Container c1=new Container();
    public JPanel p1=new JPanel();

    public gui(){
        super(titolo);
        nome=JOptionPane.showInputDialog("inserisci nome");
        JOptionPane.showMessageDialog(null,nome);
        c=this.getContentPane();
        p.setLayout(null);

        p.add(scrollpane);
            scrollpane.setBounds(70,10,500,400);
        p.add(destinatario);
            p.add(Destinatario);
            Destinatario.setBounds(70,410,200,30);
            destinatario.setBounds(160,410,300,30);
        p.add(messaggio);
            p.add(Messaggio);
            Messaggio.setBounds(70,460,200,30);
            messaggio.setBounds(160,440,300,70);
        p.add(invio);
            invio.setBounds(460,440,110,70);
        c.add(p);
        scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(larghezza,altezza);
        this.setVisible(true);

        
    }


    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        if(arg0.getSource().equals(invio)){
            client.destinatario=this.destinatario.getText();
            client.messaggio=this.messaggio.getText();
            try {
                client.comunica();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
    }
}