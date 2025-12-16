package game;

import board.*;
import entities.heroes.Hero;
import entities.monsters.Monster;
import factories.*;
import io.*;
import items.*;

import java.util.*;

/**
 * Main game class for Legends of Valor.
 * A MOBA-style strategic battle arena game with 3 lanes,
 * terrain bonuses, and objective-based gameplay.
 */
public class LegendsOfValorGame extends RPGGame {
    private LegendsOfValorBoard board;
    private List<Hero> heroes;
    private List<Monster> monsters;
    private int roundNumber;
    private int monstersSpawnInterval;
    private static final int BOARD_SIZE = 8;
    private static final int MONSTER_NEXUS_ROW = 0;
    private static final int HERO_NEXUS_ROW = 7;

    public LegendsOfValorGame() {
        super();
        this.heroes = new ArrayList<>();
        this.monsters = new ArrayList<>();
        this.roundNumber = 0;
        this.monstersSpawnInterval = 8; // Spawn every 8 rounds
    }

    public LegendsOfValorGame(java.util.Scanner scanner, io.OutputHandler output) {
        super(scanner, output);
        this.heroes = new ArrayList<>();
        this.monsters = new ArrayList<>();
        this.roundNumber = 0;
        this.monstersSpawnInterval = 8; // Spawn every 8 rounds
    }

    @Override
    public void start() {
        displayWelcome();
        initializeGame();
        if (isRunning) {
            gameLoop();
        }
    }

    @Override
    protected void displayWelcome() {
        output.display(Color.BOLD_YELLOW + "========================================" + Color.RESET);
        output.display(Color.BOLD_YELLOW + "       LEGENDS OF VALOR" + Color.RESET);
        output.display(Color.BOLD_YELLOW + "========================================" + Color.RESET);
        output.display("\nWelcome to the Arena!");
        output.display(Color.CYAN + "\nObjective:" + Color.RESET);
        output.display("- Reach the enemy Nexus to WIN");
        output.display("- Defend your Nexus from monsters");
        output.display(Color.CYAN + "\nControls:" + Color.RESET);
        output.display("  W - Move up    |  A - Move left");
        output.display("  S - Move down  |  D - Move right");
        output.display("  T - Teleport   |  R - Recall to Nexus");
        output.display("  A - Attack     |  C - Cast Spell");
        output.display("  P - Use Potion |  E - Equip Item");
        output.display("  M - Market (at Nexus)");
        output.display("  I - Info       |  Q - Quit");
        input.waitForEnter("\nPress Enter to begin...");
    }

    @Override
    protected void initializeGame() {
        output.display("\n" + Color.BOLD_CYAN + "=== BOARD SETUP ===" + Color.RESET);
        board = new LegendsOfValorBoard();
        output.display("3-Lane battle arena created!");

        // Hero selection with lane assignment
        selectHeroesAndLanes();

        if (heroes.size() != 3) {
            output.display("Error: Need exactly 3 heroes!");
            isRunning = false;
            return;
        }

        // Spawn initial heroes
        spawnHeroes();

        // Spawn initial monsters
        spawnMonsters();

        output.display("\nGame initialized! Battle begins!");
    }

    /**
     * Allows player to select 3 heroes and assign them to lanes.
     */
    private void selectHeroesAndLanes() {
        output.display(Color.BOLD_CYAN + "\n=== HERO SELECTION ===" + Color.RESET);
        List<Hero> availableHeroes = HeroFactory.getAllHeroes();

        if (availableHeroes.isEmpty()) {
            output.display("Error: No heroes available!");
            isRunning = false;
            return;
        }

        String[] laneNames = {"Top Lane", "Mid Lane", "Bot Lane"};

        for (int i = 0; i < 3; i++) {
            output.display(Color.GREEN + "\n--- Selecting hero for " + laneNames[i] + " ---" + Color.RESET);
            displayAvailableHeroes(availableHeroes);

            int choice = input.readInt("Select hero (1-" + availableHeroes.size() + "): ",
                                      1, availableHeroes.size());

            Hero selected = availableHeroes.get(choice - 1);
            selected.setOutputHandler(output);
            selected.setLane(i);
            heroes.add(selected);

            output.display(Color.GREEN + selected.getName() + " assigned to " + laneNames[i] + Color.RESET);
        }
    }

