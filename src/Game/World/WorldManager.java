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
    
    ArrayList<BaseArea> SpawnedAreas;//Areas currently on world

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

        SpawnedAreas = new ArrayList<>();

        StaticEntitiesAvailables.add(new LillyPad(handler));
        StaticEntitiesAvailables.add(new Log(handler));
        StaticEntitiesAvailables.add(new Tree(handler));

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

    public void tick(){
        for(BaseArea area : SpawnedAreas) {
            area.tick();
        }
    	for(int i=0; i<SpawnedAreas.size(); i++) {
    		SpawnedAreas.get(i).setYPosition(SpawnedAreas.get(i).getYPosition()+movementSpeed);
     		  	//Check if Area passed the screen
    			if(SpawnedAreas.get(i).getYPosition() > handler.getHeight()) {
    				//Replace with a new random area and position it on top
     			   SpawnedAreas.set(i, randomArea(-2*64));
     		   }
    			//Make sure players position is synchronized with area's movement
    			if(SpawnedAreas.get(i).getYPosition() < player.getY() && player.getY()-SpawnedAreas.get(i).getYPosition()<3) {
    				player.setY(SpawnedAreas.get(i).getYPosition());
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
    	}
    	else {
    		randomArea = new EmptyArea(handler, yPosition);
    	}
    	return randomArea;
    }
    
}
