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
	private Utilisateur user2;
	private JButton EnvoiMessage = new JButton("Envoyer");
	private JTextField jtf = new JTextField("Envoyer un message");
	
	public WindowConversation(Utilisateur u) {
		EnvoiMessage.addActionListener(new envoiListener());
		this.setTitle("Conversation avec : " + u.get_nickname());
		pan.add(jtf);
		this.setVisible(true);
	}

	class envoiListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String message = jtf.getText();
			try {
				tcpc.envoiMsg(message, user2.get_port());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
