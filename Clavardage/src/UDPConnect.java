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
	 
	    public UDPConnect(Utilisateur user) throws UnknownHostException, SocketException {
				this.socket = new DatagramSocket();
		        this.address = InetAddress.getByName("localhost");
		        this.user = user; 
	    }
	 
	    public Hashtable<Integer, String> sendEcho(String msg) throws IOException {
    	String received = new String(); //Partie cherchant à avoir la liste des users
    	this.table1 = new Hashtable<>();
	    	for (int i = 1024; i < 65535; i++) {
		        buf = msg.getBytes();
		        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, i);
		        socket.send(packet);
		        packet = new DatagramPacket(buf, buf.length);
		        socket.receive(packet);
		        received = new String(packet.getData(), 0, packet.getLength());
		        this.table1.put(i,received); 
	        }
	    	return this.table1; 
	    }
	 
	    public void close() {
	        socket.close();
	    }

		@Override
		public void run() { //Partie récepteur/Serveur
			while (true) {
				try {
					ServerSocket ss = new ServerSocket(user.getPort());
					
					PrintWriter pr = new PrintWriter(s.getOutputStream());
					pr.println(user.get_nickname());
					
					Socket s = ss.accept();
					
					pr.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
		    }
		}
}
