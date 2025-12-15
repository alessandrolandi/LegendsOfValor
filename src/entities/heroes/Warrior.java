package entities.heroes;


import io.OutputHandler;

/**
 * Warrior hero class with bonuses to strength and agility.
 * Warriors excel in physical combat with enhanced damage and mobility.
 */
public class Warrior extends Hero {
    public Warrior(String name) {
        super(name);
    }

    public Warrior(String name, OutputHandler output) {
        super(name, output);
    }
    
    @Override
    protected void applyStartingBonuses() {
        strength = (int)(strength * 1.1);
        agility = (int)(agility * 1.1);
    }
    
    @Override
    protected void applyLevelUpBonuses() {
        // Extra 5% to favored skills
        strength = (int)(strength * 1.05);
        agility = (int)(agility * 1.05);
    }
}