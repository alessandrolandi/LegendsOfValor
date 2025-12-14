/**
 * Represents a market tile where heroes can buy and sell items.
 * Each market tile contains its own Market instance.
 */
public class MarketTile extends Tile {
    private Market market;
    
    public MarketTile(int row, int col) {
        super(row, col);
        this.market = new Market();
    }
    
    @Override
    public boolean isAccessible() {
        return true;
    }
    
    @Override
    public String getSymbol() {
        return "M";
    }
    
    public Market getMarket() {
        return market;
    }
}