import heroes.Hero;
import monsters.Monster;

/**
 * Represents a Heroes and Monsters RPG game.
 * Extends the base RPGGame class to provide specific functionality
 * for managing parties of heroes that battle against monsters.
 */
public abstract class HeroesAndMonsters extends RPGGame {
    protected Party party;
    protected World world;

    /**
     * Creates a new Heroes and Monsters game instance.
     * Calls the parent RPGGame constructor to initialize input/output handlers.
     */
    public HeroesAndMonsters() {
        super();
    }

    /**
     * Gets the party of heroes in this game.
     * @return The party of heroes
     */
    public Party getParty() {
        return party;
    }

    /**
     * Gets the game world.
     * @return The game world
     */
    public World getWorld() {
        return world;
    }

    /**
     * Initiates a battle between the party and monsters.
     */
    protected abstract void startBattle();

    /**
     * Displays information about the party and their status.
     */
    protected abstract void displayInfo();
}
