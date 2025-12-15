package game;

import java.util.*;
import java.util.Scanner;
import io.*;

import entities.heroes.Hero;
import entities.heroes.Paladin;
import entities.heroes.Sorcerer;
import entities.heroes.Warrior;
import items.*;
import board.*;
import combat.*;
import market.*;
import factories.*;

/**
 * Main game class for Legends: Monsters and Heroes.
 * A complete RPG game experience with hero selection, world exploration,
 * battles, and market interactions.
 */
public class HeroesAndMonstersGame extends RPGGame {
    protected Party party;
    protected HeroesAndMonstersBoard world;

    public HeroesAndMonstersGame() {
        super();
    }

    public HeroesAndMonstersGame(Scanner scanner, OutputHandler output) {
        super(scanner, output);
    }

    @Override
    public void start() {
        displayWelcome();
        initializeGame();
        gameLoop();
    }

    @Override
    protected void displayWelcome() {
        output.display("========================================");
        output.display("   LEGENDS: MONSTERS AND HEROES");
        output.display("========================================");
        output.display("\nWelcome, brave adventurer!");
        output.display("\nControls:");
        output.display("  W/w - Move up");
        output.display("  A/a - Move left");
        output.display("  S/s - Move down");
        output.display("  D/d - Move right");
        output.display("  I/i - Show information");
        output.display("  E/e - Manage equipment and inventory");
        output.display("  M/m - Enter market (when on market tile)");
        output.display("  Q/q - Quit game");
        input.waitForEnter("\nPress Enter to continue...");
    }

    @Override
    protected void initializeGame() {
        // Load all available heroes
        List<Hero> availableHeroes = HeroFactory.getAllHeroes();

        if (availableHeroes.isEmpty()) {
            output.display("Error: No heroes available!");
            isRunning = false;
            return;
        }

        output.display("\n=== HERO SELECTION ===");
        output.display("Choose 1-3 heroes for your party:\n");

        // Display all available heroes
        displayAvailableHeroes(availableHeroes);

        // Select heroes for party
        List<Hero> selectedHeroes = new ArrayList<>();
        int numHeroes = input.readInt("\nHow many heroes will join your party? (1-3): ", 1, 3);

        for (int i = 0; i < numHeroes; i++) {
            Hero hero = selectHero(availableHeroes, selectedHeroes, i + 1);
            if (hero != null) {
                selectedHeroes.add(hero);
                hero.setOutputHandler(output);
            }
        }

        if (selectedHeroes.isEmpty()) {
            output.display("Error: No heroes selected!");
            isRunning = false;
            return;
        }

        this.party = new Party(selectedHeroes);

        // Create world
        this.world = new HeroesAndMonstersBoard(8, 8, output);
        output.display("\nWorld created! Your adventure begins...\n");
    }

    private void displayAvailableHeroes(List<Hero> heroes) {
        for (int i = 0; i < heroes.size(); i++) {
            Hero hero = heroes.get(i);
            output.display((i + 1) + ". " + hero.getName() + " (" + hero.getClass().getSimpleName() + ")");
            output.display("   Level: " + hero.getLevel() +
                          " | HP: " + (int)hero.getHp() +
                          " | MP: " + (int)hero.getMp());
            output.display("   STR: " + hero.getStrength() +
                          " | DEX: " + hero.getDexterity() +
                          " | AGI: " + hero.getAgility());
            output.display("   Gold: " + hero.getGold());
            output.displayLine();
        }
    }

    private Hero selectHero(List<Hero> availableHeroes, List<Hero> selectedHeroes, int heroNum) {
        while (true) {
            int choice = input.readInt("\nSelect hero #" + heroNum + " (1-" + availableHeroes.size() + "): ",
                                      1, availableHeroes.size());

            Hero selectedHero = availableHeroes.get(choice - 1);

            // Check if hero already selected
            if (selectedHeroes.contains(selectedHero)) {
                output.display("That hero is already in your party! Choose another.");
                continue;
            }

            output.display("Selected: " + selectedHero.getName() + " the " +
                          selectedHero.getClass().getSimpleName());
            return selectedHero;
        }
    }

    @Override
    protected void gameLoop() {
        while (isRunning) {
            displayWorld();
            displayPartyPosition();

            String command = input.readLine("\nEnter command: ").trim().toLowerCase();

            if (command.isEmpty()) continue;

            processCommand(command.charAt(0));
        }

        output.display("\nThanks for playing! Goodbye!");
    }

    @Override
    protected void processCommand(char command) {
        switch (command) {
            case 'w':
                moveParty(-1, 0);
                break;
            case 's':
                moveParty(1, 0);
                break;
            case 'a':
                moveParty(0, -1);
                break;
            case 'd':
                moveParty(0, 1);
                break;
            case 'i':
                displayInfo();
                break;
            case 'e':
                manageInventory();
                break;
            case 'm':
                enterMarket();
                break;
            case 'q':
                quitGame();
                break;
            default:
                output.display("Invalid command!");
        }
    }

