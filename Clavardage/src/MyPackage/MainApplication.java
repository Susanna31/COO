package MyPackage;

import java.io.IOException;

public class MainApplication {
	
	public static void main(String[] args) throws IOException, InterruptedException {

		Utilisateur user1 = new Utilisateur("192.128.0.0", "Password");
		user1.set_nickname("Pierre Ung");
		user1.set_port(user1.test_Port());
		
		Utilisateur user2 = new Utilisateur("192.128.0.0", "Password");
		user2.set_nickname("Gwenaël Ebersohl");
		user2.set_port(user2.test_Port());
		System.out.println("Le port de l'utilisateur 1 est : " + user1.get_port() +
				", celui de l'utilisateur 2 est : " + user2.get_port());
		
		UDPConnect U1 = new UDPConnect(user1);
		UDPConnect U2 = new UDPConnect(user2);
		U1.Start_thread();
		Thread.sleep(1000);
		U2.sendEcho("Salut");
		
		return;
	}
}
