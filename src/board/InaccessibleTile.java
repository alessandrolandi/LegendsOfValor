package board;

/**
 * Represents an inaccessible tile that heroes cannot move through.
 * Acts as an obstacle in the game world.
 * In Legends of Valor, used as walls to separate lanes.
 */
public class InaccessibleTile extends Tile {
    public InaccessibleTile(int row, int col) {
        super(row, col);
    }
    
    @Override
    public boolean isAccessible() {
        return false;
    }
    
    @Override
    public String getSymbol() {
        return "X";
    }
}
