package windows;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;
import java.sql.SQLException;
import java.util.*;

import javax.swing.*;

import other.Utilisateur;


public class WindowUserList{
	
	private JFrame frame = new JFrame();	
	private JPanel pan = new JPanel();
	private JLabel jl = new JLabel("Veuillez choisir avec qui vous voulez parler");
	private JButton confirm = new JButton("Valider");
	private Utilisateur user; 
	//private Hashtable<Integer, String> table;
	//private Object copie_table;
	private Checkbox[] arrayBox;
	//private String[] listName;
	//private WindowConversation conv;
	//ajout
	private Window window;
	
	
	public WindowUserList(Hashtable<Integer, String> t, Utilisateur u, Window w) {
		this.user = u;
		int i = 0;
		//this.table = t;
		frame.setSize(300,300);
		frame.setContentPane(pan);
		frame.setTitle("Liste des utilisateurs en ligne");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//ajout
		this.window = w;
		
		pan.add(jl);
		
		//listName = new String[t.size()];
		arrayBox = new Checkbox[t.size()];

		//Set<Integer> keys = this.table.keySet();
		//ajout
		Set<Integer>keys = this.user.get_table().keySet();
		
		confirm.addActionListener(new confirmListener());
		
		//affichage pseudos connectés avec qui clavardage possible
    	for(Integer key : keys) {
    		//arrayBox[i] = new Checkbox(this.table.get(key));
    		//ajout
    		arrayBox[i] = new Checkbox(this.user.get_table().get(key));
    		pan.add(arrayBox[i]);
    		i++;
    	}
		
		pan.add(confirm);
		frame.setVisible(true);
	}
	
	/*public WindowConversation get_WinConv() {
		return this.conv;
	}*/
	
	//récupération numéro port associé au pseudo s ???
	public int compare_list(String s, Hashtable<Integer, String> t) {
		Set<Integer> keys = t.keySet();
    	for(Integer key : keys) {
    		//if (this.table.get(key).equals(s)) {
    		if(this.user.get_table().get(key).equals(s)) {
    			return key;
    		}
    	}
		return 0;
	}
	
	class confirmListener implements ActionListener{

		public void actionPerformed(ActionEvent e){
			//copie_table = table.clone();	
			//copie_table = user.get_table().clone();
			//for (int a = 0; a < table.size(); a++){
			for(int a = 0; a < user.get_table().size(); a++) {
				if (arrayBox[a].getState()){
					//int result = compare_list(arrayBox[a].getLabel(), (Hashtable<Integer, String>) table.clone());
					int result = compare_list(arrayBox[a].getLabel(), (Hashtable<Integer,String>) user.get_table().clone());
					//user.set_ConvState(result, true);
					try {
						//WindowConversation conv = new WindowConversation(user, result, arrayBox[a].getLabel(), "100.100.1.2", window);
						if(user.getWindowConvList().get(result) == null) {
							user.putInWindowConvList(result,new WindowConversation(user, result, arrayBox[a].getLabel(), "100.100.1.2", window));
						}
						else {
							user.getWindowConvList().get(result).toFront();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					frame.dispose();
				}
			}
		}
	}
		
}
