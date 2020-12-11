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
	private Checkbox[] arrayBox = new Checkbox[99999];
	private String[] listName;
	
	
	public WindowUserList(Hashtable<Integer, String> t) {
		int i = 0;
		this.table = t;
		this.setSize(300,300);
		this.setContentPane(pan);
		pan.add(jl);
		listName = new String[user.get_table_length()];

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
	
	class confirmListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			
			int j = 0;
			for (int i = 0; i < 2; i++) {
				if (arrayBox[i].getState()){
					listName[j] = arrayBox[i].getLabel();
					j++;
					System.out.println(listName[j]);
				}
			}
		}
	}
}
