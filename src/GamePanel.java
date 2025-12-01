
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GamePanel extends JPanel implements KeyListener, ActionListener { 
    
    private ArrayList<Player> players = new ArrayList<>();
    private ScorePanel scorePanel; 
    private GameTimer gameTimer;
    private Player userPlayer; 
    private Timer gameLoopTimer;
    private int scoreData = 0; 
    private String gameState = "RUNNING";
    private Timer breakTimer; 
    private int breakCountdown = 3;

    public GamePanel() {
        setBackground(Color.BLACK); 
        initializePlayers();
        addKeyListener(this); 
        setFocusable(true); 
        requestFocusInWindow();
        
        int delay = 16;
        gameLoopTimer = new Timer(delay, this);
        gameLoopTimer.start();
    }
    
    public void setGameTimer(GameTimer timer) {
        this.gameTimer = timer;
    }
    
    public void setScorePanel(ScorePanel scores) {
        this.scorePanel = scores;
    }

    private void initializePlayers() {
    	Color[] playerColors = { Color.RED, Color.BLUE, Color.GREEN };
        this.userPlayer = new Player(playerColors[0], 50, 50, true, true);
        players.add(userPlayer); 

        for (int i = 1; i < 3; i++) {
            players.add(new Player(playerColors[i], 100 + i * 50, 150, false, false));
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        updateGame(); 
    }
    
    public void gameOverTrigger() {
        gameState = "GAME_OVER";
        gameLoopTimer.stop(); 

        JOptionPane.showMessageDialog(this, 
                                      "Game Over! Your score was: " + scoreData, 
                                      "Game Over", 
                                      JOptionPane.INFORMATION_MESSAGE);
        System.exit(0); 
    }

    public void initiateRoundBreak() {
        gameState = "ROUND_BREAK";
        breakCountdown = 3; 

        gameLoopTimer.stop(); 
        
        breakTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                breakCountdown--;
                if (breakCountdown <= 0) {
                    breakTimer.stop();
                    resumeGame(); 
                }
            }
        });
        breakTimer.start();
        
        handleScoringAndReset();
    }
    
    private void resumeGame() {
        gameState = "RUNNING";
        gameLoopTimer.start(); 
        repaint();
    }
    
    private void handleScoringAndReset() {
        if (!userPlayer.isTagged()) { 
            scoreData++;
            if (scorePanel != null) { scorePanel.updateScore(scoreData); }
        }
        
        resetAllPlayerPositions();
        gameTimer.resetTimer2();
    }

    public void updateGame() {
        
        if (gameTimer.getCount1() > 0) {
            if (gameState.equals("RUNNING")) {
                for (Player player : players) {
                    if (!(player.isUser())) {
                        player.moveRandomly(800, 600); 
                    }
                }
                checkForTags(); 
            }
            
        } else {
            gameLoopTimer.stop();
        }
        
        repaint(); 
    }

    public void checkForTags() {
        boolean tagOccurredThisFrame = false;

        for (Player tagger : players) {
            if (tagger.isTagged() && !tagOccurredThisFrame) { 
                for (Player taggedPlayer : players) {
                    if (tagger != taggedPlayer && !taggedPlayer.isTagged()) {
                        if (isColliding(tagger, taggedPlayer)) {
                            
                            taggedPlayer.setIsTagged(true); 
                            tagger.setIsTagged(false);     
                            
                            initiateRoundBreak(); 
                            
                            tagOccurredThisFrame = true; 
                            break; 
                        }
                    }
                }
            }
        }
    }
    
    private void resetAllPlayerPositions() {
        int npcIndex = 0; 

        for (Player player : players) {
            if (player.isTagged()) {
                player.resetPosition(50, 50);
            } 
            else {
                int newX = 100 + npcIndex * 50;
                int newY = 150;
                player.resetPosition(newX, newY);
                npcIndex++;
            }
            player.resetSpeed(); 
        }
    }
    
    private boolean isColliding(Player p1, Player p2) {
        int dx = p1.getX() - p2.getX();
        int dy = p1.getY() - p2.getY();
        int distanceSquared = dx * dx + dy * dy;
        int collisionDist = p1.getSize() / 2 + p2.getSize() / 2;
        int triggerDistanceSquared = (collisionDist * collisionDist);

        return distanceSquared <= triggerDistanceSquared;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Player player : players) {
            player.draw(g);
        }

        if (gameState.equals("ROUND_BREAK")) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            String message = "Next Round In...";
            String countdownText = String.valueOf(breakCountdown);

            g.drawString(message, getWidth() / 2 - 150, getHeight() / 2 - 50);
            g.drawString(countdownText, getWidth() / 2 - 20, getHeight() / 2 + 20);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameState.equals("RUNNING")) {
            int key = e.getKeyCode();
            int dx = 0;
            int dy = 0;
            if (userPlayer != null) {
                if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) dx = -1;
                else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) dx = 1;
                else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) dy = -1;
                else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) dy = 1;
                userPlayer.move(dx * userPlayer.getSpeed(), dy * userPlayer.getSpeed());
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}

