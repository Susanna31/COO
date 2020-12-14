package MyPackage;
import java.io.*;
import java.net.*;

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
			BufferedReader br = new BufferedReader(new InputStreamReader(s1.getInputStream()));
	        String message = br.readLine();
	        System.out.println(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
        //faire observer/observable et notifier fenetre que message reçu pour l'afficher

    }

    public void envoiMsg(String message, int portDest) throws UnknownHostException, IOException {
            
    	Socket s = new Socket("localhost",portDest);
        PrintWriter out = new PrintWriter(s.getOutputStream(),true);
        out.println(message);

        s.close();

        //afficher message sur fenetre avec destinataires
    }

    public void closeSession(){
        this.sessionOuverte = false;
    }

    @Override
    public void run(){
        while (sessionOuverte){
            this.receptionServer();
        }
    }
}