    private void moveParty(int rowDelta, int colDelta) {
        if (world.moveParty(rowDelta, colDelta)) {
            output.display("Party moved.");

            Tile currentTile = world.getCurrentTile();
            if (currentTile instanceof CommonTile) {
                // Random chance of battle
                if (Math.random() < 0.3) { // 30% chance
                    output.display("\nMonsters appear!");
                    startBattle();
                }
            }
        } else {
            output.display("Cannot move there!");
        }
    }

    protected void startBattle() {
        Battle battle = new Battle(party, world.getCurrentTile().getRow(),
                                  world.getCurrentTile().getCol(), input, output);
        boolean victory = battle.start();

        // Check if player quit during battle
        if (battle.didPlayerQuit()) {
            isRunning = false;
            return;
        }

        if (!victory) {
            output.display("\nAll heroes have fallen! GAME OVER!");
            isRunning = false;
        }
    }

    protected void displayInfo() {
        output.display("\n=== PARTY INFORMATION ===");
        for (Hero hero : party.getHeroes()) {
            hero.displayStats();
            output.displayLine();
        }
    }

    private void enterMarket() {
        Tile currentTile = world.getCurrentTile();
        if (currentTile instanceof MarketTile) {
            Market market = ((MarketTile) currentTile).getMarket();
            market.enter(party, input, output);
        } else {
            output.display("You are not at a market!");
        }
    }

    private void displayWorld() {
        world.display();
    }

    private void displayPartyPosition() {
        int[] pos = world.getPartyPosition();
        output.display("Party position: (" + pos[0] + ", " + pos[1] + ")");
    }

    private void manageInventory() {
        output.display("\n=== INVENTORY MANAGEMENT ===");

        // Select hero
        List<Hero> heroes = party.getHeroes();
        if (heroes.size() == 1) {
            manageHeroInventory(heroes.get(0));
        } else {
            output.display("Select hero:");
            for (int i = 0; i < heroes.size(); i++) {
                output.display((i + 1) + ". " + heroes.get(i).getName());
            }
            output.display("0. Back");

            int choice = input.readMenuChoice(heroes.size());
            if (choice > 0) {
                manageHeroInventory(heroes.get(choice - 1));
            }
        }
    }

    private void manageHeroInventory(Hero hero) {
        while (true) {
            output.display("\n=== " + hero.getName() + "'s Inventory ===");
            output.display("HP: " + (int)hero.getHp() + " | MP: " + (int)hero.getMp() + " | Gold: " + hero.getGold());

            output.displayMenu("Choose action:",
                "Equip weapon",
                "Equip armor",
                "Use potion",
                "Change weapon grip",
                "View inventory");
            output.display("0. Back");

            int choice = input.readMenuChoice("Enter choice: ", 5);

            // Handle quit command
            if (choice == -999) {
                if (input.readYesNo("Are you sure you want to quit the game?")) {
                    output.display("\nQuitting game...");
                    System.exit(0);
                }
                continue;
            }

            if (choice == 0) break;

            switch (choice) {
                case 1:
                    equipWeapon(hero);
                    break;
                case 2:
                    equipArmor(hero);
                    break;
                case 3:
                    usePotion(hero);
                    break;
                case 4:
                    changeWeaponGrip(hero);
                    break;
                case 5:
                    viewInventory(hero);
                    break;
            }
        }
    }

    private void equipWeapon(Hero hero) {
        output.display("");
        output.displayMenu(null,
            "Equip main hand weapon",
            "Equip off hand weapon");
        output.display("0. Back");

        int choice = input.readMenuChoice("Enter choice: ", 2);

        if (choice == 1) {
            List<Weapon> weapons = getWeapons(hero);
            if (weapons.isEmpty()) {
                output.display("No weapons in inventory!");
                return;
            }

            output.display("\nSelect weapon:");
            for (int i = 0; i < weapons.size(); i++) {
                output.display((i + 1) + ". " + weapons.get(i).getName() +
                    " (Dmg: " + weapons.get(i).getDamage() +
                    ", Hands: " + weapons.get(i).getHandsRequired() + ")");
            }
            output.display("0. Back");

            int w = input.readMenuChoice(weapons.size());
            if (w > 0) {
                hero.equipWeapon(weapons.get(w - 1));
            }
        } else if (choice == 2) {
            List<Weapon> weapons = getWeapons(hero);
            if (weapons.isEmpty()) {
                output.display("No weapons in inventory!");
                return;
            }

            // Filter for one-handed weapons only
            List<Weapon> oneHanded = new ArrayList<>();
            for (Weapon w : weapons) {
                if (w.getHandsRequired() == 1) {
                    oneHanded.add(w);
                }
            }

            if (oneHanded.isEmpty()) {
                output.display("No one-handed weapons in inventory!");
                return;
            }

            output.display("\nSelect weapon:");
            for (int i = 0; i < oneHanded.size(); i++) {
                output.display((i + 1) + ". " + oneHanded.get(i).getName() +
                    " (Dmg: " + oneHanded.get(i).getDamage() + ")");
            }
            output.display("0. Back");

            int w = input.readMenuChoice(oneHanded.size());
            if (w > 0) {
                hero.equipOffhandWeapon(oneHanded.get(w - 1));
            }
        }
    }

