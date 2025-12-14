import io.*;

/**
 * Interface representing a creature in the game.
 * Provides common combat functionality for all creatures including heroes and monsters.
 * Allows heroes and monsters to attack in the same way through polymorphism.
 */
public interface Creature {
    /**
     * Performs an attack on the target creature.
     * @param target The creature to attack
     * @return The amount of damage dealt
     */
    double attack(Creature target);

    /**
     * Takes damage and reduces HP accordingly.
     * @param damage The amount of damage to take
     * @return The actual damage taken after defenses
     */
    double takeDamage(double damage);

    /**
     * Attempts to dodge an incoming attack.
     * @return true if the dodge was successful, false otherwise
     */
    boolean dodge();

    /**
     * Displays the creature's current statistics.
     */
    void displayStats();

    /**
     * Sets the output handler for this creature.
     * @param output The output handler to use
     */
    void setOutputHandler(OutputHandler output);

    // Getters
    String getName();
    int getLevel();
    double getHp();
    boolean isFainted();
}
