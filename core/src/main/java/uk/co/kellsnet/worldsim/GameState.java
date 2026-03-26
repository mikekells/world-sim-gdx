package uk.co.kellsnet.worldsim;

import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class GameState {

    private final TileMap tileMap;
    private final Player player;
    private final List<Entity> entities = new ArrayList<>();

    private final Position playerSpawn;
    private int timesCaught = 0;
    private int successfulMoves = 0;
    private boolean gameOver = false;

    public GameState(TileMap tileMap, Position position) {
        this.tileMap = tileMap;
        this.playerSpawn = new Position(position.getX(), position.getY());
        this.player = new Player(position);
        entities.add(new NPC(new Position(10, 10)));
        entities.add(new NPC(new Position(16, 16)));
    }

    public TileMap getTileMap() {
        return tileMap;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean tryMovePlayer(int dx, int dy) {
        Position p = getPlayer().getPosition();

        int targetX = p.getX() + dx;
        int targetY = p.getY() + dy;

        debug("[MOVE] Attempting move to (" + targetX + ", " + targetY + ")");

        if (!tileMap.inBounds(targetX, targetY)) {
            debug("[MOVE] Blocked: target out of bounds");
            return false;
        }

        TileType tile = tileMap.getTile(targetX, targetY);
        debug("[MOVE] Target tile is " + tile);

        if (tile.isWalkable()) {
            p.set(targetX, targetY);
            recordSuccessfulMoves();
            debug("[MOVE] Success: player now at (" + p.getX() + ", " + p.getY() + ")");
            debug("[MOVE] Stood on tile type: " + tile);
            return true;
        } else {
            debug("[MOVE] Blocked: tile is not walkable");
            return false;
        }
    }

    public boolean update(float delta) {
        if (gameOver) {
            return false;
        }

        boolean playerReset = false;

        for (Entity entity : entities) {
            if (entity instanceof NPC npc) {
                float timer = npc.getMoveTimer();
                timer -= delta;

                if (timer <= 0f) {
                    if (isNearPlayer(npc)) {
                        if (MathUtils.randomBoolean(0.8f)) {
                            moveNpcTowardPlayer(npc);
                        } else {
                            moveNpcRandomly(npc);
                        }
                    } else {
                        moveNpcRandomly(npc);
                    }
                    timer = npc.getMoveDelay();
                }

                npc.setMoveTimer(timer);
            }
        }

        checkNpcProximity();

        if (checkNpcCollision()) {
            handlePlayerCaught();

            if(!gameOver) {
                playerReset =true;
            }
        }

        return playerReset;
    }

    private void moveNpcRandomly(NPC npc){
            int direction = MathUtils.random(3);

            int dx = 0;
            int dy = 0;

            switch (direction) {
                case 0 -> dx = 1;
                case 1 -> dx = -1;
                case 2 -> dy = 1;
                case 3 -> dy = -1;
            }

            tryMoveNpc(npc, dx, dy);
    }

    private void tryMoveNpc(NPC npc, int dx, int dy) {
        Position p = npc.getPosition();

        int targetX = p.getX() + dx;
        int targetY = p.getY() + dy;

        if (!tileMap.inBounds(targetX, targetY)) {
            return;
        }

        TileType tile = tileMap.getTile(targetX, targetY);

        if (tile.isWalkable()) {
            p.set(targetX, targetY);
        }
    }

    public List<Entity> getEntities() {
        return entities;
    }

    private void checkNpcProximity() {
        for (Entity entity : entities) {
            if (entity instanceof NPC npc) {
                boolean isNear = isNearPlayer(npc);

                if (isNear && !npc.isPlayerNearby()) {
                    debug("[NPC] NPC at (" + npc.getPosition().getX() + ", " + npc.getPosition().getY() + ") spotted player!");
                }

                npc.setPlayerNearby(isNear);
            }
        }
    }

    private boolean isNearPlayer(NPC npc) {
        int playerX = player.getPosition().getX();
        int playerY = player.getPosition().getY();

        int npcX = npc.getPosition().getX();
        int npcY = npc.getPosition().getY();

        int dx = Math.abs(npcX - playerX);
        int dy = Math.abs(npcY - playerY);

        return dx <= 5 && dy <= 5;
    }

    private void moveNpcTowardPlayer(NPC npc) {
        Position npcPos = npc.getPosition();
        Position playerPos = player.getPosition();

        int dx = playerPos.getX() - npcPos.getX();
        int dy = playerPos.getY() - npcPos.getY();

        int moveX = 0;
        int moveY = 0;

        if (Math.abs(dx) > Math.abs(dy)) {
            moveX = Integer.signum(dx);
        } else {
            moveY = Integer.signum(dy);
        }

        tryMoveNpc(npc, moveX, moveY);
    }

    private boolean checkNpcCollision() {
        boolean playerTouched = false;

        for (Entity entity : entities) {
            if (entity instanceof NPC npc) {
                boolean touching = isTouchingPlayer(npc);

                if (touching && !npc.isTouchingPlayer()) {
                    debug("[NPC] NPC at (" + npc.getPosition().getX() + ", " + npc.getPosition().getY() + ") touched the player!");
                    playerTouched = true;
                }

                npc.setTouchingPlayer(touching);
            }
        }

        return playerTouched;
    }

    private boolean isTouchingPlayer(NPC npc) {
        int playerX = player.getPosition().getX();
        int playerY = player.getPosition().getY();

        int npcX = npc.getPosition().getX();
        int npcY = npc.getPosition().getY();

        return npcX == playerX && npcY == playerY;
    }

    public void resetPlayerToSpawn() {
        player.getPosition().set(playerSpawn.getX(), playerSpawn.getY());
        debug("[PLAYER] Reset to spawn at (" + playerSpawn.getX() + ", " + playerSpawn.getY() + ")");
    }

    public int getTimesCaught() {
        return timesCaught;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    private void handlePlayerCaught() {
        successfulMoves = 0;
        recordPlayerCaught();
        player.takeDamage(1);

        debug("[PLAYER] Health = " + player.getHealth());

        if (player.getHealth() <= 0) {
            gameOver = true;
            debug("[GAME] Game Over! | 'R' to restart.");
        } else {
            resetPlayerToSpawn();
        }
    }

    private void recordPlayerCaught() {
        timesCaught++;
        debug("[GAME] Player caught! Total catches = " + timesCaught);
    }

    public int getSuccessfulMoves() {
        return successfulMoves;
    }

    private void recordSuccessfulMoves() {
        successfulMoves++;
        debug("[MOVE] Successful moves = " + getSuccessfulMoves());
    }

    public void resetGame() {
        player.resetHealth(3);
        player.getPosition().set(playerSpawn.getX(), playerSpawn.getY());

        timesCaught = 0;
        successfulMoves = 0;
        gameOver = false;

        for (Entity entity : entities) {
            if (entity instanceof NPC npc) {
                npc.resetToSpawn();
            }
        }

        debug("[GAME] Restarted");
        debug("[PLAYER] Health reset to " + player.getHealth());
        debug("[PLAYER] Reset to spawn at (" + player.getPosition().getX() + ", " + player.getPosition().getY() + ")");
    }

    private void debug(String message) {
        if (Debug.ENABLED) {
            System.out.println(message);
        }
    }
}
