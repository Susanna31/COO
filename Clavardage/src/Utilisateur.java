import java.net.*;
import java.io.*;
import javax.swing.*;

public class Utilisateur {
	
	private String ip_adress;
	//private String password;
	private String nickname;
	private int port;
	
	public Utilisateur(String ip, String pass) {
		this.ip_adress = ip;
		//this.password = pass;
	}
	
	//Liste des setters
	public void set_nickname(String nick) {
		this.nickname = nick;
	}
	
	//Liste des getters
	public String get_ip_adress() {
		return this.ip_adress;
	}
	
	public String get_nickname() {
		return this.nickname;
	}
	
	public int getPort() {
		return this.port;
	}
	
	public static int test_Port() {
		int testPort;
		for(testPort = 1024; testPort < 65535; testPort++) {
			try {
				ServerSocket test = new ServerSocket(testPort);
				return testPort;
			} catch (IOException test) {
			System.err.println("Le port " + testPort + " est déjà prit");
			}
		}
		return testPort;
	}
	
	public static void main(String[] args) throws IOException {
		Utilisateur User1 = new Utilisateur("10.blabla", "toto");
		User1.port = test_Port();
		System.out.println("Le port de l'utilisateur 1 est : " + User1.port);
		Utilisateur User2 = new Utilisateur("10.bloblo", "tata");
		User2.port = test_Port();
		System.out.println("Le port de l'utilisateur 2 est : " + User2.port);
		Utilisateur User3 = new Utilisateur("10.bleble", "tete");
		User3.port = test_Port();
		System.out.println("Le port de l'utilisateur 3 est : " + User3.port);
		
	}
}
