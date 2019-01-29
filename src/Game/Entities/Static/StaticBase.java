package Game.Entities.Static;

import java.awt.Graphics;
import java.awt.Rectangle;

import Game.Entities.EntityBase;
import Game.World.BaseArea;
import Main.Handler;


public class StaticBase extends EntityBase {

    private BaseArea SpawnableArea;

    StaticBase(Handler handler) {
        super(handler);
    }

    /*
     * Draws the hazard, and the Rectangle.
     */
    public void render(Graphics g) {
    	
    }
    
    public void tick() {
    	
    }
    
    public BaseArea getSpawnableArea() {
        return SpawnableArea;
    }

    public void setSpawnableArea(BaseArea spawnableArea) {
        SpawnableArea = spawnableArea;
    }

    /*
     * Obtains the Rectangle of this Area.
     */
	public Rectangle GetCollision() {
		return new Rectangle();
	}
}
