
import java.awt.Color;
import java.awt.Graphics;

class NotItPlayer extends Player {
    public NotItPlayer(Color color, int x, int y, boolean isUser) {
		super(color, x, y, true, isUser);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, 30, 30); 
    }
}
