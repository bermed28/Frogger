package Game.World;

import java.awt.Color;
import java.awt.Graphics;

import Main.Handler;
import Resources.Images;

public class WaterArea extends BaseArea {
    public WaterArea(Handler handler, int yPosition) {
        super(handler, yPosition);
    }
    
    @Override
    public void render(Graphics g) {
    	g.drawImage(Images.waterArea, 0, yPosition, null);
    	
    }
}
