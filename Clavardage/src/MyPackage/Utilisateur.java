package MyPackage;
import java.net.*;
import java.util.Hashtable;
import java.util.Set;
import java.io.*;

public class Utilisateur{
	
	private String ip_adress;
	private String password;
	private String nickname;
	private int port;
	private Hashtable<Integer, String> table;
	private Hashtable<Integer, Boolean> table_conv;
	private ServerSocket ssUser;
	
	public Utilisateur(String ip, String pass) {
		this.ip_adress = ip;
		this.password = pass;
	}
	
	//Liste des setters
	public void set_nickname(String nick) {
		this.nickname = nick;
	}
	
	public void set_port(int p) {
		this.port = p;
	}	
	
	public void set_table(Hashtable<Integer, String> t) {
		this.table = t;
	}
	
	public void set_tableConv(Hashtable<Integer, Boolean> t) {
		this.table_conv = t;
	}
	
	public void set_ConvState(int port, Boolean b) {
		this.table_conv.replace(port, b);
	}
	
	//Liste des getters
	public String get_ip_adress() {
		return this.ip_adress;
	}
	
	public String get_nickname() {
		return this.nickname;
	}
	
	public int get_port() {
		return this.port;
	}
	
	public String get_password() {
		return this.password;
	}
	
	public Hashtable<Integer, String> get_table(){
		return this.table;
	}
	
	public int get_table_length() {
		return this.table.size();
	}
	
	public ServerSocket get_ssUser() {
		return this.ssUser;
	}
	
	public Hashtable<Integer, Boolean> get_TableConv(){
		return this.table_conv;
	}

    public String getNickUserdist(int key) {
    	return this.table.get(key);
    }
	
	public int test_Port() {
		int testPort;
		for(testPort = 1024; testPort < 65535; testPort++) {
			try {
				this.ssUser = new ServerSocket(testPort);
				return testPort;
			} catch (IOException test) {
			System.err.println("Le port " + testPort + " est déjà prit");
			}
		}
		return testPort;
	}
}

