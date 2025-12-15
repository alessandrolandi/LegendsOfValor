/**
 * Represents a Bush tile.
 * Grants a dexterity bonus to heroes standing on it.
 * "Bush spaces increase the dexterity of any hero inside them."
 */
public class BushTile extends CommonTile {

    public BushTile(int row, int col) {
        super(row, col);
    }

    @Override
    public String getSymbol() {
        return "B";
    }

    /**
     * Placeholder for applying the terrain bonus.
     */

}