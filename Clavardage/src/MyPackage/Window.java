package MyPackage;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
 
public class Window extends JFrame implements ActionListener {
	
	private JPanel pan = new JPanel();
    private UserClient bouton = new UserClient("Ouverture de session de clavardage");
    private JTextField jtf = new JTextField("Nickname");
    private Button b = new Button("Confirm nickname");
	
    public Window(){
	  
	    this.setTitle("Application de clavardage");
	    this.setSize(900, 650);
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    pan.add(bouton);
	    this.setContentPane(pan);
	    pan.add(b);
	    b.addActionListener(this);
	    Font police = new Font("Arial", Font.BOLD, 14);
	    jtf.setFont(police);
	    jtf.setPreferredSize(new Dimension(150,30));
	    pan.add(jtf);
	    
	    this.setVisible(true);
  }

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Le pseudo choisi est " + jtf.getText());
		
	}
	
}