
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Player { 
    protected Color color;
    protected int x, y;
    protected int speed = 5; 
    protected boolean isTagged; 
    protected static final int SIZE = 30;
    private float speedX, speedY;
    private final float SPEED_MAGNITUDE = 2.0f; 
    private Random random = new Random();
    private boolean isUser = false;

    public Player(boolean isUser) {
        this.isUser = isUser;
    }
    
    public boolean isUser() {
        return isUser;
    }
    
    public Player(Color color, int x, int y, boolean isTagged, boolean isUser) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.isTagged = isTagged;
        this.isUser = isUser;
        this.speed = 5;
        double angle = random.nextDouble() * 2 * Math.PI;
        this.speedX = (float) (Math.cos(angle) * SPEED_MAGNITUDE);
        this.speedY = (float) (Math.sin(angle) * SPEED_MAGNITUDE);
    }
    
    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public void moveRandomly(int mapWidth, int mapHeight) {
        if (random.nextInt(100) < 5) { 
            double angle = random.nextDouble() * 2 * Math.PI;
            this.speedX = (float) (Math.cos(angle) * SPEED_MAGNITUDE);
            this.speedY = (float) (Math.sin(angle) * SPEED_MAGNITUDE);
        }

        int nextX = (int) (this.x + this.speedX);
        int nextY = (int) (this.y + this.speedY);

        if (nextX <= 0 || nextX >= mapWidth - 50) { 
            this.speedX *= -1;
            nextX = (int) (this.x + this.speedX); 
        }
        if (nextY <= 0 || nextY >= mapHeight - 50) { 
            this.speedY *= -1;
            nextY = (int) (this.y + this.speedY); 
        }

        this.x = nextX;
        this.y = nextY;
    }
    
    public void resetPosition(int newX, int newY) {
        this.x = newX;
        this.y = newY;
        this.speedX = 0; 
        this.speedY = 0;
    }
    
    public void resetSpeed() {
        this.speedX = 0;
        this.speedY = 0;
    }
    
    public void draw(Graphics g) {
g.setColor(this.color);
        
        if (this.isTagged) {
            g.fillOval(x, y, 30, 30); 
        } else {
            g.fillRect(x, y, 30, 30); 
        }
        
        if (this.isUser) {
            g.setColor(Color.WHITE);
            if (this.isTagged) {
                 g.drawOval(x, y, 30, 30);
            } else {
                 g.drawRect(x, y, 30, 30);
            }
        }
    }
    
    public int getX() { 
    	return x; 
    }
    public void setX(int x) { 
    	this.x = x; 
    }
    public int getY() { 
    	return y; 
    }
    public void setY(int y) { 
    	this.y = y; 
    }
    public int getSize() { 
    	return SIZE; 
    }
    public int getSpeed() { 
    	return speed; 
    }
    public boolean isTagged() { 
    	return isTagged; 
    }
    public void setIsTagged(boolean tagged) { 
    	this.isTagged = tagged; 
    }
}

