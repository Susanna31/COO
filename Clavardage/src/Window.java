import javax.swing.*;
 
public class Window extends JFrame {
	
	private JPanel pan = new JPanel();
    private UserClient bouton = new UserClient("Mon bouton");
	
    public Window(){
	  
    this.setTitle("Application de clavardage");
    this.setSize(900, 650);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pan.add(bouton);
    this.setContentPane(pan);
    this.setVisible(true);
  }
	
}