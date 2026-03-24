package uk.co.kellsnet.worldsim;

public class Player extends Entity {

    private int health = 3;

    public Player(Position position) {
        super(position);
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

}
