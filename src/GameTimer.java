
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat; 

public class GameTimer implements ActionListener {

    private Timer timer1; 
    private int count1; 
    private JLabel timerLabel1;
    
    private Timer timer2; 
    private int count2; 
    private JLabel timerLabel2;
    private final int ROUND_TIME_SECONDS = 30; 

    private final DecimalFormat dFormat = new DecimalFormat("00");
    
    private GamePanel gamePanel; 

    public GameTimer(GamePanel panel) { 
        this.gamePanel = panel;
        
        count1 = 600; 
        timerLabel1 = new JLabel("10:00");
        timer1 = new Timer(1000, this); 
        
        count2 = ROUND_TIME_SECONDS; 
        timerLabel2 = new JLabel("Round: 30");
        timer2 = new Timer(1000, this); 
    }
    
    public void startTimers() { 
        timer1.start(); 
        timer2.start(); 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer1) {
            if (count1 > 0) {
                count1--;
                updateTimerLabel1();
            } else {
                timer1.stop();
                timerLabel1.setText("Time Up!");
                gamePanel.gameOverTrigger(); 
            }
        } else if (e.getSource() == timer2) {
            if (count2 > 0) {
                count2--;
                updateTimerLabel2();
            } else {
            	gamePanel.initiateRoundBreak(); 
            }
        }
    }
    
    private void updateTimerLabel1() {
        int minutes = count1 / 60;
        int seconds = count1 % 60;
        timerLabel1.setText(dFormat.format(minutes) + ":" + dFormat.format(seconds));
    }
    
    private void updateTimerLabel2() { 
        timerLabel2.setText("Round: " + count2); 
    }

    public JLabel getTimerLabel1() { return timerLabel1; }
    public JLabel getTimerLabel2() { return timerLabel2; }
    public int getCount1() { return count1; }
    public int getCount2() { return count2; }
    
    public void resetTimer2() { 
        count2 = ROUND_TIME_SECONDS; 
        updateTimerLabel2();
    }
}
