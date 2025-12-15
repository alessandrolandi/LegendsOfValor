/**
 * Represents a Cave tile.
 * Grants an agility bonus to heroes standing on it.
 * "Cave spaces increase the agility of any hero inside them."
 */
public class CaveTile extends CommonTile {

    public CaveTile(int row, int col) {
        super(row, col);
    }

    @Override
    public String getSymbol() {
        return "C";
    }

    /**
     * Placeholder for applying the terrain bonus.
     */


}