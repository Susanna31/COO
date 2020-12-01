package MyPackage;
import java.io.IOException;
import java.io.*;
import java.net.*;
import java.util.Hashtable;

public class UDPConnect implements Runnable {
	    private DatagramSocket socket;
	    private InetAddress address;
		private Hashtable<Integer, String> table1;
	    private byte[] buf;
	    private boolean running;
	    private Utilisateur user;
	    private Thread thread;
	 
	    public UDPConnect(Utilisateur user) throws UnknownHostException, SocketException {
				this.socket = new DatagramSocket();
		        this.address = InetAddress.getByName("localhost");
		        this.user = user; 
		    	thread = new Thread(this);
	    }
	    
	    public void Start_thread() {
	    	thread.start();
	    }
	    
	    public Hashtable<Integer, String> sendEcho(String msg) throws IOException {
	    	String received = new String(); //Partie cherchant à avoir la liste des users
	    	this.table1 = new Hashtable<>();
	    	System.out.println("La fonction sendEcho est en route");
	    	for (int i = 1024; i < 65535; i++) {
		        buf = msg.getBytes();
		        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, i);
		        socket.send(packet);
		        packet = new DatagramPacket(buf, buf.length);
		        /*socket.receive(packet);
		        received = new String(packet.getData(), 0, packet.getLength());*/
		        this.table1.put(i,received); 
	        }
	    	return this.table1; 
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
					
					DatagramSocket dgramSocket = new DatagramSocket(user.get_port());
					DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);
					
					System.out.println("Attente du pack");
					dgramSocket.receive(inPacket);
					System.out.println("Pack reçu");
					
					InetAddress clientAddress = inPacket.getAddress();
					int clientPort = inPacket.getPort();
					
					String message = new String(inPacket.getData(), 0, inPacket.getLength());
					
					System.out.println("L'utilisateur au port " + clientPort + " m'a  envoyé le message : " + message);
					
					String response = user.get_nickname();
					inPacket = new DatagramPacket(buffer, buffer.length, clientAddress, clientPort);
					DatagramPacket outPacket = new DatagramPacket(inPacket.getData(), response.length(),clientAddress, clientPort);
					dgramSocket.send(outPacket);
					dgramSocket.close();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
		    }
		}	
}
