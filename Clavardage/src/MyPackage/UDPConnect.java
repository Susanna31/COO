package MyPackage;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Set;

public class UDPConnect implements Runnable, Observable {
	    private DatagramSocket socket;
	    private InetAddress address;
		//private Hashtable<Integer, String> table;
		//private Hashtable<Integer, Boolean> table_conv;
	    private byte[] buf;
	    private Utilisateur user;
	    private Thread thread;
	    private boolean exit = false;
	    private byte[] buffer = new byte[256];
	    //ajout
	    private Window window;
	    private ArrayList<Observer> obsList = new ArrayList<Observer>();
	 
	    public UDPConnect(Utilisateur user, Window w) throws UnknownHostException, SocketException {
		        this.address = InetAddress.getByName("localhost");
		        this.user = user; 
				this.socket = new DatagramSocket(user.get_port());
		    	thread = new Thread(this);
		    	//this.table = new Hashtable<Integer, String>();
		    	//this.table_conv = new Hashtable<Integer, Boolean>();
		    	this.window = w;
		    	//socket.connect(InetAddress.getByName("8.8.8.8"),10002);
		    	//this.user.setIp(socket.getLocalAddress().getHostAddress());
		    	
	    }
	    
	    public void start_thread() {
	    	if(!thread.getState().equals(Thread.State.RUNNABLE)) {
	    		thread.start();
	    	}
	    }
	    
	    /*public Hashtable<Integer, String> get_Table(){
	    	return table;
	    }*/
	    
	    //vérifie si le pseudo en paramètre est déjà utilisé
	    public boolean sendIfUsed(String s) {
	    	//Set<Integer> keys = this.table.keySet();
	    	Set<Integer>keys = this.user.get_table().keySet();
	    	for(Integer key : keys) {
	    		//if (this.table.get(key).equals(s)) {
	    		if(this.user.get_table().get(key).equals(s)) {
	    			return true;
	    		}
	    	}
	    	return false;
	    }
	    

	    //permet d'envoyer le message passé en paramètre à tous les autres utilisateurs connectés
	    public void sendEcho(String msg) throws IOException {
	    	
	    	String received = new String(); //Partie cherchant à avoir la liste des users
	    	
	    	for (int i = 1024; i < 2000; i++) {
	    		if (i != this.user.get_port()) {
			        buf = msg.getBytes();
			        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, i);
			        //DatagramPacket packet = new DatagramPacket(buf, buf.length, user.get_ip_address(), i);
			        socket.send(packet);
			        
	    		}
	        }
	    	return;
	    }
	    
	    //permet d'envoyer le nickname passé en paramètre au port passsé en paramètre
	    public void sendNickname(String nick, int portDest) throws IOException{
	    	this.buf = nick.getBytes();
	    	DatagramPacket packet = new DatagramPacket(buf, buf.length, address, portDest);
	    	//DatagramPacket packet = new DatagramPacket(buf, buf.length, portDest);
	    	socket.send(packet);
	    }
	    
	    public void start_while() {
	    	exit = false;
	    }
	    
	    //notifie les autres utilisateurs de la déconnexion
		public void stop_thread() throws IOException{
	    	exit = true;
	    	int currentPort = user.get_port();
	    	String Deco = "Deconnexion";
	    	buf = Deco.getBytes();
	    	//DatagramPacket packet = new DatagramPacket(buf, buf.length, address, currentPort);
	    	DatagramPacket packet = new DatagramPacket(buf, buf.length, currentPort);
	    	socket.send(packet);
	    }
		
		//permet de recevoir et traiter les messages reçus en udp
		public void serverListener() {
			try {
				DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);

				socket.receive(inPacket);
				
				InetAddress clientAddress = inPacket.getAddress();
				int clientPort = inPacket.getPort();
				
				String message = new String(inPacket.getData(), 0, inPacket.getLength());
				
				System.out.println(user.get_nickname() + " : L'utilisateur au port " + clientPort + " m'a  envoyé le message : " + message);
 
				if (message.equals("Connexion") || message.equals("Refresh")) {
					sendNickname(user.get_nickname(), clientPort);
				}
				
				else if(message.equals("Deconnexion")) {
				}
				
				else if(message.equals("Disconnect")) {
					user.get_table().remove(clientPort);
				}
				
				else {
					//met à jour hashtable associant chaque pseudo à un port
					//if(this.table.contains(clientPort)) {
						//this.table.replace(clientPort, message);
					 if(this.user.get_table().contains(clientPort)) {
						 this.user.replaceInTable(clientPort,message);
					}
					else {
						//this.table.put(clientPort, message);
						this.user.putInTable(clientPort, message);
						//this.table_conv.put(clientPort, false);
						//this.user.putInTableConv(clientPort,false);
					}
					//this.user.set_table(this.table);
					this.updateObservers(message);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	    

		@Override
		public void run() { //Partie récepteur/Serveur
			exit = false;
			while(true) {
				while (!exit) {
					serverListener();
			    }
				while(exit) {
					
				}
			}

		}	
		
		@Override
	    public void addObserver(Observer o) {
	    	this.obsList.add(o);
	    }
	    
	    @Override
	    public void delObserver(Observer o) {
	    	this.obsList.remove(o);
	    }
	    
	    @Override
	    public void updateObservers(String s) {
	    	for (Observer o : this.obsList) {
	    		o.update(s);
	    	}
	    }
}
