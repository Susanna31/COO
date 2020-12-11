package MyPackage;
import java.io.*;
import java.net.*;

public class TCPConnect implements Runnable {

    private Utilisateur user;
    private boolean sessionOuverte;

    public TCPConnect(Utilisateur u){
        this.user = u;
        this.sessionOuverte = true;
    }

    public void receptionServer() throws IOException{
        ServerSocket ss = new ServerSocket(this.user.get_port());
        Socket s = ss.accept();

        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String message = br.readLine();

        //récupérer port source message puis avec hashtable trouver pseudo expéditeur
        //faire observer/observable et notifier fenetre que message reçu pour l'afficher

    }

    public void envoiMsg(String message, int[] portsDestTab) throws UnknownHostException, IOException {
        for (int portDest : portsDestTab){
            Socket s = new Socket("localhost",portDest);
            PrintWriter out = new PrintWriter(s.getOutputStream(),true);
            out.println(message);

            s.close();
        }

        //afficher message sur fenetre avec destinataires
    }

    public void closeSession(){
        this.sessionOuverte = false;
    }

    @Override
    public void run(){
        while (sessionOuverte){
            try {
				this.receptionServer(); //Ne marche pas car plusieurs ss vont se lancer
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
}