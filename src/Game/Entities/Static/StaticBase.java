package Game.Entities.Static;

import java.awt.Graphics;
import java.awt.Rectangle;

import Game.Entities.EntityBase;
import Game.World.BaseArea;
import Main.Handler;

public class StaticBase extends EntityBase {

    BaseArea SpawnableArea;

    public StaticBase(Handler handler) {
        super(handler);
    }

    public void render(Graphics g) {
    	
    }
    
    
    public BaseArea getSpawnableArea() {
        return SpawnableArea;
    }

    public void setSpawnableArea(BaseArea spawnableArea) {
        SpawnableArea = spawnableArea;
    }

	public Rectangle GetCollision() {
		return new Rectangle();
	}
}
