package MyPackage;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;
import java.util.*;

import javax.swing.*;

public class WindowUserList extends JFrame{
	
	
	private JPanel pan = new JPanel();
	private JLabel jl = new JLabel("Veuillez choisir avec qui vous voulez parler");
	private JButton confirm = new JButton("Valider");
	private Utilisateur user; 
	private Hashtable<Integer, String> table;
	private Object copie_table;
	private Checkbox[] arrayBox;
	private String[] listName;
	
	
	public WindowUserList(Hashtable<Integer, String> t) {
		int i = 0;
		this.table = t;
		this.setSize(300,300);
		this.setContentPane(pan);
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
		this.setVisible(true);
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

		public void actionPerformed(ActionEvent e) {
			copie_table = table.clone();			
			int j = 0;
			for (int a = 0; a < table.size(); a++) {
				if (arrayBox[a].getState()){
					int result = compare_list(arrayBox[a].getLabel(), (Hashtable<Integer, String>) table.clone());
					System.out.println(result);
				}
			}
		}
	}
		
}
