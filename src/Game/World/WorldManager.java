package Game.World;

import Game.Entities.Dynamic.Player;
import Game.Entities.Static.LillyPad;
import Game.Entities.Static.Log;
import Game.Entities.Static.StaticBase;
import Game.Entities.Static.Tree;
import Game.Entities.Static.Turtle;
import Game.Entities.Static.*;
import Game.GameStates.State;
import Main.Handler;
import UI.UIManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Literally the world. This class is very important to understand.
 * Here we spawn our hazards (StaticBase), and our tiles (BaseArea)
 * 
 * We move the screen, the player, and some hazards. 
 * 				How? Figure it out.
 */
public class WorldManager {

	private ArrayList<BaseArea> AreasAvailables;			// Lake, empty and grass area (NOTE: The empty tile is just the "sand" tile. Ik, weird name.)
	private ArrayList<StaticBase> StaticEntitiesAvailables;	// Has the hazards: LillyPad, Log, Tree, and Turtle.

	private ArrayList<BaseArea> SpawnedAreas;				// Areas currently on world
	private ArrayList<StaticBase> SpawnedHazards;			// Hazards currently on world.
    
    Long time;
    Boolean reset = true;
    
    Handler handler;


	private Player player;									// How do we find the frog coordinates? How do we find the Collisions? This bad boy.
    
    UIManager object = new UIManager(handler);
    UI.UIManager.Vector object2 = object.new Vector();


	private ID[][] grid;									
	private int gridWidth,gridHeight;						// Size of the grid. 
	private int movementSpeed;								// Movement of the tiles going downwards.
    

