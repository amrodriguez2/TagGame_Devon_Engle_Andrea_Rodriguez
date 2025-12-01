
import javax.swing.*;
import java.awt.*;

public class Driver extends JFrame {
	
	public Driver() {
        setTitle("Tag Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); 
        setLocationRelativeTo(null); 
        setResizable(false); 
        
        GamePanel gamePanel = new GamePanel();
        ScorePanel scorePanel = new ScorePanel();
        GameTimer gameTimer = new GameTimer(gamePanel);

        gamePanel.setGameTimer(gameTimer);  
        gamePanel.setScorePanel(scorePanel); 
        JPanel infoPanel = new JPanel(new GridLayout(1, 3)); 
        infoPanel.add(scorePanel);
        infoPanel.add(gameTimer.getTimerLabel1());
        infoPanel.add(gameTimer.getTimerLabel2());

        add(gamePanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.NORTH);

        gameTimer.startTimers();
        
        gamePanel.requestFocusInWindow(); 

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Driver::new);
    }
}
