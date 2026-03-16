package uk.co.kellsnet.worldsim;

public class GameState {

    private final TileMap tileMap;
    private final Player player;

    public GameState(TileMap tileMap, Position position) {
        this.tileMap = tileMap;
        this.player = new Player(position);
    }

    public TileMap getTileMap() {
        return tileMap;
    }

    public Player getPlayer() {
        return player;
    }

    private void debug(String message) {
        if (Debug.ENABLED) {
            System.out.println(message);
        }
    }

}
