package Game.World;

import java.awt.Color;
import java.awt.Graphics;

import Main.Handler;
import Resources.Images;

public class EmptyArea extends BaseArea {
    public EmptyArea(Handler handler, int yPosition) {
        super(handler, yPosition);
    }
    
    @Override
    public void render(Graphics g) {
    	g.drawImage(Images.emptyArea, 0, yPosition, null);
    }
}
