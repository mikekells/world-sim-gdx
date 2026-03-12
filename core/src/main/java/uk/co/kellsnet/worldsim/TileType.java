package uk.co.kellsnet.worldsim;

public enum TileType {
    EMPTY(true),
    WALL(false),
    PILLAR(false),
    DOOR(true);

    private final boolean walkable;

    TileType(boolean walkable) {
        this.walkable = walkable;
    }

    public boolean isWalkable() {
        return walkable;
    }
}
