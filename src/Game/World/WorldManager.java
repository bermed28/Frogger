package Game.World;

import Game.Entities.Dynamic.Player;
import Game.Entities.Static.LillyPad;
import Game.Entities.Static.Log;
import Game.Entities.Static.StaticBase;
import Game.Entities.Static.Tree;
import Main.Handler;
import Resources.Images;
import UI.UIManager;
import UI.UIObject;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class WorldManager {

    ArrayList<BaseArea> AreasAvailables;//Lake, empty and grass area
    ArrayList<StaticBase> StaticEntitiesAvailables;//trees, lillies, logs
    
    ArrayList<BaseArea> SpawnedAreas;		//Areas currently on world
    ArrayList<StaticBase> SpawnedHazards;	//Hazards currently on world.
    
    Handler handler;

    Player player;
    
    UIManager object = new UIManager(handler);
    UI.UIManager.JJMP object2 = object.new JJMP();

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
        movementSpeed = 1;
        
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

	public void tick() {
		
		if(this.handler.getKeyManager().keyJustPressed(KeyEvent.VK_J))  this.object2.code = this.object2.code + "J";
		if(this.handler.getKeyManager().keyJustPressed(KeyEvent.VK_M)) this.object2.code = this.object2.code + "M";
		if(this.handler.getKeyManager().keyJustPressed(KeyEvent.VK_P)) this.object2.code = this.object2.code + "P";
		if(this.handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)) this.object2.checkCode();
		
		if(handler.getKeyManager().JJMPButton && this.object2.codeEnteredCorrectly) this.object2.onScreen = true;		
		
		for (BaseArea area : SpawnedAreas) {
			area.tick();
		}

		for (int i = 0; i < SpawnedAreas.size(); i++) {
			SpawnedAreas.get(i).setYPosition(SpawnedAreas.get(i).getYPosition() + movementSpeed);

			// Check if Area (thus a hazard as well) passed the screen.
			if (SpawnedAreas.get(i).getYPosition() > handler.getHeight()) {
				// Replace with a new random area and position it on top
				SpawnedAreas.set(i, randomArea(-2 * 64));
			}
			if (SpawnedAreas.get(i).getYPosition() < player.getY()
					&& player.getY() - SpawnedAreas.get(i).getYPosition() < 3) {
				player.setY(SpawnedAreas.get(i).getYPosition());
			}
		}
		
		HazardMovement();
		
        player.tick();
        //make player move the same as the areas
        player.setY(player.getY()+movementSpeed); 
        
        object2.tick();
   
    }

	private void HazardMovement() {

		for (int i = 0; i < SpawnedHazards.size(); i++) {

			// Moves hazard down
			SpawnedHazards.get(i).setY(SpawnedHazards.get(i).getY() + movementSpeed);

			// Verifies the hazards aren't null
			if (SpawnedHazards.get(i).GetCollision() != null) {
				// If hazard is a log, and the player Rectangle intersects with the log Rectangle, then move player to the right.
				if (SpawnedHazards.get(i) instanceof Log && player.getPlayerCollision().intersects(SpawnedHazards.get(i).GetCollision())) {
					player.setX(player.getX() + 1);
				}
			}
			
			// Moves log to the right
			if (SpawnedHazards.get(i) instanceof Log) {
				SpawnedHazards.get(i).setX(SpawnedHazards.get(i).getX() + 1);
			}

			// if hazard has passed the screen height, then remove this hazard.
			if (SpawnedHazards.get(i).getY() > handler.getHeight()) {
				SpawnedHazards.remove(i);
			}

		}
	}
	
	
    public void render(Graphics g){
    	
       for(BaseArea area : SpawnedAreas) {
    	   area.render(g);
       }
    	
       for (StaticBase hazards : SpawnedHazards) {
    		hazards.render(g);
 
       }
    	
       player.render(g);
       
       this.object2.render(g);      

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
