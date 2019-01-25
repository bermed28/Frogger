package Game.World;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import Main.Handler;
import Resources.Animation;
import Resources.Images;

public class WaterArea extends BaseArea {
    Animation anim;
    public WaterArea(Handler handler, int yPosition) {
        super(handler, yPosition);
        anim=new Animation(384,Images.Water,new Random().nextInt(3));
    }

    @Override
    public void tick() {
        anim.tick();
    }

    @Override
    public void render(Graphics g) {
        for (int i = 0; i < 9; i++) {
            g.drawImage(anim.getCurrentFrame(), i*64, yPosition,64,64, null);

        }

    }
}
