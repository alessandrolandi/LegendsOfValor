package board;

import entities.heroes.Hero;

/**
 * Represents a Bush tile.
 * Grants a dexterity bonus to heroes standing on it.
 * "Bush spaces increase the dexterity of any hero inside them."
 */
public class BushTile extends Tile {
    private static final double BONUS_MULTIPLIER = 0.10;

    public BushTile(int row, int col) {
        super(row, col);
    }

    @Override
    public boolean isAccessible() {
        return true;
    }

    @Override
    public String getSymbol() {
        return "B";
    }

    @Override
    public void onHeroEnter(Hero hero) {
        hero.applyDexterityBonus(BONUS_MULTIPLIER);
    }

    @Override
    public void onHeroExit(Hero hero) {
        hero.removeDexterityBonus(BONUS_MULTIPLIER);
    }
}