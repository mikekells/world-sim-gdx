package uk.co.kellsnet.worldsim;

public class GameState {

    private final TileMap tileMap;
    private final Player player;

    public GameState() {
        tileMap = new TileMap();
        player = new Player(2, 1);
    }

    public TileMap getTileMap() {
        return tileMap;
    }

    public Player getPlayer() {
        return player;
    }

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

    private boolean inBounds(int dx, int dy) {
        return dx >= 0 && dx < tileMap.getWidth() && dy >= 0 && dy < tileMap.getHeight();
    }

    private void debug(String message) {
        if (Debug.ENABLED) {
            System.out.println(message);
        }
    }

}
