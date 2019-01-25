package Game.Entities.Dynamic;

import Game.Entities.EntityBase;
import Main.Handler;
import Resources.Images;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends EntityBase {
    Handler handler;
    public Player(Handler handler) {
        super(handler);
        this.handler = handler;
        this.handler.getEntityManager().getEntityList().add(this);

    }

    public void tick(){

        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)){
            setY(getY()-64);
        }
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_A)){
            setX(getX()-64);
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)){
            setY(getY()+64);
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_D)){
            setX(getX()+64);
        }
    }

    public void render(Graphics g){

       g.drawImage(Images.player, getX(), getY(), getWidth(), getHeight(), null);
    }
}
