package Client;

public enum SpriteID {
    GRASS(27, 3),
    WATER(27, 3),
    ARCHER(28, 59),
    TREE(13, 13);

    public final int x;
    public final int y;

    SpriteID(int x, int y){
        this.x = x;
        this.y = y;
    }
}
