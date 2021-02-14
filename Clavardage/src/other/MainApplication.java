package other;

import java.io.IOException;
import java.util.concurrent.*;

import windows.Window;

import java.sql.*;

public class MainApplication {
	
	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException, InterruptedException {
		
		Database db = new Database();
		db.init();
		
		
		Utilisateur user3 = new Utilisateur("Password");
		user3.set_port(user3.test_Port());
		
		Thread.sleep(100);


		Window fenetre = new Window(user3);
		
	}
}
