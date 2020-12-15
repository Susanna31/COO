package MyPackage;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;
import java.util.Set;

import javax.swing.*;

public class WindowConversation extends JFrame{

	private JPanel pan = new JPanel();
	private TCPConnect tcpc;
	private Utilisateur user1;
	private int port_u2;
	private JButton EnvoiMessage = new JButton("Envoyer");
	private JTextField jtf = new JTextField("Envoyer un message");
	
	public WindowConversation(Utilisateur u, int port_user2, String nick_u2) {
		this.tcpc = new TCPConnect(u);
		this.user1 = u;
		this.port_u2 = port_user2;
		System.out.println(port_user2);
		System.out.println(u.get_nickname());
		
		EnvoiMessage.addActionListener(new envoiListener());
		
		this.setTitle("Conversation avec : " + nick_u2);
		this.setSize(500,500);
		
		this.setContentPane(pan);
		Font police = new Font("Arial", Font.PLAIN, 14);
	    jtf.setFont(police);
		jtf.setPreferredSize(new Dimension(350, 30));
		EnvoiMessage.setPreferredSize(new Dimension(125,30));
		
		pan.add(jtf);
		pan.add(EnvoiMessage);
		
		this.setVisible(true);
	}
	
	public WindowConversation(int port_user2, String nick_u2) {
		
	}

	class envoiListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			try {
				tcpc.envoiMsg(jtf.getText(), port_u2);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
