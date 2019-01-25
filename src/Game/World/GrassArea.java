package Game.World;

import java.awt.Color;
import java.awt.Graphics;

import Main.Handler;

public class GrassArea extends BaseArea {
    public GrassArea(Handler handler) {
        super(handler);
    }
    
    @Override
    public void render(Graphics g) {
    	g.setColor(Color.GREEN);
    	g.fillRect(0, yPosition, handler.getWidth(), 64);
    }
}
