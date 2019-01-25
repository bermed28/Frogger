package Game.World;

import java.awt.Color;
import java.awt.Graphics;

import Main.Handler;

public class EmptyArea extends BaseArea {
    public EmptyArea(Handler handler) {
        super(handler);
    }
    
    @Override
    public void render(Graphics g) {
    	g.setColor(Color.GRAY);
    	g.fillRect(0, yPosition, handler.getWidth(), 64);
    }
}
