/**
 * Represents a Koulou tile.
 * Grants a strength bonus to heroes standing on it.
 * "Koulou spaces increase the strength of any hero who is inside them."
 */
public class KoulouTile extends CommonTile {

    public KoulouTile(int row, int col) {
        super(row, col);
    }

    @Override
    public String getSymbol() {
        return "K";
    }

    /**
     * Placeholder for applying the terrain bonus.
     */
 
}