package board;

import entities.heroes.Hero;

/**
 * Represents a Koulou tile.
 * Grants a strength bonus to heroes standing on it.
 * "Koulou spaces increase the strength of any hero who is inside them."
 */
public class KoulouTile extends Tile {
    private static final double BONUS_MULTIPLIER = 0.10;

    public KoulouTile(int row, int col) {
        super(row, col);
    }

    @Override
    public boolean isAccessible() {
        return true;
    }

    @Override
    public String getSymbol() {
        return "K";
    }

    @Override
    public void onHeroEnter(Hero hero) {
        hero.applyStrengthBonus(BONUS_MULTIPLIER);
    }

    @Override
    public void onHeroExit(Hero hero) {
        hero.removeStrengthBonus(BONUS_MULTIPLIER);
    }
}