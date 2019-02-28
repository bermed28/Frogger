package Game.Entities;

import Main.Handler;

import java.awt.*;

public class EntityBase {


    Handler handler;
    private int height=64,width=64,x,y;
    private Rectangle bounds;



    public EntityBase(Handler handler) {
        this.handler = handler;
    }

    public void tick(){

    }

    public void render(Graphics g){

    }


    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }


    public int getWidth() {
        return width;
    }


    public int getX() {
        return x;
    }



    public int getY() {
        return y;
    }


}
