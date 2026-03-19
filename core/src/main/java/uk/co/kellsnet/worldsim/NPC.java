package uk.co.kellsnet.worldsim;

import com.badlogic.gdx.math.MathUtils;

public class NPC extends Entity {

    private float moveTimer = 0f;
    private final float moveDelay = 0.5f;
    private boolean playerNearby = false;
    private boolean touchingPlayer = false;

    public NPC(Position position) {
        super(position);
        this.moveTimer = MathUtils.random(0f, 0.5f);
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

}
