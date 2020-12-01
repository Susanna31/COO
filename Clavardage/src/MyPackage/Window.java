package MyPackage;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
 
public class Window extends JFrame{
	
	private JPanel pan = new JPanel();
    private UserClient bouton = new UserClient("Ouverture de session de clavardage");
    private JTextField jtf = new JTextField("Nickname");
	
    public Window(){
	  
	    this.setTitle("Application de clavardage");
	    this.setSize(900, 650);
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    pan.add(bouton);
	    this.setContentPane(pan);
	    Font police = new Font("Arial", Font.BOLD, 14);
	    jtf.setFont(police);
	    jtf.setPreferredSize(new Dimension(150,30));
	    pan.add(jtf);
	    
	    this.setVisible(true);
    }	
    
    class NicknameListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			System.out.println("Le pseudo choisi est " + jtf.getText());
			
		}
    	
    }
}