package Game.Entities.Dynamic;

import Game.Entities.EntityBase;
import Game.Entities.Static.StaticBase;
import Game.Entities.Static.Tree;
import Game.GameStates.State;
import Game.World.WorldManager;
import Main.Handler;
import Resources.Images;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/*
 * The Frog.
 */
public class Player extends EntityBase {
    private Handler handler;


    private Rectangle player;
    //private Rectangle nextBlockCHeck;
    public String facing = "UP";
    public Boolean moving = false;
    private int moveCoolDown=0;
    public int scoreTracker = 0;
    public static int score = 0;
//    public boolean notColliding = true;

    private int index =0;

    public Player(Handler handler) {
        super(handler);
        this.handler = handler;
        this.handler.getEntityManager().getEntityList().add(this);

        player = new Rectangle(); 	// see UpdatePlayerRectangle(Graphics g) for its usage.
        //nextBlockCHeck = new Rectangle();
    }

    public void tick(){

//        if(scoreTracker > score){
//            this.score +=1;
////            score +=1;
//            System.out.println(score);
//        }
//

        if(moving) {
            animateMovement();
        }

        if(!moving){
            move();
        }
        if((player.getX()<0)){
            this.setX(64);
        }
        if(player.getX() > 512){
            this.setX(512);
        }

    }

    private void reGrid() {
        if(facing.equals("UP")) {
            if(this.getX() % 64 >= 64 / 2 ) {
                this.setX(this.getX() + (64 - this.getX() % 64));
            }
            else {
                this.setX(this.getX() - this.getX() % 64);
            }
            setY(getY()-64);
        }
    }

    private void move(){

        if(moveCoolDown< 25){
            moveCoolDown++;
        }
        index=0;

        /////////////////MOVE UP///////////////
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_W) && !moving && facing.equals("UP")&& this.getY()-128>0) {
            moving = true;
//            if (notColliding){
            scoreTracker += 1;
       // }
        }else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_W) && !moving && !facing.equals("UP")){
            if(facing.equals("DOWN")) {
                if(this.getX() % 64 >= 64 / 2 ) {

                    this.setX(this.getX() + (64 - this.getX() % 64));
                }
                else {
                    this.setX(this.getX() - this.getX() % 64);
                }
                setY(getY() + 64);
            }
            if(facing.equals("LEFT")) {
                setY(getY() + 64);
            }

            if(facing.equals("RIGHT")) {
                setX(getX()-64);
                setY(getY()+64);
            }
            facing = "UP";
        }
        /////////////////MOVE LEFT///////////////
        else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_A) && !moving && facing.equals("LEFT")&& this.getX()>0){
            moving=true;
        }else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_A) && !moving&& !facing.equals("LEFT")){
            if(facing.equals("RIGHT")) {
                setX(getX()-64);
            }
            reGrid();
            facing = "LEFT";
        }

        /////////////////MOVE DOWN///////////////
        else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_S) && !moving && facing.equals("DOWN")){
            moving=true;
//            if (notColliding) {
                scoreTracker -= 1;
//            }
        }else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_S) && !moving && !facing.equals("DOWN")){
            reGrid();
            if(facing.equals("RIGHT")){
                setX(getX()-64);
            }
            facing = "DOWN";
        }

        /////////////////MOVE RIGHT///////////////
        else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_D) && !moving && facing.equals("RIGHT")&& this.getX()<handler.getWidth()){
            moving=true;
        }else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_D) && !moving&& !facing.equals("RIGHT")){
            if(facing.equals("LEFT")) {
                setX(getX()+64);
            }
            if(facing.equals("UP")) {
                setX(getX()+64);
                setY(getY()-64);
            }
            if(facing.equals("DOWN")) {
                if(this.getX() % 64 >= 64 / 2 ) {
                    this.setX(this.getX() + (64 - this.getX() % 64));
                }
                else {
                    this.setX(this.getX() - this.getX() % 64);
                }
                setX(getX()+64);
            }
            facing = "RIGHT";
        }
        if(scoreTracker > score){
            score ++;
            System.out.println(score);
        }
    }

    private void animateMovement(){
        if(index==8) {
            moving = false;
            index = 0;
        }
        moveCoolDown = 0;
        index++;
        switch (facing) {
            case "UP":
//                if(this.getY() < 70) {
//                    break;
//                }
                if (this.getX() % 64 >= 64 / 2) {
                    this.setX(this.getX() + (64 - this.getX() % 64));
                } else {
                    this.setX(this.getX() - this.getX() % 64);
                }
                setY(getY() - (8));
                break;

            case "LEFT":
//                if(this.getX() < -5) {
//                    break;
//                }
                setX(getX() - (8));
                break;

            case "DOWN":
                if (this.getX() % 64 >= 64 / 2) {
                    this.setX(this.getX() + (64 - this.getX() % 64));
                } else {
                    this.setX(this.getX() - this.getX() % 64);
                }
                setY(getY() + (8));
                break;

            case "RIGHT":
//                if(this.getX() > 576) {
//                    break;
//                }
                setX(getX() + (8));
                break;

        }
    }

    public void render(Graphics g){

        if(index>=8){
            index=0;
            moving = false;
        }

        switch (facing) {
            case "UP":
                g.drawImage(Images.Player[index], getX(), getY(), getWidth(), -1 * getHeight(), null);
                break;
            case "DOWN":
                g.drawImage(Images.Player[index], getX(), getY(), getWidth(), getHeight(), null);
                break;
            case "LEFT":
                g.drawImage(rotateClockwise90(Images.Player[index]), getX(), getY(), getWidth(), getHeight(), null);
                break;
            case "RIGHT":
                g.drawImage(rotateClockwise90(Images.Player[index]), getX(), getY(), -1 * getWidth(), getHeight(), null);
                break;
        }


        UpdatePlayerRectangle(g);

    }

    // Rectangles are what is used as "collisions." 
    // The hazards have Rectangles of their own.
    // This is the Rectangle of the Player.
    // Both come in play inside the WorldManager.
    private void UpdatePlayerRectangle(Graphics g) {

        player = new Rectangle(this.getX(), this.getY(), getWidth(), getHeight());
        //nextBlockCHeck = new Rectangle(this.getX(), this.getY()+ 64, getWidth(), getHeight());

        if (facing.equals("UP")){
            player = new Rectangle(this.getX(), this.getY() - 64, getWidth(), getHeight());
        }
        else if (facing.equals("RIGHT")) {
            player = new Rectangle(this.getX() - 64, this.getY(), getWidth(), getHeight());
        }
    }

    @SuppressWarnings("SuspiciousNameCombination")
    private static BufferedImage rotateClockwise90(BufferedImage src) {
        int width = src.getWidth();
        int height = src.getHeight();

        BufferedImage dest = new BufferedImage(height, width, src.getType());

        Graphics2D graphics2D = dest.createGraphics();
        graphics2D.translate((height - width) / 2, (height - width) / 2);
        graphics2D.rotate(Math.PI / 2, height / 2, width / 2);
        graphics2D.drawRenderedImage(src, null);

        return dest;
    }

    public Rectangle getPlayerCollision() {
        return player;
    }

}
