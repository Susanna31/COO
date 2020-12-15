package MyPackage;
import java.io.*;
import java.net.*;
import java.util.Set;

public class TCPConnect implements Runnable {

    private Utilisateur user;
    private boolean sessionOuverte;
    private ServerSocket ss;
    private Thread thread;

    public TCPConnect(Utilisateur u){
        this.user = u;
        this.sessionOuverte = true;
        thread = new Thread(this);
        thread.start();
    }

    public void receptionServer(){
        
		try {
			Socket s1 = user.get_ssUser().accept();
			String new_message;
			BufferedReader br = new BufferedReader(new InputStreamReader(s1.getInputStream()));
	        String message = br.readLine();
	        String test = message.substring(0, 4);
	        if(message.substring(4,5).matches("[0-9]")){
	        	test.concat(message.substring(4,5));
	        	new_message = message.substring(5);
	        }
	        else {
	        	new_message = message.substring(4);
	        }
	        int port = Integer.parseInt(test);
	        System.out.println(port);
	        if(!user.get_TableConv().get(port)) {
	        	user.get_TableConv().replace(port, true);
	        	//Créer une WindowConversation
	        	//Il faut remplir la tableconv au début
	        }
	        System.out.println(new_message);
	        
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
        //faire observer/observable et notifier fenetre que message reçu pour l'afficher

    }

    public void envoiMsg(String message, int portDest) throws UnknownHostException, IOException {
            
    	Socket s = new Socket("localhost", portDest);
    	int port = user.get_port();
    	String portStr = String.valueOf(port);
        PrintWriter out = new PrintWriter(s.getOutputStream(),true);
        out.append(portStr);
        out.println(message);

        s.close();

        //afficher message sur fenetre avec destinataires
    }

    public void closeSession(){
        this.sessionOuverte = false;
    }

    @Override
    public void run(){
    	System.out.println("Thread de : " + user.get_nickname() + " en route");
        while (sessionOuverte){
            this.receptionServer();
        }
    }
}