    private void displayAvailableHeroes(List<Hero> heroList) {
        for (int i = 0; i < heroList.size(); i++) {
            Hero hero = heroList.get(i);
            output.display((i + 1) + ". " + hero.getName() + " (" +
                          hero.getClass().getSimpleName() + ") - " +
                          "HP:" + (int)hero.getHp() + " MP:" + (int)hero.getMp() +
                          " STR:" + hero.getStrength());
        }
    }

    /**
     * Spawns heroes at their respective Nexus positions.
     */
    private void spawnHeroes() {
        for (Hero hero : heroes) {
            int lane = hero.getLane();
            int[] cols = board.getColumnsForLane(lane);
            int spawnCol = cols[0]; // Left column of lane

            hero.setPosition(HERO_NEXUS_ROW, spawnCol);
            hero.setSpawnPosition(HERO_NEXUS_ROW, spawnCol);

            Tile tile = board.getTile(HERO_NEXUS_ROW, spawnCol);
            tile.addHero(hero);
        }
    }

    /**
     * Spawns one monster per lane at the monster Nexus.
     */
    private void spawnMonsters() {
        int highestLevel = getHighestHeroLevel();

        for (int lane = 0; lane < 3; lane++) {
            Monster monster = MonsterFactory.createRandomMonster(highestLevel);
            monster.setOutputHandler(output);
            monster.setLane(lane);

            int[] cols = board.getColumnsForLane(lane);
            int spawnCol = cols[1]; // Right column of lane

            monster.setPosition(MONSTER_NEXUS_ROW, spawnCol);

            Tile tile = board.getTile(MONSTER_NEXUS_ROW, spawnCol);
            tile.addMonster(monster);

            monsters.add(monster);
        }

        output.display(Color.BOLD_RED +"\n 3 monsters have spawned!" + Color.RESET);
    }

    private int getHighestHeroLevel() {
        int maxLevel = 1;
        for (Hero hero : heroes) {
            if (hero.getLevel() > maxLevel) {
                maxLevel = hero.getLevel();
            }
        }
        return maxLevel;
    }

    @Override
    protected void gameLoop() {
        while (isRunning) {
            roundNumber++;
            output.display(Color.BOLD_CYAN + "\n" + "=".repeat(50) + Color.RESET);
            output.display(Color.BOLD_CYAN + "ROUND " + roundNumber + Color.RESET);
            output.display(Color.BOLD_CYAN + "=".repeat(50) + Color.RESET);

            // Display board
            displayBoard();

            // Heroes' turn
            output.display(Color.BOLD_GREEN + "\n>>> HEROES' TURN <<<" + Color.RESET);
            heroTurn();

            // Check if game was quit during heroes' turn
            if (!isRunning) {
                break;
            }

            // Check win condition
            if (checkVictory()) {
                output.display(Color.BOLD_GREEN + "\nVICTORY! A hero reached the monster Nexus!" + Color.RESET);
                isRunning = false;
                break;
            }

            // Monsters' turn
            output.display(Color.BOLD_RED + "\n>>> MONSTERS' TURN <<<" + Color.RESET);
            monsterTurn();

            // Check lose condition
            if (checkDefeat()) {
                output.display(Color.BOLD_RED + "\nDEFEAT! A monster reached your Nexus!" + Color.RESET);
                isRunning = false;
                break;
            }

            // End of round: regeneration
            endOfRound();

            // Spawn new monsters periodically
            if (roundNumber % monstersSpawnInterval == 0) {
                output.display(Color.RED + "\nNew wave of monsters approaching!" + Color.RESET);
                spawnMonsters();
            }
        }

        displayGameOver();
    }

