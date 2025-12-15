package market;

import java.util.*;
import io.*;

import entities.heroes.Hero;
import items.Item;
import game.Party;
import factories.ItemFactory;

/**
 * Represents a market where heroes can buy and sell items.
 * Provides an interactive shopping interface for managing hero inventories.
 */
public class Market {
    private List<Item> inventory;
    private InputHandler input;
    private OutputHandler output;

    public Market() {
        this.inventory = ItemFactory.generateMarketInventory(20);
    }

    public void enter(Party party, InputHandler input, OutputHandler output) {
        this.input = input;
        this.output = output;
        output.display("\n=== WELCOME TO THE MARKET ===");
        
        while (true) {
            output.display("\nChoose a hero to shop with (or 0 to exit):");
            List<Hero> heroes = party.getHeroes();
            
            for (int i = 0; i < heroes.size(); i++) {
                output.display((i + 1) + ". " + heroes.get(i).getName() + 
                                 " (Gold: " + heroes.get(i).getGold() + ")");
            }
            output.display("0. Exit market");
            
            int choice = input.readInt( 0, heroes.size());
            if (choice == 0) break;
            
            Hero hero = heroes.get(choice - 1);
            shopMenu(hero);
        }
    }

    private void shopMenu(Hero hero) {
        while (true) {
            output.display("\n=== " + hero.getName() + "'s Shop Menu ===");
            output.display("Gold: " + hero.getGold());
            output.display("\n1. Buy items");
            output.display("2. Sell items");
            output.display("3. View inventory");
            output.display("0. Back");
            
            int choice = input.readInt( 0, 3);
            
            switch (choice) {
                case 0: return;
                case 1: buyMenu(hero); break;
                case 2: sellMenu(hero); break;
                case 3: viewInventory(hero); break;
            }
        }
    }

    private void buyMenu(Hero hero) {
        output.display("\n=== ITEMS FOR SALE ===");
        
        List<Item> availableItems = new ArrayList<>();
        for (Item item : inventory) {
            if (item.getRequiredLevel() <= hero.getLevel()) {
                availableItems.add(item);
            }
        }
        
        if (availableItems.isEmpty()) {
            output.display("No items available for your level!");
            return;
        }
        
        for (int i = 0; i < availableItems.size(); i++) {
            output.display("\n" + (i + 1) + ".");
            availableItems.get(i).displayInfo();
        }
        output.display("\n0. Back");

        int choice = input.readInt("\nSelect item to buy (0 to cancel): ", 0, availableItems.size());
        
        if (choice > 0) {
            Item item = availableItems.get(choice - 1);
            
            if (hero.getGold() >= item.getPrice()) {
                hero.spendGold(item.getPrice());
                hero.addItem(item);
                inventory.remove(item);
                output.display("Purchased " + item.getName() + "!");
            } else {
                output.display("Not enough gold!");
            }
        }
    }
    
    private void sellMenu(Hero hero) {
        List<Item> heroInventory = hero.getInventory();
        
        if (heroInventory.isEmpty()) {
            output.display("You have nothing to sell!");
            return;
        }
        
        output.display("\n=== YOUR INVENTORY ===");
        for (int i = 0; i < heroInventory.size(); i++) {
            output.display("\n" + (i + 1) + ".");
            heroInventory.get(i).displayInfo();
            output.display("  Sell Price: " + heroInventory.get(i).getSellPrice() + " gold");
        }
        output.display("\n0. Back");

        int choice = input.readInt("\nSelect item to sell (0 to cancel): ", 0, heroInventory.size());
        
        if (choice > 0) {
            Item item = heroInventory.get(choice - 1);
            hero.removeItem(item);
            hero.addGold(item.getSellPrice());
            inventory.add(item);
            output.display("Sold " + item.getName() + " for " + item.getSellPrice() + " gold!");
        }
    }
    
    private void viewInventory(Hero hero) {
        List<Item> heroInventory = hero.getInventory();
        
        if (heroInventory.isEmpty()) {
            output.display("\nInventory is empty!");
            return;
        }
        
        output.display("\n=== " + hero.getName() + "'s INVENTORY ===");
        for (int i = 0; i < heroInventory.size(); i++) {
            output.display("\n" + (i + 1) + ".");
            heroInventory.get(i).displayInfo();
        }
    }
}
