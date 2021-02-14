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
	
	//contient les pseudos de tous les utilisateurs actifs et chaque numéro de port correspondant
	private Hashtable<Integer, String> table;
	//permet de savoir si une conversation est ouverte avec un utilisateur sur un numéro de port 
	//private Hashtable<Integer, Boolean> table_conv;
	private ServerSocket ssUser;
	//ajout
	private Hashtable<Integer,WindowConversation> windowConvList;
	
	public Utilisateur(String pass) {
		this.password = pass;
		this.nickname = "/Nickname";
		//ajout
		this.table = new Hashtable<Integer, String>();
		//this.table_conv = new Hashtable<Integer, Boolean>();
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
	
	/*public void putInTableConv(int i, boolean b) {
		this.table_conv.put(i,b);
	}*/
	
	public void putInWindowConvList(int i, WindowConversation wc) {
		this.windowConvList.put(i,wc);
	}
	
	/*public void set_tableConv(Hashtable<Integer, Boolean> t) {
		this.table_conv = t;
	}*/

	//entre dans la hashtable table_conv si une session de clavardage est en cours avec l'utilisateur sur le port passé en paramètre
	/*public void set_ConvState(int port, Boolean b) {
		this.table_conv.replace(port, b);
	}*/
	
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
	
	public Hashtable<Integer, WindowConversation> getWindowConvList(){
		return this.windowConvList;
	}
	
	public int get_table_length() {
		return this.table.size();
	}
	
	public ServerSocket get_ssUser() {
		return this.ssUser;
	}
	
	/*public Hashtable<Integer, Boolean> get_TableConv(){
		return this.table_conv;
	}*/

	//retourne pseudo utilisateur utilisant le port passé en paramètre
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
			System.err.println("Le port " + testPort + " est déjà prit");
			}
		}
		return testPort;
	}
}

