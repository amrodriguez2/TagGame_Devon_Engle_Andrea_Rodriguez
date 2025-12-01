import java.awt.Color;
import java.awt.Graphics;

public class ItPlayer extends Player{
	
	public ItPlayer(Color color, int x, int y, boolean isUser) {
		super(color, x, y, true, isUser);
	}
	
	@Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, 30, 30); 
    }

}
