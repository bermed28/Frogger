package Game.World;

import Game.Entities.Dynamic.Player;
import Game.Entities.Static.LillyPad;
import Game.Entities.Static.Log;
import Game.Entities.Static.StaticBase;
import Game.Entities.Static.Tree;
import Main.Handler;

import java.awt.*;
import java.util.ArrayList;

public class WorldManager {

    ArrayList<BaseArea> AreasAvailables;//Lake, empty and grass area
    ArrayList<StaticBase> StaticEntitiesAvailables;//trees, lillies, logs

    Handler handler;

    Player player;

    ID[][] grid;
    int gridWidth,gridHeight;


    public WorldManager(Handler handler) {
        this.handler = handler;

        AreasAvailables = new ArrayList<>();
        StaticEntitiesAvailables = new ArrayList<>();

        AreasAvailables.add(new GrassArea(handler));
        AreasAvailables.add(new WaterArea(handler));
        AreasAvailables.add(new EmptyArea(handler));


        StaticEntitiesAvailables.add(new LillyPad(handler));
        StaticEntitiesAvailables.add(new Log(handler));
        StaticEntitiesAvailables.add(new Tree(handler));

        player = new Player(handler);

        gridWidth = handler.getWidth()/64;
        gridHeight = handler.getHeight()/64;

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

        player.tick();
    }

    public void render(Graphics g){
        player.render(g);
    }
}
