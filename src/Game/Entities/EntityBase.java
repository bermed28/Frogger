package Game.Entities;

import Main.Handler;

import java.awt.*;

public class EntityBase {


    Handler handler;
    private int height=64,width=64,x,y;


    public EntityBase( Handler handler) {
        this.handler = handler;
    }

    public void tick(){

    }

    public void render(Graphics g){

    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
