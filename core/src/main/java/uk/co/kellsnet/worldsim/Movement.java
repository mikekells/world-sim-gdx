package uk.co.kellsnet.worldsim;

public class Movement {

    public boolean tryMovePlayer(int dx, int dy) {
        int targetX = player.getX() + dx;
        int targetY = player.getY() + dy;

        debug("[MOVE] Attempting move to (" + targetX + ", " + targetY + ")");

        if (!inBounds(targetX, targetY)) {
            debug("[MOVE] Blocked: target out of bounds");
            return false;
        }

        TileType tile = tileMap.getTile(targetX, targetY);
        debug("[MOVE] Target tile is " + tile);

        if (tile.isWalkable()) {
            player.move(dx, dy);
            debug("[MOVE] Success: player now at (" + player.getX() + ", " + player.getY() + ")");
            debug("[MOVE] Stood on tile type: " + tile);
            return true;
        } else {
            debug("[MOVE] Blocked: tile is not walkable");
            return false;
        }
    }

}
