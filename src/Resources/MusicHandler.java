package Resources;

import Main.Handler;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.*;
import java.util.ArrayList;

import Game.Entities.EntityManager;

public class MusicHandler {


    private Media media;
    private MediaPlayer player;
    public boolean isPlaying = false;
    private String path;
    public static String str;
    public boolean alreadyStarted = false;
    private boolean Loop = false;

    private ArrayList<MediaPlayer> playerE = new ArrayList<>();
    private Media mediaE;
    private String pathE;
    public Handler handler;

    public static final double MUTE = 0.0;

    public MusicHandler(){

        final JFXPanel fxPanel = new JFXPanel();
        str = EntityManager.getLtr();

    }

    public void play(){
        if(!isPlaying){
            if(!alreadyStarted) {
                player = new MediaPlayer(media);
                player.play();
                isPlaying = true;
                alreadyStarted = true;
            }else{
                player.play();
            }
        }

    }


    public void pause(){

        if(isPlaying){
            player.pause();
            isPlaying = false;
        }

    }

    public void set_changeMusic(String Path){

        if(isPlaying){
            pause();
            alreadyStarted = false;
        }
        path= Path;
        media = new Media(new File(Path).toURI().toString());

    }

    public void playEfect(String EPath,int index){

        pathE=EPath;
        mediaE = new Media(new File(EPath).toURI().toString());
        playerE.add(index, new MediaPlayer(mediaE));
        playerE.get(index).play();


    }

    public void stopEfect(int index){

        playerE.get(index).stop();

    }

    public MediaPlayer getPlayer() {
        return player;
    }

    public void setPlayer(MediaPlayer player) {
        this.player = player;
    }

    public void setLoop(boolean loop){
        Loop = loop;
        if(loop){
            player.setCycleCount(-1);
        }else{
            player.setCycleCount(1);
            player.setOnEndOfMedia(() -> {
                alreadyStarted = false;
                isPlaying = false;
            });
        }
    }

    public void setVolume (double volume){
        player.setVolume(volume);
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
