package MyPackage;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import java.sql.*;

public class WindowConversation extends JFrame implements WindowListener{

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
	private JTextArea jta = new JTextArea(20,40);
	private JPanel jp = new JPanel();
	private JLabel jl = new JLabel("test");
	private Database db = new Database();
	private Connection con;
	
	public WindowConversation(Utilisateur u, int port_user2, String nick_u2, String ip_u2) throws SQLException {
		this.tcpc = new TCPConnect(u);
		this.user1 = u;
		this.nick_user2 = nick_u2;
		this.port_u2 = port_user2;
		this.ip_u2 = ip_u2;
		System.out.println(port_user2);
		System.out.println(u.get_nickname());
		this.addWindowListener(this);
		this.con = db.init();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("Select * From Message WHERE ip_adress1 = '" + user1.get_ip_adress() +"' or ip_adress2 = '" + user1.get_ip_adress() +"';" );
		while(rs.next()) {
			System.out.println(rs.getString(3));
		}
		//Database pour historique
		
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
	
	public void envoi(String s) throws SQLException {
		
		horodatage = LocalDateTime.now();
        myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        formattedDate = horodatage.format(myFormatObj);
        
		JLabel tmpJl = new JLabel(formattedDate + " " + user1.get_nickname() + " : " + s);
		tmpJl.setForeground(Color.BLACK);
		tcpc.addInConvActive(port_u2, this);	
		
		jp.add(tmpJl);
		jp.updateUI();
		if(!tcpc.CheckifOpen(this)) {
			tcpc.AddinList(this, port_u2);
		}
		Statement stmt = con.createStatement();
		System.out.println("INSERT INTO Message (ip_adress1, ip_adress2, message, date) values ('"+user1.get_ip_adress()+"', '"+ ip_u2+"', '"+ s +"', default);");
		stmt.executeUpdate("INSERT INTO Message (ip_adress1, ip_adress2, message, date) values ('"+user1.get_ip_adress()+"', '"+ ip_u2+"', '"+ s +"', default);");
	}
	
	public void recevoir(String s) {
		JLabel tmpJl = new JLabel(nick_user2 + " : " + s);
		tmpJl.setForeground(new Color(255,0,0));
		jp.add(tmpJl);
		jp.updateUI();
	}

	class envoiListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			try {
				envoi(jtf.getText());
				tcpc.envoiMsg(jtf.getText(), port_u2);
				jtf.setText("Envoyer un message");
			} catch (IOException | SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.out.println("La fenetre est fermée");
		user1.set_ConvState(port_u2, false);
	}

	@Override
	public void windowClosed(WindowEvent e) {
		System.out.println("La fenetre est fermée");
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
