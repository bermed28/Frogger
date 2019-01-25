package Game.Entities.Static;

import Game.Entities.EntityBase;
import Game.World.BaseArea;
import Main.Handler;

public class StaticBase extends EntityBase {

    BaseArea SpawnableArea;

    public StaticBase(Handler handler) {
        super(handler);
    }

    public BaseArea getSpawnableArea() {
        return SpawnableArea;
    }

    public void setSpawnableArea(BaseArea spawnableArea) {
        SpawnableArea = spawnableArea;
    }
}
