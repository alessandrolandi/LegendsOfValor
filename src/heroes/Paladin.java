package heroes;

import io.OutputHandler;

/**
 * Paladin hero class with bonuses to strength and dexterity.
 * Paladins are balanced fighters with good attack power and spell effectiveness.
 */
public class Paladin extends Hero {
    public Paladin(String name) {
        super(name);
    }

    public Paladin(String name, OutputHandler output) {
        super(name, output);
    }
    
    @Override
    protected void applyStartingBonuses() {
        strength = (int)(strength * 1.1);
        dexterity = (int)(dexterity * 1.1);
    }
    
    @Override
    protected void applyLevelUpBonuses() {
        // Extra 5% to favored skills
        strength = (int)(strength * 1.05);
        dexterity = (int)(dexterity * 1.05);
    }
}