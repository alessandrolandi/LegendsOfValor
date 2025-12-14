package items;

import heroes.Hero;

/**
 * Represents a consumable potion that provides temporary or permanent stat boosts.
 * Potions can restore health, mana, or increase various attributes.
 */
public class Potion extends Item {
    private PotionType type;
    private int effectAmount;
    
    public Potion(String name, int price, int requiredLevel, PotionType type, int effectAmount) {
        super(name, price, requiredLevel, 1); 
        this.type = type;
        this.effectAmount = effectAmount;
    }
    
    public void applyEffect(Hero hero) {
        switch (type) {
            case HEALTH:
                hero.takeDamage(-effectAmount);
                output.display(hero.getName() + " recovered " + effectAmount + " HP!");
                break;
            case MANA:
                output.display(hero.getName() + " recovered " + effectAmount + " MP!");
                break;
            case STRENGTH:
                output.display(hero.getName() + " gained " + effectAmount + " Strength!");
                break;
            case DEXTERITY:
                output.display(hero.getName() + " gained " + effectAmount + " Dexterity!");
                break;
            case AGILITY:
                output.display(hero.getName() + " gained " + effectAmount + " Agility!");
                break;
        }
    }
    
    @Override
    public void displayInfo() {
        output.display(name + " [Potion]");
        output.display("  Price: " + price + " gold");
        output.display("  Required Level: " + requiredLevel);
        output.display("  Type: " + type);
        output.display("  Effect: +" + effectAmount);
    }
    
    public PotionType getType() { return type; }
}
