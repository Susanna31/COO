package MyPackage;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.*;
 
public class Window extends JFrame{
	
	private JPanel pan = new JPanel();
    private UserClient bouton = new UserClient("Ouverture de session de clavardage");
    private JButton nickBouton = new JButton("Choix du pseudo");
    private JTextField jtf = new JTextField("Nickname");
    private Utilisateur user; 
    private UDPConnect udpc;
    private Boolean test = false;
	
    public Window(Utilisateur u) throws UnknownHostException, SocketException{
    	
    	this.user = u;
    	this.udpc = new UDPConnect(user);
	    this.setTitle("Application de clavardage");
	    this.setSize(900, 650);
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    pan.add(bouton);
	    nickBouton.addActionListener(new NicknameListener());
	    this.setContentPane(pan);
	    Font police = new Font("Arial", Font.BOLD, 14);
	    jtf.setFont(police);
	    jtf.setPreferredSize(new Dimension(150,30));
	    pan.add(jtf);	
	    pan.add(nickBouton);
	    
	    this.setVisible(true);
    }	
    
    class NicknameListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			System.out.println("Le pseudo choisi est " + jtf.getText());
			try {
				udpc.start_while();
				if (!test) {
					udpc.start_thread();
				}
				test = true;
				udpc.sendEcho("Connexion");

				if(udpc.sendIfUsed(jtf.getText())) {
					System.out.println("Ce pseudo est déjà prit par un autre user");
					udpc.stop_thread();
				}
				else {
					System.out.println("Le pseudo " + jtf.getText() + " est valide");
				}

			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}
    }
}