package other;
import java.net.*;
import java.util.Hashtable;
import java.util.Set;

import windows.WindowConversation;

import java.io.*;

public class Utilisateur{
	
	private InetAddress ip_address;
	private String password;
	private String nickname;
	private int port;
	
	//contient les pseudos de tous les utilisateurs actifs et chaque num�ro de port correspondant
	private Hashtable<Integer, String> table;
	private Hashtable<Integer, String> tableIP;
	//permet de savoir si une conversation est ouverte avec un utilisateur sur un num�ro de port 
	private ServerSocket ssUser;
	private Hashtable<Integer,WindowConversation> windowConvList;
	
	public Utilisateur(String pass) {
		this.password = pass;
		this.nickname = "/Nickname";
		this.table = new Hashtable<Integer, String>();
		this.tableIP = new Hashtable<Integer, String>();
		this.windowConvList = new Hashtable<Integer, WindowConversation>();
		this.port = test_Port();
		try {
		this.ip_address = InetAddress.getLocalHost();
		}
		catch(UnknownHostException e) {
			e.printStackTrace();
		}
		System.out.println("Addresse IP = " + this.ip_address);
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
	
	public void replaceInTable(int i, String s) {
		this.table.replace(i,s);
	}
	
	public void putInTable(int i, String s) {
		this.table.put(i,s);
	}
	
	public void putInTableIP(int i, String s) {
		this.tableIP.put(i,s);
	}
	
	public void putInWindowConvList(int i, WindowConversation wc) {
		this.windowConvList.put(i,wc);
	}

    public void removeWindowConvList(int i) {
    	this.windowConvList.remove(i);
    }
	
	//Liste des getters
	
	public String get_nickname() {
		return this.nickname;
	}
	
	public int get_port() {
		return this.port;
	}
	
	public String get_password() {
		return this.password;
	}
	
	public InetAddress get_ip() {
		return this.ip_address;
	}
	
	public Hashtable<Integer, String> get_table(){
		return this.table;
	}
	
	public Hashtable<Integer, String> get_tableIP(){
		return this.tableIP;
	}
	
	public Hashtable<Integer, WindowConversation> getWindowConvList(){
		return this.windowConvList;
	}
	
	public int get_table_length() {
		return this.table.size();
	}
	
	public ServerSocket get_ssUser() {
		return this.ssUser;
	}

	//retourne pseudo utilisateur utilisant le port pass� en param�tre
    public String getNickUserdist(int key) {
    	return this.table.get(key);
    }
	
    //retourne un port libre
	public int test_Port() {
		int testPort;
		for(testPort = 1024; testPort < 65535; testPort++) {
			try {
				this.ssUser = new ServerSocket(testPort);
				return testPort;
			} catch (IOException test) {
			System.err.println("Le port " + testPort + " est d�j� prit");
			}
		}
		return testPort;
	}
}

