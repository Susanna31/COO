package MyPackage;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;


import java.sql.*;

public class WindowConversation extends JFrame implements WindowListener, Observer{

	private JPanel pan = new JPanel();
	private TCPConnect tcpc;
	private Utilisateur user1;
	private String nick_user2;
	private int port_u2;
	private String ip_u2;
	private LocalDateTime horodatage;
    private DateTimeFormatter myFormatObj;
    private String formattedDate;
	private JButton EnvoiMessage = new JButton("Envoyer");
	private JTextField jtf = new JTextField("Envoyer un message");
	private JScrollPane jsp;
	//private JTextArea jta = new JTextArea(20,40);
	private JPanel jp = new JPanel();
	//private JLabel jl = new JLabel("test");
	private Database db = new Database();
	private Connection con;
	//ajout
	private Window window;
	
	public WindowConversation(Utilisateur u, int port_user2, String nick_u2, String ip_u2, Window w) throws SQLException {
		this.user1 = u;
		//ajout
	    this.window = w;
		this.tcpc = new TCPConnect(this.user1,window);
		this.nick_user2 = nick_u2;
		this.port_u2 = port_user2;
		this.ip_u2 = ip_u2;
		System.out.println(port_user2);
		System.out.println(u.get_nickname());
		this.addWindowListener(this);
		
		this.con = db.init();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("Select * From Message WHERE ip_adress1 = '" + user1.get_ip().getHostAddress() +"' or ip_adress2 = '" + user1.get_ip().getHostAddress() +"';" );
		while(rs.next()) {
			if (rs.getString(1).equals(user1.get_ip().getHostAddress())) {
				envoi(rs.getString(3), 0, rs.getString(4).toString());
			}
			
			else if (rs.getString(2).equals(user1.get_ip().getHostAddress())) {
					recevoir(rs.getString(3), 0, rs.getString(4).toString());
			}

		}
		//Database pour historique
		//ajout
		this.window.getUDPC().addObserver(this);
		
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
		jsp.setVerticalScrollBarPolicy(jsp.VERTICAL_SCROLLBAR_ALWAYS);
		
		pan.add(jsp);	
		pan.add(jtf);
		pan.add(EnvoiMessage);
		
		this.setVisible(true);
	}
	
	//aficher message envoyé
	public void envoi(String s, int input, String date) throws SQLException {
		
		horodatage = LocalDateTime.now();
        myFormatObj = DateTimeFormatter.ofPattern("dd-MM HH:mm");
        formattedDate = horodatage.format(myFormatObj);
		
		if(input == 1) {
			Statement stmt = con.createStatement();
			System.out.println("INSERT INTO Message (ip_adress1, ip_adress2, message, date) values ('"+user1.get_ip().getHostAddress()+"', '"+ ip_u2+"', '"+ s +"', default);");
			stmt.executeUpdate("INSERT INTO Message (ip_adress1, ip_adress2, message, date) values ('"+user1.get_ip().getHostAddress()+"', '"+ ip_u2+"', '"+ s +"', default);");
			JLabel tmpJl2 = new JLabel(formattedDate + " " + user1.get_nickname() + " : " + s);
			jp.add(tmpJl2);
		}
		
        else{	
        	JLabel tmpJl = new JLabel(date + " " + user1.get_nickname() + " : " + s);
        	tmpJl.setForeground(Color.BLACK);
        	jp.add(tmpJl);
        }
		
		jp.updateUI();
		//tcpc.addInConvActive(port_u2, this);	
		
	}
	
	//afficher message reçu
	public void recevoir(String s, int input, String dateRec) {
		
        horodatage = LocalDateTime.now();
        myFormatObj = DateTimeFormatter.ofPattern("dd-MM HH:mm");
        formattedDate = horodatage.format(myFormatObj);
		String date = formattedDate.toString();

		JLabel tmpJl = new JLabel(date + " " + nick_user2 + " : " + s);
		tmpJl.setForeground(new Color(255,0,0));
		
		jp.add(tmpJl);
		jp.updateUI();
		
		/*try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	//listener permettant d'afficher le message envoyé
	class envoiListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			try {
				envoi(jtf.getText(), 1, null);
				tcpc.envoiMsg(jtf.getText(), port_u2, ip_u2);
				jtf.setText("Envoyer un message");
			} catch (IOException | SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	//ajout
	@Override
	public void update(String nickname) {
		this.nick_user2 = nickname;
		this.setTitle("Conversation avec : " + nickname);
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.out.println("La fenetre est fermée");
		//user1.set_ConvState(port_u2, false);
		//ajout
		this.window.getUDPC().delObserver(this);
		this.user1.removeWindowConvList(port_u2);
	}

	@Override
	public void windowClosed(WindowEvent e) {
		System.out.println("La fenetre est fermée");
		//user1.set_ConvState(port_u2, false);
		//ajout
		this.window.getUDPC().delObserver(this);
		this.user1.removeWindowConvList(port_u2);
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