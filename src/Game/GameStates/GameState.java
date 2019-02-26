package Game.GameStates;

import Game.Entities.EntityManager;
import Game.World.WorldManager;
import Main.Handler;
import java.awt.*;
import Game.Entities.Dynamic.Player;

import com.sun.glass.events.KeyEvent;

/**
 * Created by AlexVR on 7/1/2018.
 */

/*
 * This is the state once the game is Started.
 * The WorldManager Class is constructed.
 */
public class GameState extends State {
    private Player player;

    public GameState(Handler handler){
        super(handler);
        handler.setEntityManager(new EntityManager(handler));
        handler.setWorldManager(new WorldManager(handler));
        player = new Player(handler);

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
        g.setColor(Color.green);
        g.setFont(new Font("Bauhaus 93",Font.ROMAN_BASELINE,40));
        g.drawString("Score:",handler.getWidth() - 215 , 50);
        g.drawString(String.valueOf(player.score), handler.getWidth() -100, 50);


    }

}
