package uk.co.kellsnet.worldsim;

public class Movement {

    public static boolean tryMovePlayer(GameState state, int dx, int dy) {
        TileMap tileMap = state.getTileMap();
        Position p = state.getPlayer().getPosition();

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

    private static void debug(String message) {
        if (Debug.ENABLED) {
            System.out.println(message);
        }
    }

}
