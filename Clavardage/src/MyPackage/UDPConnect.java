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
	 
	    public UDPConnect(Utilisateur user) throws UnknownHostException, SocketException {
		        this.address = InetAddress.getByName("localhost");
		        this.user = user; 
				this.socket = new DatagramSocket(user.get_port());
		    	thread = new Thread(this);
		    	this.table = new Hashtable<Integer,String>();
		    	this.start_thread();
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
	    		if (this.table.get(key) == s) {
	    			return true;
	    		}
	    	}
	    	return false;
	    }
	    
	    public void sendEcho(String msg) throws IOException {
	    	
	    	String received = new String(); //Partie cherchant à avoir la liste des users
	    	System.out.println("La fonction sendEcho est en route");
	    	
	    	for (int i = 1024; i < 65535; i++) {
	    		if (i != this.user.get_port()) {
			        buf = msg.getBytes();
			        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, i);
			        socket.send(packet);
			        packet = new DatagramPacket(buf, buf.length);
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

		@Override
		public void run() { //Partie récepteur/Serveur
			System.out.println("Le thread est en route");
			byte[] buffer = new byte[256];
			
			while (true) {
				
				try {
					
					//DatagramSocket dgramSocket = new DatagramSocket(user.get_port());
					DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);

					System.out.println("Attente du pack");
					socket.receive(inPacket);

					System.out.println("Pack reçu");
					
					InetAddress clientAddress = inPacket.getAddress();
					int clientPort = inPacket.getPort();
					
					String message = new String(inPacket.getData(), 0, inPacket.getLength());
					
					System.out.println("L'utilisateur au port " + clientPort + " m'a  envoyé le message : " + message);

					if (message.equals("Connexion")) {
						sendNickname(user.get_nickname(), clientPort);
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
		}	
}
