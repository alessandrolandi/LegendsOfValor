package board;

import io.OutputHandler;

/**
 * Abstract base class for game boards.
 * Provides common functionality for managing a 2D grid of tiles.
 * Subclasses implement specific board generation logic for different game modes.
 */
public abstract class Board {
    protected Tile[][] grid;
    protected int rows;
    protected int cols;
    protected OutputHandler output;

    /**
     * Creates a new board with the specified dimensions.
     * @param rows Number of rows in the board
     * @param cols Number of columns in the board
     */
    public Board(int rows, int cols) {
        this(rows, cols, new OutputHandler());
    }

    /**
     * Creates a new board with the specified dimensions and output handler.
     * @param rows Number of rows in the board
     * @param cols Number of columns in the board
     * @param output Output handler for displaying messages
     */
    public Board(int rows, int cols, OutputHandler output) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new Tile[rows][cols];
        this.output = output;
    }

    /**
     * Generates the board with tiles.
     * Subclasses must implement this to define their specific board layout.
     */
    protected abstract void generateBoard();

    /**
     * Checks if the given position is within the board bounds.
     * @param row The row to check
     * @param col The column to check
     * @return true if the position is valid, false otherwise
     */
    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    /**
     * Checks if the given position is accessible (not blocked/inaccessible).
     * @param row The row to check
     * @param col The column to check
     * @return true if the position is accessible, false otherwise
     */
    public boolean isAccessible(int row, int col) {
        if (!isValidPosition(row, col)) return false;
        return grid[row][col].isAccessible();
    }

    /**
     * Gets the tile at the specified position.
     * @param row The row of the tile
     * @param col The column of the tile
     * @return The tile at the position, or null if invalid
     */
    public Tile getTile(int row, int col) {
        if (!isValidPosition(row, col)) return null;
        return grid[row][col];
    }

    /**
     * Sets the output handler for this board.
     * @param output The output handler to use
     */
    public void setOutputHandler(OutputHandler output) {
        this.output = output;
    }

    /**
     * Gets the number of rows in the board.
     * @return The number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Gets the number of columns in the board.
     * @return The number of columns
     */
    public int getCols() {
        return cols;
    }

    /**
     * Gets the entire grid of tiles.
     * @return The 2D array of tiles
     */
    public Tile[][] getGrid() {
        return grid;
    }

    /**
     * Displays the board to the user.
     * Subclasses can override this to provide custom display logic.
     */
    public abstract void display();
}
