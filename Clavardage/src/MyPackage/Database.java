package MyPackage;
import java.io.*; 
import java.util.*; 

public class Database {
	
	Hashtable<String, Integer> table1 = new Hashtable<>();
	
	public static void store_message(String message, Utilisateur user1, Utilisateur user2) {
		//voir comment stocker des messages dans une database
	}
	
	public static boolean history_exists(Utilisateur user1, Utilisateur user2) {
		//Voir comment vérifier qu'une conversation existe
		return true;
	}
	
	public static String[] history_load(String adress_ip1, String adress_ip2) {
		String[] message = {"Salut"};
		return message; 
	}
}
