package entities.heroes;


import io.OutputHandler;

/**
 * Sorcerer hero class with bonuses to dexterity and agility.
 * Sorcerers specialize in spellcasting with enhanced magical damage and evasion.
 */
public class Sorcerer extends Hero {
    public Sorcerer(String name) {
        super(name);
    }

    public Sorcerer(String name, OutputHandler output) {
        super(name, output);
    }
    
    @Override
    protected void applyStartingBonuses() {
        dexterity = (int)(dexterity * 1.1);
        agility = (int)(agility * 1.1);
    }
    
    @Override
    protected void applyLevelUpBonuses() {
        // Extra 5% to favored skills
        dexterity = (int)(dexterity * 1.05);
        agility = (int)(agility * 1.05);
    }
}