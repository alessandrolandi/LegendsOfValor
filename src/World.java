import io.*;

/**
 * Represents the game world as a grid of tiles.
 * Manages the world map, party movement, and tile generation.
 */
public class World {
    private Tile[][] grid;
    private int size;
    private int partyRow;
    private int partyCol;
    private OutputHandler output;

    public World(int rows, int cols) {
        this(rows, cols, new OutputHandler());
    }

    public World(int rows, int cols, OutputHandler output) {
        this.size = rows; 
        this.grid = new Tile[rows][cols];
        this.output = output;
        generateWorld();
        placeParty();
    }

    public void setOutputHandler(OutputHandler output) {
        this.output = output;
    }
    
    private void generateWorld() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                double rand = Math.random();
                
                if (rand < 0.20) {
                    grid[i][j] = new InaccessibleTile(i, j);
                } else if (rand < 0.50) {
                    grid[i][j] = new MarketTile(i, j);
                } else {
                    grid[i][j] = new CommonTile(i, j);
                }
            }
        }
        
        // Ensure starting position is accessible
        grid[0][0] = new CommonTile(0, 0);
    }
    
    private void placeParty() {
        partyRow = 0;
        partyCol = 0;
    }
    
    public boolean moveParty(int rowDelta, int colDelta) {
        int newRow = partyRow + rowDelta;
        int newCol = partyCol + colDelta;
        
        if (isValidPosition(newRow, newCol) && grid[newRow][newCol].isAccessible()) {
            partyRow = newRow;
            partyCol = newCol;
            return true;
        }
        
        return false;
    }
    
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }
    
    public void display() {
        output.display("\n=== WORLD MAP ===");
        output.display("Legend: P=Party, M=Market, X=Inaccessible, .=Common");
        output.displayLine();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (i == partyRow && j == partyCol) {
                    output.displayInline("P ");
                } else {
                    output.displayInline(grid[i][j].getSymbol() + " ");
                }
            }
            output.displayLine();
        }
        output.displayLine();
    }
    
    public Tile getCurrentTile() {
        return grid[partyRow][partyCol];
    }
    
    public int[] getPartyPosition() {
        return new int[]{partyRow, partyCol};
    }
}
