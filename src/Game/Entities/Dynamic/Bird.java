package Game.Entities.Dynamic;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import Main.Handler;
import Resources.Images;

public class Bird {

	Handler handler;
	Random random = new Random();

	public boolean onScreen = false;

	private int side = 1;

	private int speed = 8;
	private int xPos;
	private int yPos;
	private int width = 200;
	private int height = 160;

	public Bird() {

		if(this.side == 0) {
			
			this.setxPos(0 - this.getWidth());
			
		}
		
		else {
			
			this.setxPos(576 + this.getWidth());
			
		}
		
		this.setyPos(random.nextInt(768 - 100) + 100);

	}

	public void render(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;

		if(this.side == 0) {

			g2.drawImage(Images.bird[0], this.getxPos(), this.getyPos(), this.getWidth(), this.getHeight(), null);

		}

		else {

			g2.drawImage(Images.bird[1], this.getxPos(), this.getyPos(), this.getWidth(), this.getHeight(), null);

		}

	}

	public void tick() {

		if(this.side == 0) {

			if(this.xPos >= 768) {

				this.onScreen = false;

				this.setxPos(0 - this.width);

				this.setyPos(random.nextInt(550 - 100) + 100);
				
				this.side = random.nextInt(2);

			}

			if(this.onScreen) {

				this.setxPos(this.getxPos() + this.getSpeed());

			}

		}
		
		else {
			
			if(this.xPos + this.getWidth() <= 0) {

				this.onScreen = false;

				this.setxPos(576 + this.width);

				this.setyPos(random.nextInt(550 - 100) + 100);	
				
				this.side = random.nextInt(2);

			}

			if(this.onScreen) {

				this.setxPos(this.getxPos() - this.getSpeed());

			}
			
		}

	}

	public int getSpeed() {
		return speed;
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
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public void setxPos(int xPos) {
		this.xPos = xPos;
	}
	public void setyPos(int yPos) {
		this.yPos = yPos;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public void setHeight(int height) {
		this.height = height;
	}

}