    /**
     * Heroes take their turns.
     */
    private void heroTurn() {
        for (int i = 0; i < heroes.size(); i++) {
            Hero hero = heroes.get(i);

            if (hero.isFainted()) {
                output.display("\n" + hero.getName() + " is fainted and will respawn next round.");
                continue;
            }

            output.display(Color.BOLD_GREEN + "\n--- " + hero.getName() + "'s Turn (Lane " + (hero.getLane() + 1) + ") ---" + Color.RESET);
            output.display("Position: (" + hero.getRow() + ", " + hero.getCol() + ")");
            output.display("HP: " + (int)hero.getHp() + " | MP: " + (int)hero.getMp());

            performHeroAction(hero);

            // Check if game was quit during this hero's action
            if (!isRunning) {
                break;
            }
        }
    }

    /**
     * Prompts for and performs hero actions until a turn-ending action is taken.
     * Turn-ending actions: movement, teleport, recall, attack, cast spell
     * Non-turn-ending actions: potion, equip, market, info
     */
    private void performHeroAction(Hero hero) {
        boolean turnEnded = false;

        while (!turnEnded && isRunning) {
            output.display("\nActions: [W]Move Up [S]Down [A]Left [D]Right");
            output.display("         [T]Teleport [R]Recall [K]Attack [C]Spell");
            output.display("         [P]Potion [E]Equip [M]Market [I]Info [Q]Quit");

            String command = input.readLine("Choose action: ").trim().toLowerCase();

            if (command.isEmpty()) {
                output.display(Color.YELLOW + "No action taken." + Color.RESET);
                continue;
            }

            switch (command.charAt(0)) {
                case 'w':
                    moveHero(hero, -1, 0);
                    turnEnded = true;
                    break;
                case 's':
                    moveHero(hero, 1, 0);
                    turnEnded = true;
                    break;
                case 'a':
                    moveHero(hero, 0, -1);
                    turnEnded = true;
                    break;
                case 'd':
                    moveHero(hero, 0, 1);
                    turnEnded = true;
                    break;
                case 't':
                    teleportHero(hero);
                    turnEnded = true;
                    break;
                case 'r':
                    recallHero(hero);
                    turnEnded = true;
                    break;
                case 'k':
                    attackWithHero(hero);
                    turnEnded = true;
                    break;
                case 'c':
                    castSpellWithHero(hero);
                    turnEnded = true;
                    break;
                case 'p':
                    usePotionWithHero(hero);
                    // Does not end turn
                    break;
                case 'e':
                    equipItemWithHero(hero);
                    // Does not end turn
                    break;
                case 'm':
                    accessMarket(hero);
                    // Does not end turn
                    break;
                case 'i':
                    hero.displayStats();
                    // Does not end turn
                    break;
                case 'q':
                    if (input.readYesNo("Are you sure you want to quit the game?")) {
                        output.display("\nQuitting game...");
                        isRunning = false;
                        return;
                    }
                    break;
                default:
                    output.display("Invalid action!");
            }
        }
    }

    /**
     * Moves a hero in the specified direction.
     */
    private void moveHero(Hero hero, int rowDelta, int colDelta) {
        int newRow = hero.getRow() + rowDelta;
        int newCol = hero.getCol() + colDelta;

        // Validate movement
        if (!isValidMove(hero, newRow, newCol)) {
            output.display( Color.YELLOW + "Cannot move there!" + Color.RESET);
            return;
        }

        // Remove from old tile
        Tile oldTile = board.getTile(hero.getRow(), hero.getCol());
        oldTile.removeHero(hero);

        // Move to new tile
        hero.setPosition(newRow, newCol);
        Tile newTile = board.getTile(newRow, newCol);
        newTile.addHero(hero);

        output.display(Color.YELLOW+ hero.getName() + " moved to (" + newRow + ", " + newCol + ")" + Color.RESET);
    }

