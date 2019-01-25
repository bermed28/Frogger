package Game.World;

import Main.Handler;

public class BaseArea {
    Handler handler;

    int yPosition;

    boolean canStepOn = true;

    public BaseArea(Handler handler) {
        this.handler = handler;
    }
}
