package entities.heroes;

import items.*;
import java.util.*;

/**
 * Represents an inventory that holds and manages a collection of items.
 * Provides methods to add, remove, and query items in the inventory.
 */
public class Inventory {
    private List<Item> items;

    /**
     * Creates a new empty inventory.
     */
    public Inventory() {
        this.items = new ArrayList<>();
    }

    /**
     * Adds an item to the inventory.
     * @param item The item to add
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Removes an item from the inventory.
     * @param item The item to remove
     * @return true if the item was removed, false otherwise
     */
    public boolean removeItem(Item item) {
        return items.remove(item);
    }

    /**
     * Gets all items in the inventory.
     * @return A list of all items
     */
    public List<Item> getItems() {
        return new ArrayList<>(items);
    }

    /**
     * Gets all weapons in the inventory.
     * @return A list of all weapons
     */
    public List<Weapon> getWeapons() {
        List<Weapon> weapons = new ArrayList<>();
        for (Item item : items) {
            if (item instanceof Weapon && item.isUsable()) {
                weapons.add((Weapon) item);
            }
        }
        return weapons;
    }

    /**
     * Gets all armor pieces in the inventory.
     * @return A list of all armor
     */
    public List<Armor> getArmor() {
        List<Armor> armor = new ArrayList<>();
        for (Item item : items) {
            if (item instanceof Armor && item.isUsable()) {
                armor.add((Armor) item);
            }
        }
        return armor;
    }

    /**
     * Gets all potions in the inventory.
     * @return A list of all potions
     */
    public List<Potion> getPotions() {
        List<Potion> potions = new ArrayList<>();
        for (Item item : items) {
            if (item instanceof Potion && item.isUsable()) {
                potions.add((Potion) item);
            }
        }
        return potions;
    }

    /**
     * Gets all spells in the inventory.
     * @return A list of all spells
     */
    public List<Spell> getSpells() {
        List<Spell> spells = new ArrayList<>();
        for (Item item : items) {
            if (item instanceof Spell && item.isUsable()) {
                spells.add((Spell) item);
            }
        }
        return spells;
    }

    /**
     * Checks if the inventory is empty.
     * @return true if the inventory is empty, false otherwise
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * Gets the number of items in the inventory.
     * @return The number of items
     */
    public int size() {
        return items.size();
    }

    /**
     * Clears all items from the inventory.
     */
    public void clear() {
        items.clear();
    }
}
