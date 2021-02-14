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
	private Checkbox[] arrayBox;
	//ajout
	private Window window;
	
	
	public WindowUserList(Hashtable<Integer, String> t, Utilisateur u, Window w) {
		this.user = u;
		int i = 0;
		frame.setSize(300,300);
		frame.setContentPane(pan);
		frame.setTitle("Liste des utilisateurs en ligne");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.window = w;
		
		pan.add(jl);
		
		arrayBox = new Checkbox[t.size()];

		Set<Integer>keys = this.user.get_table().keySet();
		
		confirm.addActionListener(new confirmListener());
		
		//affichage pseudos connect�s avec qui clavardage possible
    	for(Integer key : keys) {
    		arrayBox[i] = new Checkbox(this.user.get_table().get(key));
    		System.out.println("TEST AAAAA" + this.user.get_table().get(key));
    		pan.add(arrayBox[i]);
    		i++;
    	}
		
		pan.add(confirm);
		frame.setVisible(true);
	}
	
	//r�cup�ration des num�ros de port associ�s aux pseudos s, ce qui permet d'ouvrir la conversation avec les bons num�ros de port.
	public int compare_list(String s, Hashtable<Integer, String> t) {
		Set<Integer> keys = t.keySet();
    	for(Integer key : keys) {
    		if(this.user.get_table().get(key).equals(s)) {
    			return key;
    		}
    	}
		return 0;
	}
	
	class confirmListener implements ActionListener{

		public void actionPerformed(ActionEvent e){
			for(int a = 0; a < user.get_table().size(); a++) {
				if (arrayBox[a].getState()){
					int result = compare_list(arrayBox[a].getLabel(), (Hashtable<Integer,String>) user.get_table().clone());
					try {
						if(user.getWindowConvList().get(result) == null) {
							user.putInWindowConvList(result,new WindowConversation(user, result, arrayBox[a].getLabel(), user.get_tableIP().get(result), window));
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
