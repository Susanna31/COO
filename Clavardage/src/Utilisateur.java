import java.net.*;
import java.io.*;
import javax.swing.*;
import java.util.*; 

public class Utilisateur {
	
	private String ip_adress;
	//private String password;
	private String nickname;
	private int port;
	private Hashtable<Integer, String> table1;
	
	public Utilisateur(String ip, String pass) {
		this.ip_adress = ip;
		//this.password = pass;
	}
	
	//Liste des setters
	public void set_nickname(String nick) {
		this.nickname = nick;
		//Puis pinger les autres utilisateurs pour la disponibilité de celui-ci
	}
	
	public void set_port(int p) {
		this.port = p;
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
		User1.set_port(test_Port());
		System.out.println("Le port de l'utilisateur 1 est : " + User1.port);
		
		Window fenetre = new Window();
		User1.table1 = new Hashtable<>();
		User1.table1.put(3,"Salut"); 
		User1.table1.put(9,"tulaS"); 
		System.out.println("Mappings of ht1 : " + User1.table1); 
		}
}

