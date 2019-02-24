package Game.GameStates;

import Main.Handler;
import Resources.Images;
import UI.UIImageButton;
import UI.UIManager;
import Resources.MusicHandler;
import Game.Entities.Dynamic.Player;

import java.awt.*;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class DeathState extends State {

    private int count = 0;
    private UIManager uiManager;
    private MusicHandler musicHandler;
    private Player player;

    public DeathState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUimanager(uiManager);
        musicHandler = new MusicHandler();
        player = new Player(handler);

        /*
         * Adds a button that by being pressed changes the State
         */
        //restart button
        uiManager.addObjects(new UIImageButton(33, handler.getGame().getHeight() - 150, 128, 64, Images.Restart, () -> {
            handler.getMouseManager().setUimanager(null);
            handler.getGame().reStart();
            State.setState(handler.getGame().gameState);
            Player.score =0;
        }));

        // title menu button
        uiManager.addObjects(new UIImageButton(33 + 192 * 2,  handler.getGame().getHeight() - 150, 128, 64, Images.BTitle, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().menuState);
        }));
//        musicHandler.pause();
//        musicHandler.set_changeMusic("res/music/UTheme.mp3");
//        musicHandler.play();
//        musicHandler.setLoop(true);
//        musicHandler.setVolume(0.25);






    }

    @Override
    public void tick() {
        handler.getMouseManager().setUimanager(uiManager);
        uiManager.tick();
        count++;
        if( count>=30){
            count=30;
        }
        if(handler.getKeyManager().pbutt && count>=30){
            count=0;
            State.setState(handler.getGame().gameState);
        }

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Images.GameOver,0,0,handler.getGame().getWidth(),handler.getGame().getHeight(),null);
        uiManager.Render(g);
//        String s = Integer.toString(player.score);
        g.drawString(String.valueOf(player.score), handler.getWidth() -300, 450);

    }
}

