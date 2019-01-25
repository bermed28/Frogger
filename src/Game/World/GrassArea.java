package Game.World;

import java.awt.Color;
import java.awt.Graphics;

import Main.Handler;
import Resources.Images;

public class GrassArea extends BaseArea {
    public GrassArea(Handler handler, int yPosition) {
        super(handler, yPosition);
    }
    
    @Override
    public void render(Graphics g) {
    	g.drawImage(Images.grassArea, 0, yPosition, null);
    }
}
