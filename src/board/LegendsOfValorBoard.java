package board;

import entities.heroes.Hero;
import entities.monsters.Monster;
import io.OutputHandler;
import java.util.*;

/**
 * Represents the 8x8 game board for Legends of Valor.
 * The board is divided into 3 lanes separated by walls:
 * - Columns 0-1: Top Lane
 * - Column 2: Wall (Inaccessible)
 * - Columns 3-4: Mid Lane
 * - Column 5: Wall (Inaccessible)
 * - Columns 6-7: Bot Lane
 *
 * - Row 0: Monsters' Nexus
 * - Row 7: Heroes' Nexus
 */
public class LegendsOfValorBoard extends Board {
    private static final int BOARD_SIZE = 8;
    private static final int NUM_LANES = 3;

    // Lane column definitions
    private static final int[] WALL_COLS = {2, 5}; // Wall columns

    private Random random;

    public LegendsOfValorBoard() {
        super(BOARD_SIZE, BOARD_SIZE);
        this.random = new Random();
        generateBoard();
    }

    /**
     * Generates the Legends of Valor board with lanes, walls, and special tiles.
     */
    @Override
    protected void generateBoard() {
        // First pass: Create Nexus and walls
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (row == 0) {
                    // Monsters' Nexus
                    if (isWallColumn(col)) {
                        grid[row][col] = new InaccessibleTile(row, col);
                    } else {
                        grid[row][col] = new NexusTile(row, col);
                    }
                } else if (row == BOARD_SIZE - 1) {
                    // Heroes' Nexus
                    if (isWallColumn(col)) {
                        grid[row][col] = new InaccessibleTile(row, col);
                    } else {
                        grid[row][col] = new NexusTile(row, col);
                    }
                } else if (isWallColumn(col)) {
                    // Walls separating lanes
                    grid[row][col] = new InaccessibleTile(row, col);
                } else {
                    // Will be filled with special tiles in next pass
                    grid[row][col] = null;
                }
            }
        }

        // Second pass: Distribute special tiles in lanes
        // 20% Bush, 20% Cave, 20% Koulou, 40% Plain
        List<TileType> tileTypes = new ArrayList<>();
        int lanesSize = 6 * 6; // 6 columns × 6 rows (excluding Nexus rows)

        int bushCount = (int)(lanesSize * 0.20);
        int caveCount = (int)(lanesSize * 0.20);
        int koulouCount = (int)(lanesSize * 0.20);

        for (int i = 0; i < bushCount; i++) tileTypes.add(TileType.BUSH);
        for (int i = 0; i < caveCount; i++) tileTypes.add(TileType.CAVE);
        for (int i = 0; i < koulouCount; i++) tileTypes.add(TileType.KOULOU);

        // Fill the rest with plains
        while (tileTypes.size() < lanesSize) {
            tileTypes.add(TileType.PLAIN);
        }

        // Shuffle for random distribution
        Collections.shuffle(tileTypes, random);

        // Place tiles
        int typeIndex = 0;
        for (int row = 1; row < BOARD_SIZE - 1; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (grid[row][col] == null) { // Skip already placed tiles
                    TileType type = tileTypes.get(typeIndex++);
                    grid[row][col] = createTile(type, row, col);
                }
            }
        }
    }

    /**
     * Creates a tile of the specified type.
     */
    private Tile createTile(TileType type, int row, int col) {
        switch (type) {
            case BUSH:
                return new BushTile(row, col);
            case CAVE:
                return new CaveTile(row, col);
            case KOULOU:
                return new KoulouTile(row, col);
            case PLAIN:
            default:
                return new CommonTile(row, col);
        }
    }

    /**
     * Checks if a column is a wall column.
     */
    private boolean isWallColumn(int col) {
        for (int wallCol : WALL_COLS) {
            if (col == wallCol) return true;
        }
        return false;
    }

    /**
     * Returns the lane number (0=top, 1=mid, 2=bot) for a given column.
     * Returns -1 if the column is a wall.
     */
    public int getLaneForColumn(int col) {
        if (col == 0 || col == 1) return 0; // Top lane
        if (col == 3 || col == 4) return 1; // Mid lane
        if (col == 6 || col == 7) return 2; // Bot lane
        return -1; // Wall
    }

    /**
     * Returns the columns for a given lane.
     */
    public int[] getColumnsForLane(int lane) {
        switch (lane) {
            case 0: return new int[]{0, 1}; // Top lane
            case 1: return new int[]{3, 4}; // Mid lane
            case 2: return new int[]{6, 7}; // Bot lane
            default: return new int[]{};
        }
    }

    /**
     * Replaces an obstacle tile with a plain tile.
     */
    public boolean removeObstacle(int row, int col) {
        if (!isValidPosition(row, col)) return false;
        if (grid[row][col] instanceof ObstacleTile) {
            grid[row][col] = new CommonTile(row, col);
            return true;
        }
        return false;
    }

    /**
     * Gets all heroes currently on the board.
     */
    public List<Hero> getAllHeroes() {
        List<Hero> heroes = new ArrayList<>();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                heroes.addAll(grid[row][col].getHeroes());
            }
        }
        return heroes;
    }

    /**
     * Gets all monsters currently on the board.
     */
    public List<Monster> getAllMonsters() {
        List<Monster> monsters = new ArrayList<>();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                monsters.addAll(grid[row][col].getMonsters());
            }
        }
        return monsters;
    }

    @Override
    public void display() {
        // LegendsOfValor uses its own display in the game class
        // This method is required by the Board abstract class but not used here
    }

    // Getters
    public int getSize() { return BOARD_SIZE; }
    public int getNumLanes() { return NUM_LANES; }

    /**
     * Enum for tile types used in board generation.
     */
    private enum TileType {
        PLAIN, BUSH, CAVE, KOULOU
    }
}
