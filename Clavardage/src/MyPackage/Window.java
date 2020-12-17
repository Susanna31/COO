package MyPackage;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;
import java.util.Set;

import javax.swing.*;
 
public class Window extends JFrame implements WindowListener{
	
	private JPanel pan = new JPanel();
    private JButton clavBouton = new JButton("Ouverture Clavardage");
    private JButton nickBouton = new JButton("Choix du pseudo");
    private JButton changeNick = new JButton("Changement du pseudo");
    private JButton confirmNick = new JButton("Valider Changement");
    private JTextField jtf = new JTextField("Nickname");
    private JTextField jtf2 = new JTextField("Change Nickname");
    private JLabel jl = new JLabel("");
    private JLabel jl2 = new JLabel("");
    private Utilisateur user; 
    private UDPConnect udpc;
    private TCPConnect tcpc;
    private Boolean test = false;
	
    public Window(Utilisateur u) throws UnknownHostException, SocketException{
    	
    	this.user = u;
    	this.udpc = new UDPConnect(user);
    	this.tcpc = new TCPConnect(user);
	    this.setTitle("Application de clavardage");
	    this.setSize(550, 200);
	    this.setLocationRelativeTo(null);
	    this.addWindowListener(this);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   
	    nickBouton.addActionListener(new NicknameListener());
	    clavBouton.addActionListener(new OpenDiscussion());
	    changeNick.addActionListener(new ChangeNicknameListener());
	    confirmNick.addActionListener(new confirmNickListener());
	    
	    this.setContentPane(pan);
	    Font police = new Font("Arial", Font.BOLD, 14);
	    jtf.setFont(police);
	    jtf.setPreferredSize(new Dimension(150,30));
	    jtf2.setFont(police);
	    jtf2.setPreferredSize(new Dimension(150,30));
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
					udpc.sendEcho(user.get_nickname());
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
    
    class ChangeNicknameListener implements ActionListener{
    	
		@Override
		public void actionPerformed(ActionEvent e) {
			pan.add(jtf2);
			pan.add(confirmNick);
			pan.add(jl2);
			}
		}
    
    class confirmNickListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			jtf2.setVisible(true);
			confirmNick.setVisible(true);
			jl2.setVisible(true);
			if(udpc.sendIfUsed(jtf2.getText())) {
				jl2.setText("Pseudo déjà utilisé");
			}
			else {
				try {
					user.set_nickname(jtf2.getText());
					udpc.sendEcho(user.get_nickname());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			jl2.setText("");
			jtf2.setVisible(false);
			confirmNick.setVisible(false);
			jl2.setVisible(false);
		}
    	
    }

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		try {
			udpc.sendEcho("Disconnect");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void windowClosed(WindowEvent e) {
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


