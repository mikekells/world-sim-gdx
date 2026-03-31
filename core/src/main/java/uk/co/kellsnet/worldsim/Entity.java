package uk.co.kellsnet.worldsim;

import com.badlogic.gdx.math.MathUtils;

public class Entity {

    private final Position position;
    private final Position previousPosition;

    private float renderX;
    private float renderY;

    private float moveProgress = 1f;
    private float moveDuration = 0.12f;
    private boolean moving = false;

    public Entity(Position startPosition) {
        this.position = startPosition;
        this.previousPosition = new Position(startPosition.getX(), startPosition.getY());
        this.renderX = startPosition.getX();
        this.renderY = startPosition.getY();
    }

    public Position getPosition() {
        return position;
    }

    public float getRenderX() {
        return renderX;
    }
    public float getRenderY() {
        return renderY;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoveDuration(float moveDuration) {
        this.moveDuration = moveDuration;
    }

    public void moveTo(int targetX, int targetY) {
        previousPosition.set(position.getX(), position.getY());
        position.set(targetX, targetY);

        moveProgress = 0f;
        moving = true;
    }

    public void updateMovement(float delta) {
        if (!moving) {
            renderX = position.getX();
            renderY = position.getY();
            return;
        }

        moveProgress += delta / moveDuration;

        if (moveProgress >= 1f) {
            moveProgress = 1f;
            moving = false;
        }

        renderX = MathUtils.lerp(previousPosition.getX(), position.getX(), moveProgress);
        renderY = MathUtils.lerp(previousPosition.getY(), position.getY(), moveProgress);
    }

    public void snapTo(int x, int y) {
        previousPosition.set(x, y);
        position.set(x, y);
        renderX = x;
        renderY = y;
        moveProgress = 1f;
        moving = false;
    }
}
