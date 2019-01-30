package Main;

import Game.Entities.Dynamic.Player;
import Game.Entities.EntityManager;
import Game.World.WorldManager;
import Input.KeyManager;
import Input.MouseManager;
import Resources.Animation;


/**
 * Created by AlexVR on 7/1/2018.
 */
public class Handler {

    private GameSetUp game;
    private EntityManager entityManager;
    private WorldManager world;
    private Player player;
    public static String str = Animation.getLtr();


    public Handler(GameSetUp game){
        this.game = game;
    }

    public int getWidth(){
        return game.getWidth();
    }

    public int getHeight(){
        return game.getHeight();
    }

    public GameSetUp getGame() {
        return game;
    }

    public void setGame(GameSetUp game) {
        this.game = game;
    }

    public KeyManager getKeyManager(){
        return game.getKeyManager();
    }

    public MouseManager getMouseManager(){
        return game.getMouseManager();
    }

    public EntityManager getEntityManager(){
        return entityManager;
    }

    public void setEntityManager(EntityManager em){
        entityManager = em;
    }

    public WorldManager getWorld(){
        return world;
    }

    public void setWorldManager(WorldManager wm){
        world = wm;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
