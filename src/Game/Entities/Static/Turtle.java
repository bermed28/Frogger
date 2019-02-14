package Game.Entities.Static;

import Main.Handler;
import Resources.Animation;
import Resources.Images;

import java.awt.*;
import java.util.Random;

public class Turtle extends StaticBase {
	
	private Animation anim;
	private Rectangle turtle;
	private Random rand;

	public Turtle(Handler handler, int xPosition, int yPosition) {
		super(handler);
		 // Sets original position to be this one.
		this.setX(xPosition);
		this.setY(yPosition);
		rand = new Random();
		// Instantiate the animation of this Turtle, and starts it at a random frame.
		anim = new Animation(200, Images.Turtle, rand.nextInt(20));
	}

	
	@Override
	public void tick() {
		anim.tick();	// Animation frame movement.
	}
	
	@Override
	public void render(Graphics g) { 
			
			if (!WentUnderWater()) {
				g.drawImage(anim.getCurrentFrame(), this.getX(), this.getY(), -1*80, 64, null);
				turtle = new Rectangle(this.getX()-10, this.getY(), 20, 55);
			}
			else {
				g.drawImage(anim.getCurrentFrame(), this.getX(), this.getY(), -1*80, 64, null);
				turtle = new Rectangle();
			}


				
	}
	
    @Override
    public Rectangle GetCollision() {
    	return turtle;
    }
    
    /*
     * Verifies the frame the Turtle is underground.
     */
    public boolean WentUnderWater(){
    	
    	int frame = anim.getIndex();
    	
    	if (frame >= 4 && frame <= 17){
    		return false;
    	}
    	else
    		return true;
    }
}
