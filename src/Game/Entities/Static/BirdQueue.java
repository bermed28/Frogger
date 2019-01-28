package Game.Entities.Static;

import java.awt.Graphics;

import Main.Handler;

public class BirdQueue {
	
	Handler handler;

	public int xPos;
	public int yPos;
	public int width = 1;
	public int height = 1;
	
	public BirdQueue(int x, int y) {
		
		this.xPos = x;
		this.yPos = y;
		
	}
	
	public void render(Graphics g) {
		
		g.drawRect(this.xPos, this.yPos, this.width, this.height);
		
	}
	
	public void tick() {

		this.setyPos(this.getyPos() + 1);
		
		if(this.getyPos() >= handler.getGame().getHeight() * 2) {
			
			this.setyPos(1);
			
		}
		
	}

	public int getxPos() {
		return xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public void setyPos(int yPox) {
		this.yPos = yPox;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
}
