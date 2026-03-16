package uk.co.kellsnet.worldsim;

public class Entity {

    private final Position position;

    public Entity(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }
}
