package Game.World;

import java.awt.Graphics;

import Main.Handler;

public class BaseArea {
    Handler handler;

    int yPosition;

    boolean canStepOn = true;

    public BaseArea(Handler handler) {
        this.handler = handler;
    }
    
    public void render(Graphics g) {
    	
    }
    
    public int getYPosition() {
    	return this.yPosition;
    }
    
    public void setYPosition(int position) {
    	this.yPosition = position;
    }
}
