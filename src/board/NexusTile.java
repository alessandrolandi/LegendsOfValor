package board;

/**
 * Represents a Nexus tile.
 * For Heroes, this acts as their base and a marketplace.
 * For Monsters, this is their spawn point and target for Heroes.
 * Reaching the opponent's Nexus results in victory.
 */
public class NexusTile extends MarketTile {

    public NexusTile(int row, int col) {
        super(row, col);
    }

    @Override
    public String getSymbol() {
        return "N";
    }

    /**
     * Checks if this Nexus has a market (hero Nexus does).
     */
    public boolean hasMarket() {
        return getMarket() != null;
    }

    // You might want to distinguish if this is a Hero Nexus or Monster Nexus
    // typically handled by the row index (row 0 vs row 7) in the Game Logic.
}