import java.util.*;

import heroes.Hero;

/**
 * Represents a party of heroes that adventure together.
 * Provides methods to manage the party's status and actions.
 */
public class Party {
    private List<Hero> heroes;
    
    public Party(List<Hero> heroes) {
        this.heroes = heroes;
    }
    
    public boolean allFainted() {
        for (Hero hero : heroes) {
            if (!hero.isFainted()) {
                return false;
            }
        }
        return true;
    }
    
    public void reviveAll() {
        for (Hero hero : heroes) {
            if (hero.isFainted()) {
                hero.revive();
            }
        }
    }
    
    public void regenerateAll() {
        for (Hero hero : heroes) {
            hero.regenerate();
        }
    }
    
    public int getHighestLevel() {
        int maxLevel = 1;
        for (Hero hero : heroes) {
            if (hero.getLevel() > maxLevel) {
                maxLevel = hero.getLevel();
            }
        }
        return maxLevel;
    }
    
    public List<Hero> getHeroes() {
        return heroes;
    }
    
    public int size() {
        return heroes.size();
    }
}