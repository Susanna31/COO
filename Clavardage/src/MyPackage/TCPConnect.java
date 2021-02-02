package MyPackage;
import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


public class TCPConnect implements Runnable {

    private Utilisateur user;
    private boolean sessionOuverte;
    private Thread thread;
    private LocalDateTime horodatage;
    private DateTimeFormatter myFormatObj;
    private String formattedDate;
    private WindowConversation conv;
    private List<WindowConversation> List1 = new ArrayList<WindowConversation>();
    private List<Integer> List2 = new ArrayList<Integer>();
    private Hashtable<Integer, WindowConversation> Conv = new Hashtable<Integer, WindowConversation>();
    private Hashtable<Integer, Integer> ConvBinary = new Hashtable<Integer, Integer>();

    public TCPConnect(Utilisateur u){
        this.user = u;
        this.sessionOuverte = true;
        thread = new Thread(this);
        thread.start();
    }

    public void receptionServer() throws SQLException{
        
		try {
			Socket s1 = user.get_ssUser().accept();
			BufferedReader br = new BufferedReader(new InputStreamReader(s1.getInputStream()));
	        String message = br.readLine();
	        String[] splitedList = message.split("#forbidden#");
	        
	        int new_port = Integer.parseInt(splitedList[0]);
	        String new_message = splitedList[1];
	        ConvBinary.put(new_port, 1);
	        horodatage = LocalDateTime.now();
	        myFormatObj = DateTimeFormatter.ofPattern("dd-MM HH:mm");
	        formattedDate = horodatage.format(myFormatObj);
	        
	        if(!user.get_TableConv().get(new_port)){
	        	user.set_ConvState(new_port, true);
	        	List1.add(new WindowConversation(user, new_port, user.getNickUserdist(new_port), "100.100.1.2"));
	        	}
	        
	        if(!List2.contains(new_port)) {
        		List2.add(new_port);	
	        }
	        
	        Integer tmp_port = List2.indexOf(new_port);
	        System.out.println(List1.size());
	        List1.get(tmp_port).recevoir(formattedDate + " " + new_message);
	        	
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public Boolean isConvActive(int Port) {
    	if (this.Conv.get(Port) != null){
    		return true;
    	}
    	return false;
    }
    
    public void addInConvActive(int Port, WindowConversation Wc) {
    	if(this.Conv.get(Port) == null) {
    		this.Conv.put(Port, Wc);
    	}
    }

    public void envoiMsg(String message, int portDest) throws UnknownHostException, IOException, SQLException {
            
    	Socket s = new Socket("localhost", portDest);
    	int port = user.get_port();
    	String portStr = String.valueOf(port);
        PrintWriter out = new PrintWriter(s.getOutputStream(),true);
        out.append(portStr + "#forbidden#");
        out.println(message);
        user.set_ConvState(portDest, true);
        
        if(!List2.contains(portDest)) {
    		List2.add(portDest);
        }
        
        if(!isConvActive(portDest)) {
        	Conv.put(portDest, new WindowConversation(user, portDest, user.getNickUserdist(portDest), "100.100.1.2"));
        	System.out.println("On créer une nouvelle fenêtre");
        }

        s.close();

    }

    public void closeSession(){
        this.sessionOuverte = false;
    }
    
    public Boolean CheckifOpen(WindowConversation wc) {
    	return List1.contains(wc);
    }
    
    public void AddinList(WindowConversation wc, int port) {
    	List1.add(wc);
    	List2.add(port);
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