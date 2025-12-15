/**
 * Represents an Obstacle tile.
 * Initially inaccessible (like a wall), but can be removed by a Hero.
 * When removed, it should be replaced by a Plain (Common) tile in the World map.
 */
public class ObstacleTile extends Tile {

    public ObstacleTile(int row, int col) {
        super(row, col);
    }

    @Override
    public boolean isAccessible() {
        // Obstacles block movement until removed
        return false;
    }

    @Override
    public String getSymbol() {
        // Using 'O' to distinguish from 'I' or 'X' (Inaccessible)
        return "O";
    }
}