package connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Set;

import observers.Observable;
import observers.Observer;
import other.Utilisateur;
import windows.Window;

public class UDPConnect implements Runnable, Observable {
	    private DatagramSocket socket;
	    private InetAddress address;
	    private byte[] buf;
	    private Utilisateur user;
	    private Thread thread;
	    private boolean exit = false;
	    private byte[] buffer = new byte[256];
	    private int compteur = 0;
	    private Window window;
	    private ArrayList<Observer> obsList = new ArrayList<Observer>();
	 
	    public UDPConnect(Utilisateur user, Window w) throws UnknownHostException, SocketException {
		        this.address = InetAddress.getByName("localhost");
		        this.user = user; 
				this.socket = new DatagramSocket(user.get_port());
		    	thread = new Thread(this);
		    	this.window = w;
		    	
	    }
	    
	    public void start_thread() {
	    	if(!thread.getState().equals(Thread.State.RUNNABLE)) {
	    		thread.start();
	    	}
	    }
	    
	    //v�rifie si le pseudo en param�tre est d�j� utilis�
	    public boolean sendIfUsed(String s) {
	    	Set<Integer>keys = this.user.get_table().keySet();
	    	for(Integer key : keys) {
	    		if(this.user.get_table().get(key).equals(s)) {
	    			return true;
	    		}
	    	}
	    	return false;
	    }
	    

	    //permet d'envoyer le message pass� en param�tre � tous les autres utilisateurs connect�s
	    public void sendEcho(String msg) throws IOException {
	    	
	    	String received = new String(); //Partie cherchant � avoir la liste des users
	    	this.compteur = 0; //Etant donn� que le port donn� aux utilisateurs est donn� de 1024 vers 65535, le break nous permet ici de couper l'envoi si 
	    					   //le port de 100 utilisateurs d'affil�e n'est pas attribu�, ce qui nous permet d'arr�ter le broadcast plus t�t.
	    	for (int i = 1024; i < 65535; i++) {
	    		this.compteur +=1;
	    		System.out.println(compteur);
    			if(compteur >=100) {
    				break;
    			}
	    		if (i != this.user.get_port()) {
			        buf = msg.getBytes();
			        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, i);
			        socket.send(packet);
			        
	    		}
	        }
	    	return;
	    }
	    
	    //permet d'envoyer le nickname pass� en param�tre au port pass� en param�tre
	    public void sendNickname(String nick, int portDest) throws IOException{
	    	this.buf = nick.getBytes();
	    	DatagramPacket packet = new DatagramPacket(buf, buf.length, address, portDest);
	    	socket.send(packet);
	    }
	    
	    //Permet d'envoyer les ACK qui confirment la r�ceptions et remettre le compteur � 0
	    public void sendConfirm(int portDest) throws IOException{
	    	this.buf = "Ok".getBytes();
	    	DatagramPacket packet = new DatagramPacket(buf, buf.length, address, portDest);
	    	
	    	socket.send(packet);
	    }
	    
	    public void start_while() {
	    	exit = false;
	    }
	    
	    //notifie les autres utilisateurs de la d�connexion
		public void stop_thread() throws IOException{
	    	exit = true;
	    	int currentPort = user.get_port();
	    	String Deco = "Deconnexion";
	    	buf = Deco.getBytes();
	    	DatagramPacket packet = new DatagramPacket(buf, buf.length, currentPort);
	    	socket.send(packet);
	    }
		
		//permet de recevoir et traiter les messages re�us en udp
		public void serverListener() {
			try {
				DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);
				socket.receive(inPacket);		
				InetAddress clientAddress = inPacket.getAddress();
				int clientPort = inPacket.getPort();
				
				String message = new String(inPacket.getData(), 0, inPacket.getLength());
				//Permet de savoir quels messages ont �t� re�u de la part de quels utilisateur (surtout utile au debogage)
				System.out.println(user.get_nickname() + " : L'utilisateur au port " + clientPort + " m'a  envoy� le message : " + message);
 
				if (message.equals("Connexion") || message.equals("Refresh")) {
					sendNickname(user.get_nickname(), clientPort);
					sendConfirm(clientPort);
				}
				
				else if(message.equals("Disconnect")) {
					user.get_table().remove(clientPort);
				}
				
				else if(message.equals("Ok")) {
					this.compteur = 0;
				}
				
				else {
					sendConfirm(clientPort);
					//met � jour hashtable associant chaque pseudo � un port
					 if(this.user.get_table().contains(clientPort)) {
						 this.user.replaceInTable(clientPort,message);
					}
					else {
						this.user.putInTable(clientPort, message);
					}
					this.updateObservers(message);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	   
		@Override
		public void run() { //Partie r�cepteur/Serveur
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
