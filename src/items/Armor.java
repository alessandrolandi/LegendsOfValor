package items;

/**
 * Represents an armor item that provides damage reduction when equipped.
 * Armor reduces incoming damage based on its damage reduction value.
 */
public class Armor extends Item {
    private int damageReduction;
    
    public Armor(String name, int price, int requiredLevel, int damageReduction) {
        super(name, price, requiredLevel, 10); 
        this.damageReduction = damageReduction;
    }
    
    @Override
    public void displayInfo() {
        output.display(name + " [Armor]");
        output.display("  Price: " + price + " gold");
        output.display("  Required Level: " + requiredLevel);
        output.display("  Damage Reduction: " + damageReduction);
        output.display("  Uses: " + uses + "/" + maxUses);
    }
    
    public int getReduction() { return damageReduction; }
}