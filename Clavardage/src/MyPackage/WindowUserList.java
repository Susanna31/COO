package MyPackage;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;
import java.util.*;

import javax.swing.*;

public class WindowUserList{
	
	private JFrame frame = new JFrame();	
	private JPanel pan = new JPanel();
	private JLabel jl = new JLabel("Veuillez choisir avec qui vous voulez parler");
	private JButton confirm = new JButton("Valider");
	private Utilisateur user; 
	private Hashtable<Integer, String> table;
	private Object copie_table;
	private Checkbox[] arrayBox;
	private String[] listName;
	
	
	public WindowUserList(Hashtable<Integer, String> t, Utilisateur u) {
		this.user = u;
		int i = 0;
		this.table = t;
		frame.setSize(300,300);
		frame.setContentPane(pan);
		frame.setTitle("Liste des utilisateurs en ligne");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pan.add(jl);
		
		listName = new String[t.size()];
		arrayBox = new Checkbox[t.size()];

		Set<Integer> keys = this.table.keySet();
		
		confirm.addActionListener(new confirmListener());
		
    	for(Integer key : keys) {
    		arrayBox[i] = new Checkbox(this.table.get(key));
    		pan.add(arrayBox[i]);
    		i++;
    	}
		
		pan.add(confirm);
		frame.setVisible(true);
	}
	
	public int compare_list(String s, Hashtable<Integer, String> t) {
		Set<Integer> keys = t.keySet();
    	for(Integer key : keys) {
    		if (this.table.get(key).equals(s)) {
    			return key;
    		}
    	}
		return 0;
	}
	
	class confirmListener implements ActionListener{

		public void actionPerformed(ActionEvent e){
			copie_table = table.clone();			
			for (int a = 0; a < table.size(); a++){
				if (arrayBox[a].getState()){
					int result = compare_list(arrayBox[a].getLabel(), (Hashtable<Integer, String>) table.clone());
					WindowConversation conv = new WindowConversation(user, result, arrayBox[a].getLabel());
					frame.setVisible(false);
				}
			}
		}
	}
		
}
