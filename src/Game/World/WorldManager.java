package Game.World;

import Game.Entities.Dynamic.Player;
import Game.Entities.Static.LillyPad;
import Game.Entities.Static.Log;
import Game.Entities.Static.PowerUp;
import Game.Entities.Static.Car;
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
	private ArrayList<StaticBase> StaticEntitiesAvailables;// Has the hazards: LillyPad, Log, Tree, and Turtle.

	private ArrayList<BaseArea> SpawnedAreas;				// Areas currently on world
	private ArrayList<StaticBase> SpawnedHazards;			// Hazards currently on world.
    
    Long time;
    Boolean reset = true;
    WaterArea waterArea;


    
    Handler handler;


	private Player player; // How do we find the frog coordinates? How do we find the Collisions? This bad boy.

    UIManager object = new UIManager(handler);
    UI.UIManager.Vector object2 = object.new Vector();


	private ID[][] grid;									
	private int gridWidth,gridHeight;						// Size of the grid. 
	private int movementSpeed;								// Movement of the tiles going downwards.
    private int i;

	public WorldManager(Handler handler) {
        this.handler = handler;

        AreasAvailables = new ArrayList<>();				// Here we add the Tiles to be utilized.
        StaticEntitiesAvailables = new ArrayList<>();		// Here we add the Hazards to be utilized.

        AreasAvailables.add(new GrassArea(handler, 0));		
        AreasAvailables.add(new WaterArea(handler, 0));
        AreasAvailables.add(new EmptyArea(handler, 0));
		AreasAvailables.add(new RoadArea(handler, 0));

        StaticEntitiesAvailables.add(new LillyPad(handler, 0, 0));
        StaticEntitiesAvailables.add(new Log(handler, 0, 0));
        StaticEntitiesAvailables.add(new Tree(handler, 0, 0));
		StaticEntitiesAvailables.add(new Stone(handler, 0, 0));
        StaticEntitiesAvailables.add(new Turtle(handler, 0, 0));
        StaticEntitiesAvailables.add(new Car(handler, 0, 0));
		StaticEntitiesAvailables.add(new PowerUp(handler, 0, 0));


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
		//waterDeath();
        player.tick();
        //make player move the same as the areas
        player.setY(player.getY()+movementSpeed); 
        
        object2.tick();



	}

	private void HazardMovement() {

		for (int i = 0; i < SpawnedHazards.size(); i++) {
			// Moves hazard down
			SpawnedHazards.get(i).setY(SpawnedHazards.get(i).getY() + movementSpeed);

			// Moves Log or Turtle to the right

			if (SpawnedHazards.get(i) instanceof Log) {
				SpawnedHazards.get(i).setX(SpawnedHazards.get(i).getX() + 2);
				if(SpawnedHazards.get(i).getX() >704) {
					SpawnedHazards.get(i).setX(-128);
				}

				// Verifies the hazards Rectangles aren't null and
				// If the player Rectangle intersects with the Log Rectangle, then
				// move player to the right.
				if (SpawnedHazards.get(i).GetCollision() != null
						&& player.getPlayerCollision().intersects(SpawnedHazards.get(i).GetCollision())&& player.getX()<576) {
					player.setX(player.getX() + 2);
				}
			}
			if (SpawnedHazards.get(i) instanceof Turtle) {
				SpawnedHazards.get(i).setX(SpawnedHazards.get(i).getX() - 2);
				if(SpawnedHazards.get(i).getX() < -128){
					SpawnedHazards.get(i).setX(576);
				}
				// Verifies the hazards Rectangles aren't null and
				// If the player Rectangle intersects with the Turtle Rectangle, then
				// move player to the right.
				if (SpawnedHazards.get(i).GetCollision() != null
						&& player.getPlayerCollision().intersects(SpawnedHazards.get(i).GetCollision())&&player.getX()>0) {
					player.setX(player.getX() - 2);

				}

			}

            if (SpawnedHazards.get(i) instanceof Car) {
                SpawnedHazards.get(i).setX(SpawnedHazards.get(i).getX() + 2);
                if(SpawnedHazards.get(i).getX() >704) {
                    SpawnedHazards.get(i).setX(-128);
                }

                // Verifies the hazards Rectangles aren't null and
                // If the player Rectangle intersects with the Car Rectangle, then
                // it kills the player.
                if (SpawnedHazards.get(i).GetCollision() != null
                        && player.getPlayerCollision().intersects(SpawnedHazards.get(i).GetCollision())) {
                    State.setState(handler.getGame().deathState);
                }
            }

			if (SpawnedHazards.get(i) instanceof Tree) {
                SpawnedHazards.get(i).setX(SpawnedHazards.get(i).getX());
                if (SpawnedHazards.get(i).GetCollision() != null
                        && player.getPlayerCollision().intersects(SpawnedHazards.get(i).GetCollision())) {
                	if(player.facing.equals("UP")) {
						player.setY(player.getY() + 64);
						player.scoreTracker--;
						Player.score--;
					}if(player.facing.equals("DOWN")) {
						player.setY(player.getY() -64);
						player.scoreTracker++;
					}if(player.facing.equals("LEFT")) {
						player.setX(player.getX() + 8);
					}if(player.facing.equals("RIGHT")) {
						player.setX(player.getX() - 8);
					}
				}
			}

			if (SpawnedHazards.get(i) instanceof Stone) {
				SpawnedHazards.get(i).setX(SpawnedHazards.get(i).getX());
				if (SpawnedHazards.get(i).GetCollision() != null
						&& player.getPlayerCollision().intersects(SpawnedHazards.get(i).GetCollision())) {
					if(player.facing.equals("UP")) {
						player.setY(player.getY() + 8);
					}if(player.facing.equals("DOWN")) {
						player.setY(player.getY() -8);
					}if(player.facing.equals("LEFT")) {
						player.setX(player.getX() + 8);
					}if(player.facing.equals("RIGHT")) {
						player.setX(player.getX() - 8);
					}
				}
			}

            // if hazard has passed the screen height, then remove this hazard.
			if (SpawnedHazards.get(i).getY() > handler.getHeight()) {
				SpawnedHazards.remove(i);
			}

			if (player.getY() > handler.getHeight()) {
				State.setState(handler.getGame().deathState);
			}
		}

		for(int i = 0; i < SpawnedHazards.size(); i++) {
			if (SpawnedHazards.get(i) instanceof PowerUp) {
				PowerUp p = (PowerUp) (SpawnedHazards.get(i));
				if (SpawnedHazards.get(i).GetCollision() != null
						&& player.getPlayerCollision().intersects(SpawnedHazards.get(i).GetCollision()) && p.notCollected) {
					player.scoreTracker += 2.5;
					Player.score += 2.5;
					p.notCollected = false;
					SpawnedHazards.remove(i);
				}
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
			if(player.getX() == 0){
				randomArea = new EmptyArea(handler, yPosition);
			} else {
				randomArea = new GrassArea(handler, yPosition);
				grassHazard(yPosition);
			}

        } else if (randomArea instanceof WaterArea) {
            if(player.getX() == 0){
				randomArea = new EmptyArea(handler, yPosition);
			} else {
				randomArea = new WaterArea(handler, yPosition);
				//Used to not spawn two lily pad in consecutive Y positions
				//i used to check if a lilly pad spawned before
				if (i == 1) {
					TurtleLogHazard(yPosition);
				} else {
					SpawnHazard(yPosition);
				}

			}
        } else if (randomArea instanceof RoadArea) {
			randomArea = new RoadArea(handler, yPosition);
            spawnCar(yPosition);
		} else {
            randomArea = new EmptyArea(handler, yPosition);
            powerUpSpawn(yPosition);
        }
        return randomArea;
    }
	/*
	 * Given a yPositionm this method will add a new hazard to the SpawnedHazards ArrayList
	 */
	private void SpawnHazard(int yPosition) {
		i = 0;
		Random rand = new Random();
		int randInt;
		int choice = rand.nextInt(10);
		// Chooses between Log or Lillypad
		if (choice <=2) {
			randInt = 64 * rand.nextInt(4);
			SpawnedHazards.add(new Turtle(handler, randInt, yPosition));
		}
		else if (choice <=5) {
		    i = 1;
            randInt = 64 * rand.nextInt(9);
            SpawnedHazards.add(new LillyPad(handler, randInt, yPosition));
            for (int X = 0; X < 7; X++) {
                SpawnedHazards.add(new LillyPad(handler, randInt, yPosition));
                randInt = 64 * rand.nextInt(9);
            }
        }
		else{
			SpawnedHazards.add(new Log(handler, 0, yPosition));
			int extraBlock = 0;
			for (int X = 0; X < 3; X++) {
				extraBlock -=224;
				SpawnedHazards.add(new Log(handler, extraBlock, yPosition));
			}
		}
	}
	private void TurtleLogHazard(int yPosition) {
		Random rand = new Random();
		int randInt;
		int choice = rand.nextInt(10);
		i = 0; //makes sure 2 lilly's dont spawn in two consecutive Y rows, DO NOT DELETE THIS
		// Chooses between Log or Turtle
		if (choice <=2) {
			randInt = 64 * rand.nextInt(4);
			SpawnedHazards.add(new Turtle(handler, randInt, yPosition));
		}
		else {
			SpawnedHazards.add(new Log(handler, 0, yPosition));
			int extraBlock = 0;
			for (int X = 0; X < 3; X++) {
				extraBlock -= 192;
				SpawnedHazards.add(new Log(handler, extraBlock, yPosition));
			}
		}
	}

	private void spawnCar(int yPosition) {
	    Random rand = new Random();
	    int randInt = rand.nextInt(2);
	    int extraBlock = 0;
        for (int X = 0; X <= randInt; X++) {
            extraBlock -= 192;
            SpawnedHazards.add(new Car(handler, extraBlock, yPosition));
        }
    }

	private void grassHazard(int yPosition) {
		Random rand = new Random();
		int randInt;
		int choice = rand.nextInt(8);

		if (choice <= 7) {
			randInt = 64 * rand.nextInt(8);
			for (int X = 0; X < 5; X++) {
				SpawnedHazards.add(new Tree(handler, randInt, yPosition));
				randInt = 64 * rand.nextInt(8);
			}
		}
		if(choice <= 5) {
            randInt = 64 * rand.nextInt(2);
            SpawnedHazards.add(new Stone(handler, randInt, yPosition));
        }
	}

	public void powerUpSpawn(int yPosition) {
	    Random rand = new Random();
	    int randInt;
        randInt = 64 * rand.nextInt(9);
        for (int X = 0; X <= 1; X++) {
            SpawnedHazards.add(new PowerUp(handler, randInt, yPosition));
            randInt = 64 * rand.nextInt(5);
        }
	}

	public void waterDeath() {
		for(int i = 0 ; i < SpawnedAreas.size(); i++) {
			if(SpawnedAreas.get(i) instanceof WaterArea && player.getY() == SpawnedAreas.get(i).getYPosition()) {
				for(int  j = 0 ; j < SpawnedHazards.size();j++) {
					if(SpawnedHazards.get(j).getX()  <= player.getX() &&
							(SpawnedHazards.get(j).getX() + 5) >= (player.getX() + handler.getWidth())
							&& SpawnedHazards.get(j).getY() + 5 <= player.getY()
							&& SpawnedHazards.get(j).getY() -5 >= (player.getY() + handler.getHeight())){
						return;
					}
					State.setState(handler.getGame().deathState);
				}
			}
		}
	}

}
