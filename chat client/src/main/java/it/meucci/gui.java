package it.meucci;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

public class gui extends JFrame implements ActionListener{
    //chat 
    public Container c=new Container();
    public static final String titolo="Chat Java";
    public JPanel p=new JPanel();
    public final JTextField messaggio=new JTextField();
    public final int larghezza=800;
    public final int altezza=600;
    public final JTextArea TextArea=new JTextArea();
    public final JScrollPane scrollpane=new JScrollPane(TextArea);
    public final JButton invio=new JButton("Invio");
    public final JButton abbandona=new JButton("Abbandona");
    public final JButton chiudiChat=new JButton("Chiudi Chat");
    public final JLabel Destinatario=new JLabel("Destinatario:");
    public final JLabel Messaggio=new JLabel("Messaggio:");
    public client CLI;
    public final JComboBox<String> destinatario=new JComboBox<String>(/*CLI*/);

    public gui(client CLI){
        super(titolo);
        this.CLI = CLI;
        c=this.getContentPane();
        p.setLayout(null);

        p.add(scrollpane);
            scrollpane.setBounds(70,10,540,400);
        p.add(destinatario);
            p.add(Destinatario);
            Destinatario.setBounds(70,410,200,30);
            destinatario.setBounds(160,410,300,30);
        p.add(messaggio);
            p.add(Messaggio);
            Messaggio.setBounds(70,460,200,30);
            messaggio.setBounds(160,440,300,70);
        p.add(invio);
            invio.setBounds(460,410,150,50);
            invio.addActionListener(this);
        p.add(abbandona);
            abbandona.setBounds(460,460,150,50);
            abbandona.addActionListener(this);
        if(CLI.amministratore){
        p.add(chiudiChat);
            chiudiChat.setBounds(460,510,150,50);
            chiudiChat.addActionListener(this);
        }
        c.add(p);
        scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(larghezza,altezza);
        this.setVisible(true);
        TextArea.append(CLI.change+"\n");
    }
    public void nuovoAmministratore(){
        p.add(chiudiChat);
            chiudiChat.setBounds(460,510,150,50);
            chiudiChat.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent arg0) {

        if(arg0.getSource().equals(invio)){
 /*           CLI.destinatario=this.destinatario.getText();
            destinatario.setText("");*/
            CLI.destinatario=this.destinatario.getSelectedItem().toString();
            CLI.messaggio=this.messaggio.getText();
            messaggio.setText("");
            try {
                CLI.comunica();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(arg0.getSource().equals(abbandona)){
            try {
                CLI.out.writeBytes("fine\n");
                TextArea.append("Abbandono il gruppo..");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(arg0.getSource().equals(chiudiChat)){
            try {
                CLI.out.writeBytes("stop\n");
                TextArea.append("Chiudo la chat e abbandono il gruppo..");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}