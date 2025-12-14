/**
 * Abstract base class representing a tile in the game world.
 * Each tile has a position and specific properties defined by subclasses.
 */
public abstract class Tile {
    protected int row;
    protected int col;
    
    public Tile(int row, int col) {
        this.row = row;
        this.col = col;
    }
    
    public abstract boolean isAccessible();
    public abstract String getSymbol();
    
    public int getRow() { return row; }
    public int getCol() { return col; }
}
