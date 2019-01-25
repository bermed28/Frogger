package Game.World;

import java.awt.Color;
import java.awt.Graphics;

import Main.Handler;

public class WaterArea extends BaseArea {
    public WaterArea(Handler handler) {
        super(handler);
    }
    
    @Override
    public void render(Graphics g) {
    	g.setColor(Color.BLUE);
    	g.fillRect(0, yPosition, handler.getWidth(), 64);
    }
}
