package items;

/**
 * Represents a spell that heroes can cast to damage monsters and apply effects.
 * Spells have different types (Fire, Ice, Lightning) with unique effects.
 */
public class Spell extends Item {
    private int baseDamage;
    private int manaCost;
    private SpellType type;
    
    public Spell(String name, int price, int requiredLevel, int baseDamage, 
                 int manaCost, SpellType type) {
        super(name, price, requiredLevel, 3);
        this.baseDamage = baseDamage;
        this.manaCost = manaCost;
        this.type = type;
    }
    
    @Override
    public void displayInfo() {
        output.display(name + " [Spell]");
        output.display("  Price: " + price + " gold");
        output.display("  Required Level: " + requiredLevel);
        output.display("  Base Damage: " + baseDamage);
        output.display("  Mana Cost: " + manaCost);
        output.display("  Type: " + type);
        output.display("  Uses: " + uses + "/" + maxUses);
    }
    
    public int getBaseDamage() { return baseDamage; }
    public int getManaCost() { return manaCost; }
    public SpellType getType() { return type; }
}