    /**
     * Validates if a hero can move to the target position.
     */
    private boolean isValidMove(Hero hero, int newRow, int newCol) {
        // Check bounds
        if (!board.isValidPosition(newRow, newCol)) {
            return false;
        }

        // Check if accessible
        if (!board.isAccessible(newRow, newCol)) {
            return false;
        }

        Tile targetTile = board.getTile(newRow, newCol);

        // Cannot move into space with another hero
        if (targetTile.hasHero()) {
            return false;
        }

        // Cannot move backward past a monster
        if (targetTile.hasMonster() && newRow < hero.getRow()) {
            return false;
        }

        return true;
    }

    /**
     * Teleports hero to adjacent space of another hero in different lane.
     */
    private void teleportHero(Hero hero) {
        output.display(Color.CYAN + "\nSelect target hero to teleport near:" + Color.RESET);
        for (int i = 0; i < heroes.size(); i++) {
            Hero h = heroes.get(i);
            if (h != hero && !h.isFainted()) {
                output.display(Color.CYAN + (i + 1) + ". " + h.getName() + " at (" +
                              h.getRow() + ", " + h.getCol() + ") Lane " + (h.getLane() + 1) + Color.RESET);
            }
        }

        int choice = input.readInt("Select (0 to cancel): ", 0, heroes.size());
        if (choice == 0) return;

        Hero target = heroes.get(choice - 1);

        // Check different lane
        if (target.getLane() == hero.getLane()) {
            output.display(Color.BOLD_YELLOW + "Can only teleport to different lanes!" + Color.RESET);
            return;
        }

        // Try to place adjacent to target
        int[][] adjacentOffsets = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] offset : adjacentOffsets) {
            int newRow = target.getRow() + offset[0];
            int newCol = target.getCol() + offset[1];

            if (isValidMove(hero, newRow, newCol)) {
                // Remove from old position
                Tile oldTile = board.getTile(hero.getRow(), hero.getCol());
                oldTile.removeHero(hero);

                // Teleport
                hero.setPosition(newRow, newCol);
                Tile newTile = board.getTile(newRow, newCol);
                newTile.addHero(hero);

                output.display(Color.CYAN + hero.getName() + " teleported to (" + newRow + ", " + newCol + ")!" + Color.RESET);
                return;
            }
        }

        output.display(Color.YELLOW + "No valid teleport position available!" + Color.RESET);
    }

    /**
     * Recalls hero to their spawn Nexus.
     */
    private void recallHero(Hero hero) {
        Tile oldTile = board.getTile(hero.getRow(), hero.getCol());
        oldTile.removeHero(hero);

        hero.recall();
        Tile nexusTile = board.getTile(hero.getRow(), hero.getCol());
        nexusTile.addHero(hero);

        output.display(Color.YELLOW + hero.getName() + " recalled to Nexus!" + Color.RESET);
    }

    /**
     * Hero attacks a monster in range.
     */
    private void attackWithHero(Hero hero) {
        List<Monster> inRange = getMonstersInRange(hero);

        if (inRange.isEmpty()) {
            output.display(Color.YELLOW + "No monsters in attack range!" + Color.RESET);
            return;
        }

        output.display(Color.RED + "\nMonsters in range:" + Color.RESET);
        for (int i = 0; i < inRange.size(); i++) {
            Monster m = inRange.get(i);
            output.display(Color.YELLOW + (i + 1) + ". " + m.getName() + " HP:" + (int)m.getHp() +
                          " at (" + m.getRow() + ", " + m.getCol() + ")" + Color.RESET);
        }

        int choice = input.readInt("Attack which monster? ", 1, inRange.size());
        Monster target = inRange.get(choice - 1);

        double damage = hero.attack(target);

        if (target.isDefeated()) {
            handleMonsterDefeat(target, hero);
        }
    }

    /**
     * Returns list of monsters within attack range of hero.
     */
    private List<Monster> getMonstersInRange(Hero hero) {
        List<Monster> inRange = new ArrayList<>();
        int heroRow = hero.getRow();
        int heroCol = hero.getCol();

        for (Monster monster : monsters) {
            if (monster.isDefeated()) continue;

            int mRow = monster.getRow();
            int mCol = monster.getCol();

            // Check if in adjacent cells (including diagonals not across walls)
            int rowDiff = Math.abs(heroRow - mRow);
            int colDiff = Math.abs(heroCol - mCol);

            if (rowDiff <= 1 && colDiff <= 1 && (rowDiff + colDiff) > 0) {
                // Make sure not across wall
                if (board.getLaneForColumn(heroCol) == board.getLaneForColumn(mCol) ||
                    colDiff == 0) {
                    inRange.add(monster);
                }
            }
        }

        return inRange;
    }

    /**
     * Hero casts a spell on a monster.
     */
    private void castSpellWithHero(Hero hero) {
        List<Monster> inRange = getMonstersInRange(hero);

        if (inRange.isEmpty()) {
            output.display(Color.YELLOW + "No monsters in range!" + Color.RESET);
            return;
        }

        // Get spells from inventory
        List<Spell> spells = new ArrayList<>();
        for (Item item : hero.getInventory()) {
            if (item instanceof Spell && item.isUsable()) {
                spells.add((Spell) item);
            }
        }

        if (spells.isEmpty()) {
            output.display(Color.YELLOW + "No spells available!" + Color.RESET);
            return;
        }

        output.display(Color.CYAN + "\nAvailable spells:" + Color.RESET);
        for (int i = 0; i < spells.size(); i++) {
            Spell s = spells.get(i);
            output.display((i + 1) + ". " + s.getName() + " (Mana:" + s.getManaCost() +
                          " Dmg:" + s.getBaseDamage() + ")");
        }

        int spellChoice = input.readInt("Cast which spell? (0 to cancel): ", 0, spells.size());
        if (spellChoice == 0) return;

        output.display("\nTarget:");
        for (int i = 0; i < inRange.size(); i++) {
            Monster m = inRange.get(i);
            output.display((i + 1) + ". " + m.getName() + " HP:" + (int)m.getHp());
        }

        int targetChoice = input.readInt("Select target: ", 1, inRange.size());

        Spell spell = spells.get(spellChoice - 1);
        Monster target = inRange.get(targetChoice - 1);

        double damage = hero.castSpell(spell, target);

        if (target.isDefeated()) {
            handleMonsterDefeat(target, hero);
        }
    }

    private void usePotionWithHero(Hero hero) {
        List<Potion> potions = new ArrayList<>();
        for (Item item : hero.getInventory()) {
            if (item instanceof Potion && item.isUsable()) {
                potions.add((Potion) item);
            }
        }

        if (potions.isEmpty()) {
            output.display("No potions available!");
            return;
        }

        output.display("\nAvailable potions:");
        for (int i = 0; i < potions.size(); i++) {
            potions.get(i).displayInfo();
        }

        int choice = input.readInt("Use which potion? (0 to cancel): ", 0, potions.size());
        if (choice == 0) return;

        Potion potion = potions.get(choice - 1);
        hero.usePotion(potion);
        hero.removeItem(potion);
    }

    private void equipItemWithHero(Hero hero) {
        output.display(Color.CYAN + "\n=== EQUIP ITEM ===" + Color.RESET);
        output.display("1. Equip main hand weapon");
        output.display("2. Equip off hand weapon");
        output.display("3. Equip armor");
        output.display("0. Back");

        int choice = input.readMenuChoice("Enter choice: ", 3);

        if (choice == 1) {
            equipMainHandWeapon(hero);
        } else if (choice == 2) {
            equipOffHandWeapon(hero);
        } else if (choice == 3) {
            equipArmor(hero);
        }
    }

    private void equipMainHandWeapon(Hero hero) {
        List<Weapon> weapons = getWeaponsFromInventory(hero);
        if (weapons.isEmpty()) {
            output.display(Color.YELLOW + "No weapons in inventory!" + Color.RESET);
            return;
        }

        output.display(Color.CYAN + "\nSelect weapon:" + Color.RESET);
        for (int i = 0; i < weapons.size(); i++) {
            Weapon w = weapons.get(i);
            output.display((i + 1) + ". " + w.getName() +
                " (Dmg: " + w.getDamage() +
                ", Hands: " + w.getHandsRequired() + ")");
        }
        output.display("0. Back");

        int choice = input.readMenuChoice(weapons.size());
        if (choice > 0) {
            hero.equipWeapon(weapons.get(choice - 1));
        }
    }

    private void equipOffHandWeapon(Hero hero) {
        List<Weapon> weapons = getWeaponsFromInventory(hero);
        if (weapons.isEmpty()) {
            output.display(Color.YELLOW + "No weapons in inventory!" + Color.RESET);
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
            output.display(Color.YELLOW + "No one-handed weapons in inventory!" + Color.RESET);
            return;
        }

        output.display(Color.CYAN + "\nSelect weapon:" + Color.RESET);
        for (int i = 0; i < oneHanded.size(); i++) {
            Weapon w = oneHanded.get(i);
            output.display((i + 1) + ". " + w.getName() +
                " (Dmg: " + w.getDamage() + ")");
        }
        output.display("0. Back");

        int choice = input.readMenuChoice(oneHanded.size());
        if (choice > 0) {
            hero.equipOffhandWeapon(oneHanded.get(choice - 1));
        }
    }

    private void equipArmor(Hero hero) {
        List<Armor> armors = getArmorsFromInventory(hero);
        if (armors.isEmpty()) {
            output.display(Color.YELLOW + "No armor in inventory!" + Color.RESET);
            return;
        }

        output.display(Color.CYAN + "\nSelect armor:" + Color.RESET);
        for (int i = 0; i < armors.size(); i++) {
            Armor a = armors.get(i);
            output.display((i + 1) + ". " + a.getName() +
                " (Def: " + a.getReduction() + ")");
        }
        output.display("0. Back");

        int choice = input.readMenuChoice(armors.size());
        if (choice > 0) {
            hero.equipArmor(armors.get(choice - 1));
        }
    }

    private List<Weapon> getWeaponsFromInventory(Hero hero) {
        List<Weapon> weapons = new ArrayList<>();
        for (Item item : hero.getInventory()) {
            if (item instanceof Weapon && item.isUsable()) {
                weapons.add((Weapon) item);
            }
        }
        return weapons;
    }

    private List<Armor> getArmorsFromInventory(Hero hero) {
        List<Armor> armors = new ArrayList<>();
        for (Item item : hero.getInventory()) {
            if (item instanceof Armor && item.isUsable()) {
                armors.add((Armor) item);
            }
        }
        return armors;
    }

    private void accessMarket(Hero hero) {
        Tile currentTile = board.getTile(hero.getRow(), hero.getCol());

        if (!(currentTile instanceof NexusTile)) {
            output.display(Color.YELLOW + "Must be at Nexus to access market!" + Color.RESET);
            return;
        }

        NexusTile nexus = (NexusTile) currentTile;
        if (nexus.hasMarket()) {
            // Create a temporary party with just this hero
            List<Hero> singleHero = new ArrayList<>();
            singleHero.add(hero);
            game.Party tempParty = new game.Party(singleHero);

            nexus.getMarket().enter(tempParty, input, output);
        }
    }

    /**
     * Handles monster defeat - gives rewards to all heroes.
     */
    private void handleMonsterDefeat(Monster monster, Hero killer) {
        output.display("\n" + monster.getName() + " defeated!");

        // Remove from board
        Tile tile = board.getTile(monster.getRow(), monster.getCol());
        tile.removeMonster(monster);
        monsters.remove(monster);

        // Rewards for all heroes
        int goldReward = 500 * monster.getLevel();
        int expReward = 2 * monster.getLevel();

        for (Hero hero : heroes) {
            hero.addGold(goldReward);
            hero.gainExperience(expReward);
        }

        output.display(Color.BOLD_GREEN + "All heroes gained " + goldReward + " gold and " + expReward + " XP!" + Color.RESET);
    }

    /**
     * Monsters take their turn.
     */
    private void monsterTurn() {
        for (Monster monster : new ArrayList<>(monsters)) {
            if (monster.isDefeated()) continue;

            // Check if any hero in attack range
            List<Hero> heroesInRange = getHeroesInRange(monster);

            if (!heroesInRange.isEmpty()) {
                // Attack random hero in range
                Hero target = heroesInRange.get(new Random().nextInt(heroesInRange.size()));
                output.display(monster.getName() + " attacks " + target.getName() + "!");
                monster.attack(target);

                if (target.isFainted()) {
                    output.display(Color.RED + target.getName() + " has been defeated!" + Color.RESET);
                }
            } else {
                // Move forward (south)
                moveMonsterForward(monster);
            }
        }
    }

    private List<Hero> getHeroesInRange(Monster monster) {
        List<Hero> inRange = new ArrayList<>();
        int mRow = monster.getRow();
        int mCol = monster.getCol();

        for (Hero hero : heroes) {
            if (hero.isFainted()) continue;

            int hRow = hero.getRow();
            int hCol = hero.getCol();

            int rowDiff = Math.abs(mRow - hRow);
            int colDiff = Math.abs(mCol - hCol);

            if (rowDiff <= 1 && colDiff <= 1 && (rowDiff + colDiff) > 0) {
                if (board.getLaneForColumn(mCol) == board.getLaneForColumn(hCol) ||
                    colDiff == 0) {
                    inRange.add(hero);
                }
            }
        }

        return inRange;
    }

    private void moveMonsterForward(Monster monster) {
        int newRow = monster.getRow() + 1;
        int newCol = monster.getCol();

        if (!board.isValidPosition(newRow, newCol)) {
            return;
        }

        Tile newTile = board.getTile(newRow, newCol);

        // Can't move into space with another monster
        if (newTile.hasMonster()) {
            return;
        }

        // Remove from old tile
        Tile oldTile = board.getTile(monster.getRow(), monster.getCol());
        oldTile.removeMonster(monster);

        // Move
        monster.setPosition(newRow, newCol);
        newTile.addMonster(monster);

        output.display(Color.YELLOW + monster.getName() + " advanced to (" + newRow + ", " + newCol + ")" + Color.RESET);
    }

    /**
     * End of round processing.
     */
    private void endOfRound() {
        // Regenerate heroes
        for (Hero hero : heroes) {
            if (!hero.isFainted()) {
                hero.regenerate();
            } else {
                // Respawn fainted heroes
                Tile oldTile = board.getTile(hero.getRow(), hero.getCol());
                if (oldTile != null) {
                    oldTile.removeHero(hero);
                }

                hero.revive();
                hero.recall();

                Tile nexusTile = board.getTile(hero.getRow(), hero.getCol());
                nexusTile.addHero(hero);

                output.display(Color.GREEN + hero.getName() + " respawned at Nexus!" + Color.RESET);
            }
        }
    }

    private boolean checkVictory() {
        for (Hero hero : heroes) {
            if (hero.getRow() == MONSTER_NEXUS_ROW) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDefeat() {
        for (Monster monster : monsters) {
            if (monster.getRow() == HERO_NEXUS_ROW) {
                return true;
            }
        }
        return false;
    }

    private void displayGameOver() {
        output.display("\n" + "=".repeat(50));
        output.display(Color.RED + "GAME OVER" + Color.RESET);
        output.display("=".repeat(50));
        output.display(Color.CYAN + "Final Stats:" + Color.RESET);
        for (Hero hero : heroes) {
            output.display(hero.getName() + " - Level " + hero.getLevel() +
                          " | Gold: " + hero.getGold());
        }
    }

    private String colorForSymbol(String symbol) {
    switch (symbol) {
        case "P": return Color.WHITE;         // Plain
        case "N": return Color.BOLD_CYAN;     // Nexus
        case "I": return Color.BRIGHT_BLACK;  // Inaccessible
        case "B": return Color.GREEN;          // Bush
        case "K": return Color.BOLD_YELLOW;   // Koulou
        case "C": return Color.BOLD_PURPLE;   // Cave
        default:  return Color.WHITE;
    }
}

    /**
     * Displays the current board state in the format shown in the PDF specification.
     */
    private void displayBoard() {
    output.display("\n");
    Tile[][] grid = board.getGrid();

    for (int row = 0; row < BOARD_SIZE; row++) {
        StringBuilder line1 = new StringBuilder();
        StringBuilder line2 = new StringBuilder();
        StringBuilder line3 = new StringBuilder();

        for (int col = 0; col < BOARD_SIZE; col++) {
            Tile tile = grid[row][col];
            String symbol = tile.getSymbol();

            // Map symbols
            if (symbol.equals(".")) {
                symbol = "P";
            } else if (symbol.equals("X")) {
                symbol = "I";
            }

            String color = colorForSymbol(symbol);

            // Line 1
            line1.append(color)
                 .append(symbol).append(" - ").append(symbol).append(" - ").append(symbol)
                 .append(Color.RESET);

            // Line 2
            String content = getTileContent(tile);


            String leftRightColor = color;   // for '|'
            String middleColor = color;      // for inside content

            if (content.contains("M")) {
                middleColor = Color.BOLD_RED;
            } else if (content.contains("H")) {
                middleColor = Color.BOLD_GREEN;
            }

            // Split content: |  M1     |
            char left = content.charAt(0);
            char right = content.charAt(content.length() - 1);
            String middle = content.substring(1, content.length() - 1);

            line2.append(leftRightColor).append(left).append(Color.RESET)
                .append(middleColor).append(middle).append(Color.RESET)
                .append(leftRightColor).append(right).append(Color.RESET);

            // Line 3
            line3.append(color)
                 .append(symbol).append(" - ").append(symbol).append(" - ").append(symbol)
                 .append(Color.RESET);

            if (col < BOARD_SIZE - 1) {
                line1.append("    ");
                line2.append("    ");
                line3.append("    ");
            }
        }

        output.display(line1.toString());
        output.display(line2.toString());
        output.display(line3.toString());

        if (row < BOARD_SIZE - 1) {
            output.display("");
        }
    }

    output.display("");
    displayBoardLegend();
}


    private void displayBoardHeader() {
        output.display("        [Top Lane]  [Wall]  [Mid Lane]  [Wall]  [Bot Lane]");
    }

    private String getTileContent(Tile tile) {
        // All cells must be exactly 9 characters: "|content|"
        // For walls (Inaccessible), show X X X
        if (!tile.isAccessible()) {
            return "| X X X |";  // 9 chars total
        }

        // Check for hero
        String heroStr = "";
        if (tile.hasHero()) {
            Hero hero = tile.getHeroes().get(0);
            int heroIndex = heroes.indexOf(hero) + 1;
            heroStr = "H" + heroIndex;
        }

        // Check for monster
        String monsterStr = "";
        if (tile.hasMonster()) {
            Monster monster = tile.getMonsters().get(0);
            int monsterIndex = monsters.indexOf(monster) + 1;
            if (monsterIndex > 0) {
                monsterStr = "M" + monsterIndex;
            } else {
                monsterStr = "M?";
            }
        }

        // Build content string and pad to exactly 9 characters total
        StringBuilder content = new StringBuilder("|");

        if (tile.hasHero() && tile.hasMonster()) {
            content.append(heroStr).append(" ").append(monsterStr);
        } else if (tile.hasHero()) {
            content.append(heroStr);
        } else if (tile.hasMonster()) {
            content.append(monsterStr);
        }

        // Pad with spaces to make total length 9
        while (content.length() < 8) {  // 8 because we add "|" at the end
            content.append(" ");
        }
        content.append("|");

        return content.toString();
    }

    private void displayBoardLegend() {
        output.display("Legend: N=Nexus I=Inaccessible P=Plain B=Bush C=Cave K=Koulou | H1/H2/H3=Heroes M#=Monsters");
    }

    @Override
    protected void processCommand(char command) {
        // Not used in this game
    }
}
