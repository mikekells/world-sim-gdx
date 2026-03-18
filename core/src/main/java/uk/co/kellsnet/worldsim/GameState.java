package uk.co.kellsnet.worldsim;

import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class GameState {

    private final TileMap tileMap;
    private final Player player;
    private final List<Entity> entities = new ArrayList<>();

    private float npcMoveTimer = 0f;
    private final float npcMoveDelay = 0.5f;

    public GameState(TileMap tileMap, Position position) {
        this.tileMap = tileMap;
        this.player = new Player(position);
        entities.add(new NPC(new Position(10, 10)));
        entities.add(new NPC(new Position(15, 15)));
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
            debug("[MOVE] Success: player now at (" + p.getX() + ", " + p.getY() + ")");
            debug("[MOVE] Stood on tile type: " + tile);
            return true;
        } else {
            debug("[MOVE] Blocked: tile is not walkable");
            return false;
        }
    }

    public void update(float delta) {
        npcMoveTimer -= delta;

        if (npcMoveTimer <= 0f) {
            for (Entity entity : entities) {
                if (entity instanceof NPC npc) {
                    moveNpcRandomly(npc);
                }
            }
            npcMoveTimer = npcMoveDelay;
        }
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

    private void debug(String message) {
        if (Debug.ENABLED) {
            System.out.println(message);
        }
    }

}
