package MyPackage;

import java.io.IOException;
import java.util.concurrent.*;

public class MainApplication {
	
	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException, InterruptedException {

		/*Utilisateur user1 = new Utilisateur("192.128.0.0", "Password");
		user1.set_nickname("Gwenaël");
		user1.set_port(user1.test_Port());
		
		Utilisateur user2 = new Utilisateur("192.128.0.0", "Password");
		user2.set_nickname("Susanna");
		user2.set_port(user2.test_Port());*/
		
		Utilisateur user3 = new Utilisateur("192.128.0.0", "Password");
		user3.set_port(user3.test_Port());
		
		/*System.out.println("User 1 " + user1.get_port() + " User 2 " + user2.get_port()
		 + " User 3 " + user3.get_port());
		
		Thread.sleep(100);
		UDPConnect U1 = new UDPConnect(user1);
		UDPConnect U2 = new UDPConnect(user2);
		U1.start_thread();
		U2.start_thread();*/

		Window fenetre = new Window(user3);
		/*TCPConnect Uc1 = new TCPConnect(user1);
		TCPConnect Uc2 = new TCPConnect(user2);*/
		
	}
}
