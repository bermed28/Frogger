package Game.World;

import Game.Entities.Dynamic.Player;
import Game.Entities.Static.LillyPad;
import Game.Entities.Static.Log;
import Game.Entities.Static.StaticBase;
import Game.Entities.Static.Tree;
import Main.Handler;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class WorldManager {

    ArrayList<BaseArea> AreasAvailables;//Lake, empty and grass area
    ArrayList<StaticBase> StaticEntitiesAvailables;//trees, lillies, logs
    
    ArrayList<BaseArea> SpawnedAreas;		//Areas currently on world
    ArrayList<StaticBase> SpawnedHazards;	//Hazards currently on world.
    
    Handler handler;

    Player player;

    ID[][] grid;
    int gridWidth,gridHeight;
    int movementSpeed;
    

    public WorldManager(Handler handler) {
        this.handler = handler;

        AreasAvailables = new ArrayList<>();
        StaticEntitiesAvailables = new ArrayList<>();

        AreasAvailables.add(new GrassArea(handler, 0));
        AreasAvailables.add(new WaterArea(handler, 0));
        AreasAvailables.add(new EmptyArea(handler, 0));

        StaticEntitiesAvailables.add(new LillyPad(handler, 0, 0));
        StaticEntitiesAvailables.add(new Log(handler, 0, 0));
        StaticEntitiesAvailables.add(new Tree(handler));

        SpawnedAreas = new ArrayList<>();
        SpawnedHazards = new ArrayList<>();
        
        player = new Player(handler);

        gridWidth = handler.getWidth()/64;
        gridHeight = handler.getHeight()/64;
        movementSpeed = 2;
        
        //Spawn Areas in Map (2 extra areas spawned off screen)
        for(int i=0; i<gridHeight+2; i++) {
        	SpawnedAreas.add(randomArea((-2+i)*64));
        }
        	
        player.setX((gridWidth/2)*64);
        player.setY((gridHeight-3)*64);

        grid = new ID[gridWidth][gridHeight];
        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                grid[x][y]=ID.EMPTY;
            }
        }
    }

    public void tick(){
        for(BaseArea area : SpawnedAreas) {
            area.tick();
        }
        
    	for(int i=0; i<SpawnedAreas.size(); i++) {
    		SpawnedAreas.get(i).setYPosition(SpawnedAreas.get(i).getYPosition()+movementSpeed);
    		
     		  	//Check if Area (thus a hazard as well) passed the screen.
    			if(SpawnedAreas.get(i).getYPosition() > handler.getHeight()) {
    				//Replace with a new random area and position it on top
     			   SpawnedAreas.set(i, randomArea(-2*64));
     		   }
    			if(SpawnedAreas.get(i).getYPosition() < player.getY() && player.getY()-SpawnedAreas.get(i).getYPosition()<3) {
    				player.setY(SpawnedAreas.get(i).getYPosition());
    			}
    			
    			if (!(SpawnedHazards.size() <= i)) {
    				SpawnedHazards.get(i).setY(SpawnedHazards.get(i).getY() + movementSpeed);
    				
    				if (SpawnedHazards.get(i) instanceof Log){
    				SpawnedHazards.get(i).setX(SpawnedHazards.get(i).getX() + (movementSpeed / 2));
    				}
    				
    				// if hazard has passed the screen height, then remove this hazard.
    				if(SpawnedHazards.get(i).getY() > handler.getHeight()) {
    					SpawnedHazards.remove(i);
    				}
    			}
        }
    	
    	
    
        player.tick();
        //make player move the same as the areas
        player.setY(player.getY()+movementSpeed);
    }

    public void render(Graphics g){
       for(BaseArea area : SpawnedAreas) {
    	   area.render(g);
       }
    	
       for (StaticBase hazards : SpawnedHazards) {
    		hazards.render(g);
       }
    	
       player.render(g);
    }
    
    //Returns a random area
    public BaseArea randomArea(int yPosition) {
    	Random rand = new Random();
    	BaseArea randomArea = AreasAvailables.get(rand.nextInt(AreasAvailables.size()));
    	if(randomArea instanceof GrassArea) {
    		randomArea = new GrassArea(handler, yPosition);
    	}
    	else if(randomArea instanceof WaterArea) {
    		randomArea = new WaterArea(handler, yPosition);
    		SpawnHazard(yPosition);
    	}
    	else {
    		randomArea = new EmptyArea(handler, yPosition);
    	}
    	return randomArea;
    }

    // TODO:
    //	1. Add Tree choice implementation
    // 	2. Deny two Lillypads from spawning one after the other.
	//	3. Add multiple Logs with same speed in the same AreaRow.
	private void SpawnHazard(int yPosition) {
		Random rand = new Random();
		int randInt;
		
		// Chooses between Log or Lillypad
		if (rand.nextBoolean()) {
			randInt = 64 * rand.nextInt(4);
    		SpawnedHazards.add(new Log(handler, randInt, yPosition));
		}
		else {
			randInt = 64 * rand.nextInt(9);
			SpawnedHazards.add(new LillyPad(handler, randInt, yPosition));
		}
	}
    
    
    
}
