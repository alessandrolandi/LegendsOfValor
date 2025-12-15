package board;

import entities.heroes.Hero;

/**
 * Represents a Cave tile.
 * Grants an agility bonus to heroes standing on it.
 * "Cave spaces increase the agility of any hero inside them."
 */
public class CaveTile extends Tile {
    private static final double BONUS_MULTIPLIER = 0.10;

    public CaveTile(int row, int col) {
        super(row, col);
    }

    @Override
    public boolean isAccessible() {
        return true;
    }

    @Override
    public String getSymbol() {
        return "C";
    }

    @Override
    public void onHeroEnter(Hero hero) {
        hero.applyAgilityBonus(BONUS_MULTIPLIER);
    }

    @Override
    public void onHeroExit(Hero hero) {
        hero.removeAgilityBonus(BONUS_MULTIPLIER);
    }
}