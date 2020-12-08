package MyPackage;
import java.io.*;
import java.net.*;
import java.util.Hashtable;
import java.util.Set;

public class UDPConnect implements Runnable {
	    private DatagramSocket socket;
	    private InetAddress address;
		private Hashtable<Integer, String> table;
	    private byte[] buf;
	    private Utilisateur user;
	    private Thread thread;
	    private boolean exit = false;
	    private byte[] buffer = new byte[256];
	 
	    public UDPConnect(Utilisateur user) throws UnknownHostException, SocketException {
		        this.address = InetAddress.getByName("localhost");
		        this.user = user; 
				this.socket = new DatagramSocket(user.get_port());
		    	thread = new Thread(this);
		    	this.table = new Hashtable<Integer,String>();
	    }
	    
	    public void start_thread() {
	    	thread.start();
	    }
	    
	    public Hashtable<Integer, String> get_Table(){
	    	return table;
	    }
	    
	    public boolean sendIfUsed(String s) {
	    	Set<Integer> keys = this.table.keySet();
	    	for(Integer key : keys) {
	    		if (this.table.get(key).equals(s)) {
	    			return true;
	    		}
	    	}
	    	return false;
	    }
	    
	    public void sendEcho(String msg) throws IOException {
	    	
	    	String received = new String(); //Partie cherchant à avoir la liste des users
	    	
	    	for (int i = 1024; i < 2000; i++) {
	    		if (i != this.user.get_port()) {
			        buf = msg.getBytes();
			        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, i);
			        socket.send(packet);
			        
	    		}
	        }
	    	return;
	    }
	    
	    
	    public void sendNickname(String nick, int portDest) throws IOException{
	    	this.buf = nick.getBytes();
	    	DatagramPacket packet = new DatagramPacket(buf, buf.length, address, portDest);
	    	socket.send(packet);
	    }
	 
	    public void close() {
	        socket.close();
	    }
	    
	    public void start_while() {
	    	exit = false;
	    }
	    
		public void stop_thread() throws IOException{
	    	exit = true;
	    	int currentPort = user.get_port();
	    	String Deco = "Deconnexion";
	    	buf = Deco.getBytes();
	    	DatagramPacket packet = new DatagramPacket(buf, buf.length, address, currentPort);
	    	socket.send(packet);
	    }
		
		public void serverListener() {
			try {
				
				//DatagramSocket dgramSocket = new DatagramSocket(user.get_port());
				DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);

				socket.receive(inPacket);
				
				InetAddress clientAddress = inPacket.getAddress();
				int clientPort = inPacket.getPort();
				
				String message = new String(inPacket.getData(), 0, inPacket.getLength());
				
				System.out.println(user.get_nickname() + " : L'utilisateur au port " + clientPort + " m'a  envoyé le message : " + message);

				if (message.equals("Connexion")) {
					sendNickname(user.get_nickname(), clientPort);
				}
				
				else if(message.equals("Deconnexion")) {
				}
				
				else {
					this.table.put(clientPort,message);
					System.out.println("La hashtable de " + user.get_nickname() + " est : " + this.table);
				}
				
				/*String response = user.get_nickname();
				inPacket = new DatagramPacket(buffer, buffer.length, clientAddress, clientPort);
				DatagramPacket outPacket = new DatagramPacket(inPacket.getData(), response.length(),clientAddress, clientPort);
				socket.send(outPacket);*/
				
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
}