    public WorldManager(Handler handler) {
        this.handler = handler;

        AreasAvailables = new ArrayList<>();				// Here we add the Tiles to be utilized.
        StaticEntitiesAvailables = new ArrayList<>();		// Here we add the Hazards to be utilized.

        AreasAvailables.add(new GrassArea(handler, 0));		
        AreasAvailables.add(new WaterArea(handler, 0));
        AreasAvailables.add(new EmptyArea(handler, 0));

        StaticEntitiesAvailables.add(new LillyPad(handler, 0, 0));
        StaticEntitiesAvailables.add(new Log(handler, 0, 0));
        StaticEntitiesAvailables.add(new Tree(handler, 0, 0));
		StaticEntitiesAvailables.add(new Stone(handler, 0, 0));
        StaticEntitiesAvailables.add(new Turtle(handler, 0, 0));

        SpawnedAreas = new ArrayList<>();
        SpawnedHazards = new ArrayList<>();
        
        player = new Player(handler);       

        gridWidth = handler.getWidth()/64;
        gridHeight = handler.getHeight()/64;
        movementSpeed = 1;
        // movementSpeed = 20; I dare you.
        
        /* 
         * 	Spawn Areas in Map (2 extra areas spawned off screen)
         *  To understand this, go down to randomArea(int yPosition) 
         */
        for(int i=0; i<gridHeight+2; i++) {
        	SpawnedAreas.add(randomArea((-2+i)*64));
        }
        	
        player.setX((gridWidth/2)*64);
        player.setY((gridHeight-3)*64);

        // Not used atm.
        grid = new ID[gridWidth][gridHeight];
        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                grid[x][y]=ID.EMPTY;
            }
        }
    }

	public void tick() {
		
		if(this.handler.getKeyManager().keyJustPressed(this.handler.getKeyManager().num[2])) {
			this.object2.word = this.object2.word + this.handler.getKeyManager().str[1];
		}
		if(this.handler.getKeyManager().keyJustPressed(this.handler.getKeyManager().num[0])) {
			this.object2.word = this.object2.word + this.handler.getKeyManager().str[2];
		}
		if(this.handler.getKeyManager().keyJustPressed(this.handler.getKeyManager().num[1])) {
			this.object2.word = this.object2.word + this.handler.getKeyManager().str[0];
		}
		if(this.handler.getKeyManager().keyJustPressed(this.handler.getKeyManager().num[3])) {
			this.object2.addVectors();
		}
		if(this.handler.getKeyManager().keyJustPressed(this.handler.getKeyManager().num[4]) && this.object2.isUIInstance) {
			this.object2.scalarProduct(handler);
		}
		
		if(this.reset) {
			time = System.currentTimeMillis();
			this.reset = false;
		}
		
		if(this.object2.isSorted) {
			
			if(System.currentTimeMillis() - this.time >= 2000) {		
				this.object2.setOnScreen(true);	
				this.reset = true;
			}
			
		}
		
		for (BaseArea area : SpawnedAreas) {
			area.tick();
		}
		for (StaticBase hazard : SpawnedHazards) {
			hazard.tick();
		}
		
		
		
		for (int i = 0; i < SpawnedAreas.size(); i++) {
			SpawnedAreas.get(i).setYPosition(SpawnedAreas.get(i).getYPosition() + movementSpeed);

			// Check if Area (thus a hazard as well) passed the screen.
			if (SpawnedAreas.get(i).getYPosition() > handler.getHeight()) {
				// Replace with a new random area and position it on top
				SpawnedAreas.set(i, randomArea(-2 * 64));
			}
			//Make sure players position is synchronized with area's movement
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
		//int positionYLast = player.getY();
		//int positionXLast = player.getX();

		for (int i = 0; i < SpawnedHazards.size(); i++) {


			// Moves hazard down
			SpawnedHazards.get(i).setY(SpawnedHazards.get(i).getY() + movementSpeed);

			// Moves Log or Turtle to the right

			if (SpawnedHazards.get(i) instanceof Log) {
				SpawnedHazards.get(i).setX(SpawnedHazards.get(i).getX() + 1);

				// Verifies the hazards Rectangles aren't null and
				// If the player Rectangle intersects with the Log or Turtle Rectangle, then
				// move player to the right.
				if (SpawnedHazards.get(i).GetCollision() != null
						&& player.getPlayerCollision().intersects(SpawnedHazards.get(i).GetCollision())) {
					player.setX(player.getX() + 1);
				}

			}
			if (SpawnedHazards.get(i) instanceof Turtle) {
				SpawnedHazards.get(i).setX(SpawnedHazards.get(i).getX() - 1);

				// Verifies the hazards Rectangles aren't null and
				// If the player Rectangle intersects with the Log or Turtle Rectangle, then
				// move player to the right.
				if (SpawnedHazards.get(i).GetCollision() != null
						&& player.getPlayerCollision().intersects(SpawnedHazards.get(i).GetCollision())) {
					player.setX(player.getX() - 1);
				}

			}
			if (SpawnedHazards.get(i) instanceof Tree) {
                SpawnedHazards.get(i).setX(SpawnedHazards.get(i).getX());

                // Verifies the hazards Rectangles aren't null and
                // If the player Rectangle intersects with the Tree, then
                // move player his original position but it's not working yet.
                if (SpawnedHazards.get(i).GetCollision() != null
                        && player.getPlayerCollision().intersects(SpawnedHazards.get(i).GetCollision())) {
                    player.setX(player.getX()+ 10);
                    player.setY(player.getY()+ 10);


                }
			}

			if (SpawnedHazards.get(i) instanceof Stone) {
				SpawnedHazards.get(i).setX(SpawnedHazards.get(i).getX());

				// Verifies the hazards Rectangles aren't null and
				// If the player Rectangle intersects with the Tree, then
				// move player his original position but it's not working yet.
				if (SpawnedHazards.get(i).GetCollision() != null
						&& player.getPlayerCollision().intersects(SpawnedHazards.get(i).GetCollision())) {
					player.setX(player.getX() + 10);
					player.setY(player.getY() + 10);

				}



			}




            // if hazard has passed the screen height, then remove this hazard.
			if (SpawnedHazards.get(i).getY() > handler.getHeight()) {
				SpawnedHazards.remove(i);
			}

			if (player.getY() > handler.getHeight()) {
				State.setState(handler.getGame().DeathState);
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
    
    /*
     * Given a yPosition, this method will return a random Area out of the Available ones.)
     * It is also in charge of spawning hazards at a specific condition.
     */
	private BaseArea randomArea(int yPosition) {
        Random rand = new Random();

        // From the AreasAvailable, get me any random one.
        BaseArea randomArea = AreasAvailables.get(rand.nextInt(AreasAvailables.size()));

        if (randomArea instanceof GrassArea) {
            randomArea = new GrassArea(handler, yPosition);
            grassHazard(yPosition);

        } else if (randomArea instanceof WaterArea) {
            if(player.getX() == 0){
				randomArea = new GrassArea(handler, yPosition);
			} else {
            	randomArea = new WaterArea(handler, yPosition);
            	SpawnHazard(yPosition);
			}

        } else {
            randomArea = new EmptyArea(handler, yPosition);
        }
        return randomArea;
    }
	/*
	 * Given a yPositionm this method will add a new hazard to the SpawnedHazards ArrayList
	 */
	private void SpawnHazard(int yPosition) {
		Random rand = new Random();
		int randInt;
		int choice = rand.nextInt(7);
		// Chooses between Log or Lillypad
		if (choice <=2) {
			randInt = 64 * rand.nextInt(4);
			SpawnedHazards.add(new Log(handler, randInt, yPosition));
		}
		else if (choice >=5){
			randInt = 64 * rand.nextInt(7);
			SpawnedHazards.add(new LillyPad(handler, randInt, yPosition));
		}
		else {
			randInt = 64 * rand.nextInt(10);
			SpawnedHazards.add(new Turtle(handler, randInt, yPosition));
		}
			
	}

	private void grassHazard(int yPosition) {
		Random rand = new Random();
		int randInt;
		int choice = rand.nextInt(8);

		if (choice <= 7) {
			randInt = 64 * rand.nextInt(10);
			SpawnedHazards.add(new Tree(handler, randInt, yPosition));
		}

		if(choice <= 5) {
            randInt = 64 * rand.nextInt(5);
            SpawnedHazards.add(new Stone(handler, randInt, yPosition));
        }



	}
    
}
