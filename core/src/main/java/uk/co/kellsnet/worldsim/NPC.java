package uk.co.kellsnet.worldsim;

import com.badlogic.gdx.math.MathUtils;

public class NPC extends Entity {

    private float moveTimer = 0f;
    private final float moveDelay = 0.5f;

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

}
