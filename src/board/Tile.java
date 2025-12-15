package board;

import entities.heroes.Hero;
import entities.monsters.Monster;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class representing a tile in the game world.
 * Each tile has a position and specific properties defined by subclasses.
 * Extended for Legends of Valor to support holding heroes and monsters.
 */
public abstract class Tile {
    protected int row;
    protected int col;
    protected List<Hero> heroes;
    protected List<Monster> monsters;

    public Tile(int row, int col) {
        this.row = row;
        this.col = col;
        this.heroes = new ArrayList<>();
        this.monsters = new ArrayList<>();
    }

    public abstract boolean isAccessible();
    public abstract String getSymbol();

    /**
     * Called when a hero enters this tile. Override to apply terrain bonuses.
     */
    public void onHeroEnter(Hero hero) {
        // Default: no effect
    }

    /**
     * Called when a hero exits this tile. Override to remove terrain bonuses.
     */
    public void onHeroExit(Hero hero) {
        // Default: no effect
    }

    /**
     * Adds a hero to this tile.
     */
    public boolean addHero(Hero hero) {
        if (!heroes.contains(hero)) {
            heroes.add(hero);
            onHeroEnter(hero);
            return true;
        }
        return false;
    }

    /**
     * Removes a hero from this tile.
     */
    public boolean removeHero(Hero hero) {
        if (heroes.remove(hero)) {
            onHeroExit(hero);
            return true;
        }
        return false;
    }

    /**
     * Adds a monster to this tile.
     */
    public boolean addMonster(Monster monster) {
        if (!monsters.contains(monster)) {
            monsters.add(monster);
            return true;
        }
        return false;
    }

    /**
     * Removes a monster from this tile.
     */
    public boolean removeMonster(Monster monster) {
        return monsters.remove(monster);
    }

    // Getters
    public int getRow() { return row; }
    public int getCol() { return col; }
    public List<Hero> getHeroes() { return new ArrayList<>(heroes); }
    public List<Monster> getMonsters() { return new ArrayList<>(monsters); }
    public boolean hasHero() { return !heroes.isEmpty(); }
    public boolean hasMonster() { return !monsters.isEmpty(); }
    public int getHeroCount() { return heroes.size(); }
    public int getMonsterCount() { return monsters.size(); }
}
