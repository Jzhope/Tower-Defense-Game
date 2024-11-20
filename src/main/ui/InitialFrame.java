package ui;



import javax.swing.*;

// Constructs main window
// effects: sets up window in which menu while be rendered
public class InitialFrame extends JFrame {
    public InitialFrame() {
         
        setTitle("Tower Defense Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        InitialPanel initialPanel = new InitialPanel();
        add(initialPanel);
        
  
        setSize(300, 200);
        setLocationRelativeTo(null);  
        setVisible(true);
    }
    
    public static void main(String[] args) throws Exception {
        new InitialFrame();
        
    }
}
