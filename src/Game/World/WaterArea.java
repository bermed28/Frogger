package Game.World;

import java.awt.Graphics;
import java.util.Random;
import Main.Handler;
import Resources.Animation;
import Resources.Images;

public class WaterArea extends BaseArea {

    private Animation anim;

    WaterArea(Handler handler, int yPosition) {
        super(handler, yPosition);
        // Instantiate the animation of this Water, and it starts it at a random frame.
        anim=new Animation(384,Images.Water,new Random().nextInt(3));

    }

    @Override
    public void tick() {
        anim.tick();	// Animation frame movement.

    }

    @Override
    public void render(Graphics g) {
        for (int i = 0; i < 9; i++) {
            g.drawImage(anim.getCurrentFrame(), i*64, yPosition,64,66, null);
        }
    }
}
