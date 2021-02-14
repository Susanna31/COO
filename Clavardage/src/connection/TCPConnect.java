package connection;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import other.Utilisateur;
import windows.Window;
import windows.WindowConversation;



public class TCPConnect implements Runnable {

    private Utilisateur user;
    private boolean sessionOuverte;
    private Thread thread;
    private LocalDateTime horodatage;
    private DateTimeFormatter myFormatObj;
    private String formattedDate;
    private Window window;

    public TCPConnect(Utilisateur u, Window w){
        this.user = u;
        this.sessionOuverte = true;
        this.thread = new Thread(this);
        this.thread.start();
        this.window = w;
    }

    public void receptionServer() throws SQLException{
        
		try {
			Socket s1 = user.get_ssUser().accept();
			BufferedReader br = new BufferedReader(new InputStreamReader(s1.getInputStream()));
	        String message = br.readLine();
	        
	        //le numero de port se situe avant "#forbidden#", puis vient l'adresse ip, et enfin le message.
	        String[] splitedList = message.split("#forbidden#");
	   
	        int new_port = Integer.parseInt(splitedList[0]);
	        String new_ip = splitedList[1];
	        String new_message = splitedList[2];
	        horodatage = LocalDateTime.now();
	        myFormatObj = DateTimeFormatter.ofPattern("dd-MM HH:mm");
	        formattedDate = horodatage.format(myFormatObj);
	        
	        if(this.user.getWindowConvList().get(new_port) == null) {
	        	//ouverture de conversation
	        	this.user.putInWindowConvList(new_port,new WindowConversation(user, new_port, user.getNickUserdist(new_port), new_ip,this.window));
	        	}
	        
	        //ajout num port aux sessions de clavardages en cours si pas déjà fait
	        this.user.getWindowConvList().get(new_port).recevoir(new_message, 1, null);
	        	
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public void envoiMsg(String message, int portDest, String ipDest) throws UnknownHostException, IOException, SQLException {
            
    	Socket s = new Socket("localhost", portDest);
    	int port = user.get_port();
    	String portStr = String.valueOf(port);
        PrintWriter out = new PrintWriter(s.getOutputStream(),true);
        out.append(portStr + "#forbidden#" + user.get_ip().getHostAddress() + "#forbidden#");
        out.println(message);
        
        //si il n'y avait pas de session en cours entre utilisateur et destinataire, on ouvre une fentre de clavardage
        if(this.user.getWindowConvList().get(portDest) == null) {
        	this.user.putInWindowConvList(portDest,new WindowConversation(user, portDest, user.getNickUserdist(portDest), ipDest, window));
        	System.out.println("On créer une nouvelle fenêtre");
        }
        s.close();

    }

    public void closeSession(){
        this.sessionOuverte = false;
    }

    @Override
    public void run(){
        while (sessionOuverte){
            try {
				this.receptionServer();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
}