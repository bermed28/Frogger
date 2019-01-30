package Game.Entities;

import Main.Handler;

import java.awt.*;
import java.util.ArrayList;

public class EntityManager {

    Handler handler;

    private ArrayList<EntityBase> entityList;

    public EntityManager(Handler handler) {
        this.handler = handler;
        entityList = new ArrayList<>();
    }

    public void tick(){
        for (EntityBase e:entityList) {
            e.tick();
        }
    }

    public void render(Graphics g){
        for (EntityBase e:entityList) {
            e.render(g);
        }
    }

    public ArrayList<EntityBase> getEntityList() {
        return entityList;
    }

    public void setEntityList(ArrayList<EntityBase> entityList) {
        this.entityList = entityList;
    }
    public static String getLtr() {
    	return "P";
    }
}
