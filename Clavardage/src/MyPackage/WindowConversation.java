package MyPackage;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;
import java.util.Set;

import javax.swing.*;

public class WindowConversation extends JFrame implements WindowListener{

	private JPanel pan = new JPanel();
	private TCPConnect tcpc;
	private Utilisateur user1;
	private String nick_user2;
	private int port_u2;
	private JButton EnvoiMessage = new JButton("Envoyer");
	private JTextField jtf = new JTextField("Envoyer un message");
	private JScrollPane jsp;
	private JTextArea jta = new JTextArea(20,40);
	private JPanel jp = new JPanel();
	private JLabel jl = new JLabel("test");
	
	public WindowConversation(Utilisateur u, int port_user2, String nick_u2) {
		this.tcpc = new TCPConnect(u);
		this.user1 = u;
		this.nick_user2 = nick_u2;
		this.port_u2 = port_user2;
		System.out.println(port_user2);
		System.out.println(u.get_nickname());
		this.addWindowListener(this);
		
		EnvoiMessage.addActionListener(new envoiListener());
		
		this.setTitle("Conversation avec : " + nick_user2);
		this.setSize(500,500);
		
		this.setContentPane(pan);
		Font police = new Font("Arial", Font.PLAIN, 14);
	    jtf.setFont(police);
		jtf.setPreferredSize(new Dimension(350, 30));
		EnvoiMessage.setPreferredSize(new Dimension(125,30));
		
		jp.setPreferredSize(new Dimension(410,410));
		jsp = new JScrollPane(jp);
		jsp.setBounds(10, 10, 350, 350);
		
		pan.add(jsp);	
		pan.add(jtf);
		pan.add(EnvoiMessage);
		
		this.setVisible(true);
		//this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	public void envoi(String s) {
		jp.add(new JLabel(user1.get_nickname() + s));
		jp.updateUI();
	}
	
	public void recevoir(String s) {
		jp.add(new JLabel(nick_user2 + s));
		jp.updateUI();
	}

	class envoiListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			try {
				tcpc.envoiMsg(jtf.getText(), port_u2);
				envoi(jtf.getText());
				jtf.setText("Envoyer un message");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.out.println("La fenetre est ferm�e");
		user1.set_ConvState(port_u2, false);
	}

	@Override
	public void windowClosed(WindowEvent e) {
		System.out.println("La fenetre est ferm�e");
		user1.set_ConvState(port_u2, false);
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {	
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}
}
