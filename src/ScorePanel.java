
import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {
    private JLabel scoreLabel;
    private int score;

    public ScorePanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT)); 
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(scoreLabel);
        score = 0;
        
    }
    
    public int getScore() {
    	return score;
    }

    public void updateScore(int newScore) {
        scoreLabel.setText("Score: " + newScore);
    }
}
