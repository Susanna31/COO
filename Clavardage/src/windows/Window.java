package windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import connection.TCPConnect;
import connection.UDPConnect;
import observers.Observable;
import observers.Observer;
import other.Utilisateur;

 
public class Window extends JFrame implements WindowListener{
	
	private JPanel pan = new JPanel();
    private JButton clavBouton = new JButton("Ouverture Clavardage");
    private JButton nickBouton = new JButton("Choix du pseudo");
    private JButton changeNick = new JButton("Changement du pseudo");
    private JTextField jtf = new JTextField("Nickname");
    private JTextField jtf2 = new JTextField("Change Nickname");
    private JLabel jl = new JLabel("");
    private JLabel jl2 = new JLabel("");
    private Utilisateur user; 
    private UDPConnect udpc;
    private TCPConnect tcpc;
    private Boolean test = false;
    private JLabel labelPseudo = new JLabel("Entrez votre pseudo :", SwingConstants.LEFT);
	
    public Window(Utilisateur u) throws UnknownHostException, SocketException{
    	
    	this.user = u;
    	this.udpc = new UDPConnect(user,this);
    	this.tcpc = new TCPConnect(user,this);
	    this.setTitle("Application de clavardage");
	    this.setSize(550, 200);
	    this.setLocationRelativeTo(null);
	    this.addWindowListener(this);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   
	    nickBouton.addActionListener(new NicknameListener());
	    
	    this.setContentPane(pan);
	    Font police = new Font("Arial", Font.BOLD, 14);
	    jtf.setFont(police);
	    jtf.setPreferredSize(new Dimension(150,30));
	    jtf2.setFont(police);
	    jtf2.setPreferredSize(new Dimension(150,30));
	    
	    police = new Font("Arial", Font.BOLD, 14);
	    labelPseudo.setFont(police);
	    nickBouton.setPreferredSize(new Dimension(200,50));
	    clavBouton.setPreferredSize(new Dimension(200,50));
		changeNick.setPreferredSize(new Dimension(200,50));
		jtf.setPreferredSize(new Dimension(200,50));
		
	    //layout
	    JPanel pan2 = new JPanel();
	    pan2.setLayout(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.insets = new Insets(10,10,10,10);
	    gbc.gridheight = 1;
	    gbc.gridwidth = 1;
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.anchor = GridBagConstraints.WEST;
	    pan2.add(labelPseudo,gbc);
	    gbc.gridy = 1;
	    pan2.add(jtf, gbc);
	    gbc.gridx = 1;
	    pan2.add(nickBouton, gbc);
	    pan.add(pan2, BorderLayout.CENTER);
	    
	    this.setResizable(false);
	    this.setVisible(true);
    }	
    
    class NicknameListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			System.out.println("Le pseudo choisi est " + jtf.getText());
			
			pan.removeAll();
			JPanel pan2 = new JPanel();
			pan2.setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			Font police = new Font("Arial", Font.BOLD, 14);
			gbc.insets = new Insets(10,10,10,10);
			
			
			try {
				udpc.start_while();
				udpc.start_thread();
				udpc.sendEcho("Connexion");
				//Ce if nous permet de voir si le pseudo est déjà en cours d'utilisation ou non
				if(udpc.sendIfUsed(jtf.getText())) {
					System.out.println("Ce pseudo est déjà prit par un autre user");
					
					labelPseudo = new JLabel("Le pseudo " + jtf.getText() + " est déjà utilisé.");
					labelPseudo.setFont(police);
					gbc.gridheight = 1;
					gbc.gridwidth = 2;
					gbc.gridx = 0;
					gbc.gridy = 0;
					pan2.add(labelPseudo,gbc);
					gbc.gridwidth = 1;
					gbc.gridy = 1;
					pan2.add(jtf,gbc);
					gbc.gridx = 1;
					pan2.add(nickBouton,gbc);
					pan.add(pan2, BorderLayout.CENTER);
					
				//Si le pseudo est libre	
				}
				else {
					System.out.println("Le pseudo " + jtf.getText() + " est valide");
					user.set_nickname(jtf.getText());
					udpc.sendEcho(user.get_nickname());
					
					labelPseudo.setText("Votre pseudo est : " + jtf.getText());
					labelPseudo.setFont(police);
					clavBouton.addActionListener(new OpenDiscussion());
				    changeNick.addActionListener(new ChangeNicknameListener());
					gbc.gridheight = 1;
					gbc.gridwidth = 2;
					gbc.gridx = 0;
					gbc.gridy = 0;
					pan2.add(labelPseudo,gbc);
					gbc.gridwidth = 1;
					gbc.gridy = 1;
					pan2.add(clavBouton,gbc);
					gbc.gridx = 1;
					pan2.add(changeNick,gbc);
					pan.add(pan2, BorderLayout.CENTER);
					
				}

			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			pan.revalidate();
			pan.repaint();
		}
    }
    
    class OpenDiscussion implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			try {
				udpc.sendEcho("Refresh");
				Thread.sleep(200);
				newWindowUserList();
			} catch (IOException | InterruptedException e1) {
				e1.printStackTrace();
			}
    	}
	}
    
    class ChangeNicknameListener implements ActionListener{
    	
		@Override
		public void actionPerformed(ActionEvent e) {
			pan.removeAll();
			
			JPanel pan2 = new JPanel();
			labelPseudo.setText("Veuillez saisir votre nouveau pseudo :");
			pan2.setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			jtf.setText(user.get_nickname());
			
			gbc.insets = new Insets(10,10,10,10);
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.gridwidth = 2;
			gbc.gridheight = 1;
			pan2.add(labelPseudo, gbc);
			gbc.gridy = 1;
			gbc.gridwidth = 1;
			pan2.add(jtf, gbc);
			gbc.gridx = 1;
			pan2.add(nickBouton, gbc);
			
			pan.add(pan2, BorderLayout.CENTER);
			pan.revalidate();
			pan.repaint();
			}
		}
    
    public void newWindowUserList() {
    	new WindowUserList(user.get_table(),user,this);
    }

    public UDPConnect getUDPC() {
    	return this.udpc;
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


