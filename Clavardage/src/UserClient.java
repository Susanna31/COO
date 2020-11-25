import javax.swing.*;
import java.net.*;
import java.io.*;

public class UserClient extends JButton{
	
	private String name;
	
	public UserClient(String nom) {
		super(nom);
		this.name = nom;
	}
	
	public void connect(Utilisateur user_distant) throws IOException {
		
		int port_distant = user_distant.getPort();
		Socket s = new Socket("Localhost", port_distant);
	}
}
