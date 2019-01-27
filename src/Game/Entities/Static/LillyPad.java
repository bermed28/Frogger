package Game.Entities.Static;

import java.awt.Graphics;

import Main.Handler;
import Resources.Images;

public class LillyPad extends StaticBase {
	
    public LillyPad(Handler handler,int xPosition, int yPosition) {
        super(handler);
        this.setY(yPosition);
        this.setX(xPosition);
    }
    
    @Override
    public void render(Graphics g) {
    	
    	g.drawImage(Images.lilly, this.getX(), this.getY(), 64, 64, null);
    }
}
