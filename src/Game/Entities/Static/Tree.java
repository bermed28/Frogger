package Game.Entities.Static;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import Main.Handler;
import Resources.Animation;
import Resources.Images;

public class Tree extends StaticBase {
	private Animation anim;
	private Rectangle tree;
	private Random rand;

	public Tree(Handler handler, int xPosition, int yPosition) {
		super(handler);
		this.setX(xPosition);
		this.setY(yPosition);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Images.Tree, this.getX(), this.getY(), 80, 80, null);
		tree = new Rectangle(this.getX(), this.getY()+5, 80, 80);
	}
	
    @Override
    public Rectangle GetCollision() {
    	
    	return tree;
    }
}
