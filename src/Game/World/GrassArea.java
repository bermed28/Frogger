package Game.World;

import java.awt.Graphics;
import Main.Handler;
import Resources.Images;

public class GrassArea extends BaseArea {

    GrassArea(Handler handler, int yPosition) {
        super(handler, yPosition);
    }
    
    @Override
    public void render(Graphics g) {
        for (int i = 0; i < 9; i++) {
            g.drawImage(Images.grass, i*64, yPosition,64,66, null);
        }
    }

}
