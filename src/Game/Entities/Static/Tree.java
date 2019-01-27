package Game.Entities.Static;

import java.awt.Graphics;
import java.awt.Rectangle;

import Main.Handler;

public class Tree extends StaticBase {

	public Tree(Handler handler) {
		super(handler);
	}

	@Override
	public void render(Graphics g) {

	}
	
    @Override
    public Rectangle GetCollision() {
    	
    	return new Rectangle();
    }
}
