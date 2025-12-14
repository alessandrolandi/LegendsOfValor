/**
 * Represents a common, accessible tile in the game world.
 * Heroes can move through this tile and may encounter random battles.
 */
public class CommonTile extends Tile {
    public CommonTile(int row, int col) {
        super(row, col);
    }
    
    @Override
    public boolean isAccessible() {
        return true;
    }
    
    @Override
    public String getSymbol() {
        return ".";
    }
}
