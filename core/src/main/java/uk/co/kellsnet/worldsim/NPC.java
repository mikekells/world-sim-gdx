package uk.co.kellsnet.worldsim;

import com.badlogic.gdx.math.MathUtils;

public class NPC extends Entity {

    private final Position spawnPosition;
    private float moveTimer = 0f;
    private final float moveDelay = 0.5f;
    private boolean playerNearby = false;
    private boolean touchingPlayer = false;

    public NPC(Position position) {
        super(position);
        this.spawnPosition = new Position(position.getX(), position.getY());
        this.moveTimer = MathUtils.random(0f, 0.5f);
        setMoveDuration(1.12f);
    }

    public Position getSpawnPosition() {
        return spawnPosition;
    }

    public float getMoveTimer() {
        return moveTimer;
    }

    public void setMoveTimer(float moveTimer) {
        this.moveTimer = moveTimer;
    }

    public float getMoveDelay() {
        return moveDelay;
    }

    public boolean isPlayerNearby() {
        return playerNearby;
    }

    public void setPlayerNearby(boolean playerNearby) {
        this.playerNearby = playerNearby;
    }

    public boolean isTouchingPlayer() {
        return touchingPlayer;
    }

    public void setTouchingPlayer(boolean touchingPlayer) {
        this.touchingPlayer = touchingPlayer;
    }

    public void resetToSpawn() {
        snapTo(spawnPosition.getX(), spawnPosition.getY());
        moveTimer = MathUtils.random(0f, 0.5f);
        playerNearby = false;
        touchingPlayer = false;
    }
}
