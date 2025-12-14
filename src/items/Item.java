package items;
import io.*;

/**
 * Abstract base class representing an item in the game.
 * Provides common properties and behaviors for all item types.
 */
public abstract class Item {
    protected String name;
    protected int price;
    protected int requiredLevel;
    protected int uses;
    protected int maxUses;
    protected OutputHandler output;

    public Item(String name, int price, int requiredLevel, int maxUses) {
        this.name = name;
        this.price = price;
        this.requiredLevel = requiredLevel;
        this.maxUses = maxUses;
        this.uses = maxUses;
        this.output = new OutputHandler();
    }

    public void setOutputHandler(OutputHandler output) {
        this.output = output;
    }
    
    public void use() {
        uses--;
    }
    
    public boolean isUsable() {
        return uses > 0;
    }
    
    public int getSellPrice() {
        return price / 2;
    }
    
    public abstract void displayInfo();
    
    public String getName() { return name; }
    public int getPrice() { return price; }
    public int getRequiredLevel() { return requiredLevel; }
    public int getUses() { return uses; }
    public int getMaxUses() { return maxUses; }
}