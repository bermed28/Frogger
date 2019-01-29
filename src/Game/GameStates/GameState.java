package Game.GameStates;

import Game.Entities.EntityManager;
import Game.World.WorldManager;
import Main.Handler;
import java.awt.*;

import com.sun.glass.events.KeyEvent;

/**
 * Created by AlexVR on 7/1/2018.
 */

/*
 * This is the state once the game is Started.
 * The WorldManager Class is constructed.
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
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
        	State.setState(handler.getGame().pauseState);
        }

    }

    @Override
    public void render(Graphics g) {
        handler.getWorld().render(g);

    }

}
