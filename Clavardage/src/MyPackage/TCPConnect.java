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
			BufferedReader br = new BufferedReader(new InputStreamReader(s1.getInputStream()));
	        String message = br.readLine();
	        String[] splitedList = message.split("-");
	        
	        int new_port = Integer.parseInt(splitedList[0]);
	        String new_message = splitedList[1];
	        
	        if(!user.get_TableConv().get(new_port)){
	        	user.set_ConvState(new_port, true);
	        	WindowConversation conv = new WindowConversation(user, new_port, user.getNickUserdist(new_port));
	        }
	        
	        
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
        out.append(portStr + "-");
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