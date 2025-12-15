package board;

import io.*;

/**
 * Represents the game board for Legends: Monsters and Heroes.
 * Manages the board grid, party movement, and tile generation.
 * Features random tile placement with markets, inaccessible tiles, and common tiles.
 */
public class HeroesAndMonstersBoard extends Board {
    private int partyRow;
    private int partyCol;

    public HeroesAndMonstersBoard(int rows, int cols) {
        this(rows, cols, new OutputHandler());
    }

    public HeroesAndMonstersBoard(int rows, int cols, OutputHandler output) {
        super(rows, cols, output);
        generateBoard();
        placeParty();
    }

    @Override
    protected void generateBoard() {
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

    @Override
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
