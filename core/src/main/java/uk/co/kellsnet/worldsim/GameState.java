package uk.co.kellsnet.worldsim;

public class GameState {

    private final TileMap tileMap;
    private final Player player;

    public GameState() {
        tileMap = new TileMap();
        player = new Player(new Position(2, 1));
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
