package entities;
import io.*;

/**
 * Abstract base class representing a creature in the game.
 * Provides common functionality for all creatures including heroes and monsters.
 * Implements basic combat mechanics like attacking, taking damage, and dodging.
 */
public abstract class Creature {
    protected String name;
    protected int level;
    protected double hp;
    protected boolean isFainted;
    protected OutputHandler output;

    /**
     * Creates a new creature with the given name and level.
     * @param name The name of the creature
     * @param level The level of the creature
     * @param output The output handler for displaying messages
     */
    public Creature(String name, int level, OutputHandler output) {
        this.name = name;
        this.level = level;
        this.output = output;
        this.isFainted = false;
    }

    /**
     * Performs an attack on the target creature.
     * @param target The creature to attack
     * @return The amount of damage dealt
     */
    public abstract double attack(Creature target);

    /**
     * Takes damage and reduces HP accordingly.
     * @param damage The amount of damage to take
     * @return The actual damage taken after defenses
     */
    public abstract double takeDamage(double damage);

    /**
     * Attempts to dodge an incoming attack.
     * @return true if the dodge was successful, false otherwise
     */
    public abstract boolean dodge();

    /**
     * Displays the creature's current statistics.
     */
    public abstract void displayStats();

    /**
     * Sets the output handler for this creature.
     * @param output The output handler to use
     */
    public void setOutputHandler(OutputHandler output) {
        this.output = output;
    }

    // Getters
    public String getName() { return name; }
    public int getLevel() { return level; }
    public double getHp() { return hp; }
    public boolean isFainted() { return isFainted; }
}