    private void equipArmor(Hero hero) {
        List<Armor> armors = getArmors(hero);
        if (armors.isEmpty()) {
            output.display("No armor in inventory!");
            return;
        }

        output.display("\nSelect armor:");
        for (int i = 0; i < armors.size(); i++) {
            output.display((i + 1) + ". " + armors.get(i).getName() +
                " (Def: " + armors.get(i).getReduction() + ")");
        }
        output.display("0. Back");

        int choice = input.readMenuChoice(armors.size());
        if (choice > 0) {
            hero.equipArmor(armors.get(choice - 1));
        }
    }

    private void usePotion(Hero hero) {
        List<Potion> potions = getPotions(hero);
        if (potions.isEmpty()) {
            output.display("No potions in inventory!");
            return;
        }

        output.display("\nSelect potion:");
        for (int i = 0; i < potions.size(); i++) {
            potions.get(i).displayInfo();
        }
        output.display("0. Back");

        int choice = input.readMenuChoice(potions.size());
        if (choice > 0) {
            Potion potion = potions.get(choice - 1);
            hero.usePotion(potion);
            hero.removeItem(potion);
        }
    }

    private void changeWeaponGrip(Hero hero) {
        if (hero.getMainHandWeapon() == null) {
            output.display("No weapon equipped!");
            return;
        }

        output.display("\nAvailable grips for " + hero.getMainHandWeapon().getName() + ":");
        Set<WeaponGrip> allowedGrips = hero.getMainHandWeapon().getAllowedGrips();
        List<WeaponGrip> gripList = new ArrayList<>(allowedGrips);

        for (int i = 0; i < gripList.size(); i++) {
            WeaponGrip grip = gripList.get(i);
            output.display((i + 1) + ". " + grip.name() +
                " (Dmg: x" + String.format("%.1f", grip.calculateDamage(100, 100) / 10.0) +
                ", Mana: " + grip.getManaCost() +
                ", Acc: " + (int)(grip.getAccuracyModifier() * 100) + "%)");
        }
        output.display("0. Back");

        int choice = input.readMenuChoice(gripList.size());
        if (choice > 0) {
            hero.setGrip(gripList.get(choice - 1));
        }
    }

    private void viewInventory(Hero hero) {
        output.display("\n=== " + hero.getName() + "'s Inventory ===");
        List<Item> inventory = hero.getInventory();

        if (inventory.isEmpty()) {
            output.display("Inventory is empty!");
        } else {
            for (Item item : inventory) {
                output.display("- " + item.getName() + " (" + item.getClass().getSimpleName() + ")");
            }
        }

        output.display("\nEquipped:");
        output.display("Main Hand: " + (hero.getMainHandWeapon() != null ? hero.getMainHandWeapon().getName() : "None"));
        output.display("Off Hand: " + (hero.getOffHandWeapon() != null ? hero.getOffHandWeapon().getName() : "None"));
        output.display("Armor: " + (hero.getEquippedArmor() != null ? hero.getEquippedArmor().getName() : "None"));

        input.waitForEnter("\nPress Enter to continue...");
    }

    private List<Weapon> getWeapons(Hero hero) {
        List<Weapon> weapons = new ArrayList<>();
        for (Item item : hero.getInventory()) {
            if (item instanceof Weapon && item.isUsable()) {
                weapons.add((Weapon) item);
            }
        }
        return weapons;
    }

    private List<Armor> getArmors(Hero hero) {
        List<Armor> armors = new ArrayList<>();
        for (Item item : hero.getInventory()) {
            if (item instanceof Armor && item.isUsable()) {
                armors.add((Armor) item);
            }
        }
        return armors;
    }

    private List<Potion> getPotions(Hero hero) {
        List<Potion> potions = new ArrayList<>();
        for (Item item : hero.getInventory()) {
            if (item instanceof Potion && item.isUsable()) {
                potions.add((Potion) item);
            }
        }
        return potions;
    }

    private void quitGame() {
        if (input.readYesNo("Are you sure you want to quit?")) {
            isRunning = false;
        }
    }

    /**
     * Gets the party of heroes in this game.
     * @return The party of heroes
     */
    public Party getParty() {
        return party;
    }

    /**
     * Gets the game world.
     * @return The game world
     */
    public HeroesAndMonstersBoard getWorld() {
        return world;
    }
}
