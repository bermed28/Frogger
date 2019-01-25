package Game.GameStates;

import Game.Entities.EntityManager;
import Game.World.WorldManager;
import Main.Handler;
import java.awt.*;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class GameState extends State {


    public GameState(Handler handler){
        super(handler);
        handler.setEntityManager(new EntityManager(handler));
        handler.setWorldManager(new WorldManager(handler));


    }


    @Override
    public void tick() {

        handler.getWorld().tick();

    }

    @Override
    public void render(Graphics g) {

        handler.getWorld().render(g);

    }

}
