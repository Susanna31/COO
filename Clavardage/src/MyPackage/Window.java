package MyPackage;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;
import java.util.Set;

import javax.swing.*;
 
public class Window extends JFrame{
	
	private JPanel pan = new JPanel();
    private JButton clavBouton = new JButton("Ouverture Clavardage");
    private JButton nickBouton = new JButton("Choix du pseudo");
    private JButton changeNick = new JButton("Changement du pseudo");
    private JTextField jtf = new JTextField("Nickname");
    private JLabel jl = new JLabel("");
    private Utilisateur user; 
    private UDPConnect udpc;
    private TCPConnect tcpc;
    private Boolean test = false;
	
    public Window(Utilisateur u) throws UnknownHostException, SocketException{
    	
    	this.user = u;
    	this.udpc = new UDPConnect(user);
    	this.tcpc = new TCPConnect(user);
	    this.setTitle("Application de clavardage");
	    this.setSize(900, 650);
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   
	    nickBouton.addActionListener(new NicknameListener());
	    clavBouton.addActionListener(new OpenDiscussion());
	    
	    this.setContentPane(pan);
	    Font police = new Font("Arial", Font.BOLD, 14);
	    jtf.setFont(police);
	    jtf.setPreferredSize(new Dimension(150,30));
	    pan.add(jtf);	
	    pan.add(nickBouton);
	    pan.add(jl);
	    
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
					user.set_nickname(jtf.getText());
					jl.setText("Le pseudo choisi est : " +  jtf.getText());
					nickBouton.setVisible(false);
					jtf.setVisible(false);
					pan.add(clavBouton);
					pan.add(changeNick);
				}

			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}
    }
    
    class OpenDiscussion implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			try {
				udpc.sendEcho("Refresh");
				new WindowUserList(udpc.get_Table(), user);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
    	}
	}
}

