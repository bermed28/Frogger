package Game.Entities.Static;

import java.awt.Graphics;
import java.util.Random;

import Game.Entities.EntityBase;
import Main.Handler;
import Resources.Images;

public class Log extends StaticBase {
	
	
    public Log(Handler handler,int xPosition, int yPosition) {
        super(handler);
        this.setX(xPosition);
        this.setY(yPosition);
    }
    
    @Override
    public void render(Graphics g) {
    								// Make width vary
    	g.drawImage(Images.log, this.getX(), this.getY(), 128, 64, null);
    }
}
