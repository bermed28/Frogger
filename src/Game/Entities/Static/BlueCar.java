
package Game.Entities.Static;

import java.awt.Graphics;
import java.awt.Rectangle;

import Main.Handler;
import Resources.Images;

public class BlueCar extends StaticBase {

    private Rectangle bluecar;

    public BlueCar(Handler handler,int xPosition, int yPosition) {
        super(handler);
        // Sets original position to be this one.
        this.setX(xPosition);
        this.setY(yPosition);
    }

    @Override
    public void render(Graphics g) {

        g.drawImage(Images.BlueCar, this.getX(), this.getY(), -1*125, 64, null);
        bluecar = new Rectangle(this.getX()-125, this.getY(), 125, 60);
    }

    @Override
    public Rectangle GetCollision() {
        return bluecar;
    }
}
