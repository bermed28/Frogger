package Game.World;

public enum ID {

    TREE(4),
    LOG(3),
    LILLY(2),
    PLAYER(1),
    EMPTY(0);

    private int id;

    ID(int id) {
        this.id = id;
    }

    public int getID() {
        return this.id;
    }

}
