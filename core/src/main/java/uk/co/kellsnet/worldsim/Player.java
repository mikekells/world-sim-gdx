package uk.co.kellsnet.worldsim;

public class Player extends Entity {

    private int health = 3;
    private Direction facing = Direction.DOWN;

    public Player(Position position) {
        super(position);
        setMoveDuration(0.17f);
    }

    public int getHealth() {
        return health;
    }

    public void takeDamage(int amount) {
        health -= amount;
    }

    public void resetHealth(int health) {
        this.health = health;
    }

    public Direction getFacing() {
        return facing;
    }

    public void setFacing(Direction facing) {
        this.facing = facing;
    }

    public int getAnimationColumn() {
        if (!isMoving()) {
            return 0;
        }

        float progress = getMoveProgress();

        if (progress < 0.5f) {
            return 1;
        } else {
            return 2;
        }
    }
}
