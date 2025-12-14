import java.util.*;
import io.*;

import heroes.Hero;
import items.Armor;
import items.Item;
import items.Potion;
import items.Spell;
import items.Weapon;
import items.WeaponGrip;
import monsters.Monster;

/**
 * Manages combat encounters between a party of heroes and monsters.
 * Handles turn-based combat, including hero actions, monster attacks,
 * and battle resolution with rewards.
 */
public class Battle {
    private Party party;
    private List<Monster> monsters;
    private int defeatedMonsterCount;
    private int battleRow;
    private int battleCol;
    private InputHandler input;
    private OutputHandler output;

    public Battle(Party party, int row, int col, InputHandler input, OutputHandler output) {
        this.party = party;
        this.battleRow = row;
        this.battleCol = col;
        this.input = input;
        this.output = output;
        this.defeatedMonsterCount = 0;

        // Create monsters equal to party size, scaled to highest hero level
        int monsterCount = party.size();
        int monsterLevel = party.getHighestLevel();
        this.monsters = MonsterFactory.createMonsters(monsterCount, monsterLevel);
    }
    
    public boolean start() {
        output.displayHeader("BATTLE BEGINS!");
        output.displayLine();

        displayBattleState();

        int round = 1;
        while (!isBattleOver()) {
            output.displaySeparator("ROUND " + round);

            heroesTurn();

            if (allMonstersDefeated()) break;

            monstersTurn();

            if (party.allFainted()) break;

            party.regenerateAll();
            output.display("\nHeroes regenerated 10% HP and MP!");

            round++;
        }

        return resolveBattle();
    }
    
    private void heroesTurn() {
        output.displaySeparator("HEROES' TURN");

        for (Hero hero : party.getHeroes()) {
            if (hero.isFainted()) continue;

            output.display("\n" + hero.getName() + "'s turn:");
            output.display("HP: " + (int)hero.getHp() + " | MP: " + (int)hero.getMp());

            heroAction(hero);

            if (allMonstersDefeated()) break;
        }
    }
    
    private void heroAction(Hero hero) {
        while (true) {
            List<String> menuOptions = new ArrayList<>();
            menuOptions.add("Attack");
            menuOptions.add("Cast spell");
            menuOptions.add("Use potion");
            menuOptions.add("Equip weapon/armor");

            boolean hasWeapon = hero.getMainHandWeapon() != null;
            int gripOptionIndex = -1;
            if (hasWeapon) {
                gripOptionIndex = menuOptions.size() + 1; 
                menuOptions.add("Change weapon grip");
            }

            menuOptions.add("View stats");

            String[] menuArray = menuOptions.toArray(new String[0]);
            output.displayMenu("Choose action:", menuArray);

            int action = input.readMenuChoice("Enter choice: ", menuOptions.size());

            // Handle quit command
            if (action == -999) {
                if (input.readYesNo("Are you sure you want to quit the game?")) {
                    output.display("\nQuitting game...");
                    System.exit(0);
                }
                continue;
            }

            int currentOption = 1;

            if (action == currentOption++) {
                if (attackAction(hero)) return;
            } else if (action == currentOption++) {
                if (spellAction(hero)) return;
            } else if (action == currentOption++) {
                if (potionAction(hero)) return;
            } else if (action == currentOption++) {
                equipAction(hero);
            } else if (hasWeapon && action == currentOption++) {
                changeGripAction(hero);
            } else if (action == currentOption) {
                displayBattleState();
            }
        }
    }
    
    private boolean attackAction(Hero hero) {
        Monster target = selectMonster();
        if (target != null) {
            hero.attack(target);

            if (target.isDefeated()) {
                output.display(target.getName() + " was defeated!");
                monsters.remove(target);
                defeatedMonsterCount++;
            }
            return true;
        }
        return false;
    }
    
    private boolean spellAction(Hero hero) {
        List<Spell> spells = getSpells(hero);

        if (spells.isEmpty()) {
            output.display("No spells available!");
            return false;
        }

        output.display("\nSelect spell:");
        for (int i = 0; i < spells.size(); i++) {
            output.display((i + 1) + ". " + spells.get(i).getName() +
                             " (Mana: " + spells.get(i).getManaCost() + ")");
        }
        output.display("0. Back");

        int choice = input.readMenuChoice(spells.size());
        if (choice == 0) return false;

        Spell spell = spells.get(choice - 1);
        Monster target = selectMonster();

        if (target != null) {
            hero.castSpell(spell, target);

            if (!spell.isUsable()) {
                hero.removeItem(spell);
            }

            if (target.isDefeated()) {
                output.display(target.getName() + " was defeated!");
                monsters.remove(target);
                defeatedMonsterCount++;
            }
            return true;
        }
        return false;
    }
    
    private boolean potionAction(Hero hero) {
        List<Potion> potions = getPotions(hero);

        if (potions.isEmpty()) {
            output.display("No potions available!");
            return false;
        }

        output.display("\nSelect potion:");
        for (int i = 0; i < potions.size(); i++) {
            potions.get(i).displayInfo();
        }
        output.display("0. Back");

        int choice = input.readMenuChoice(potions.size());
        if (choice == 0) return false;

        Potion potion = potions.get(choice - 1);
        hero.usePotion(potion);
        hero.removeItem(potion);

        return true;
    }
    
    private void equipAction(Hero hero) {
        output.display("");
        output.displayMenu(null,
            "Equip main hand weapon",
            "Equip off hand weapon",
            "Equip armor");
        output.display("0. Back");

        int choice = input.readMenuChoice("Enter choice: ", 3);

        if (choice == 1) {
            List<Weapon> weapons = getWeapons(hero);
            if (weapons.isEmpty()) {
                output.display("No weapons in inventory!");
                return;
            }

            for (int i = 0; i < weapons.size(); i++) {
                output.display((i + 1) + ". " + weapons.get(i).getName());
            }

            int w = input.readInt(1, weapons.size());
            hero.equipWeapon(weapons.get(w - 1));
        } else if (choice == 2) {
            List<Weapon> weapons = getWeapons(hero);
            if (weapons.isEmpty()) {
                output.display("No weapons in inventory!");
                return;
            }

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

            for (int i = 0; i < oneHanded.size(); i++) {
                output.display((i + 1) + ". " + oneHanded.get(i).getName());
            }

            int w = input.readInt(1, oneHanded.size());
            hero.equipOffhandWeapon(oneHanded.get(w - 1));
        } else if (choice == 3) {
            List<Armor> armors = getArmors(hero);
            if (armors.isEmpty()) {
                output.display("No armor in inventory!");
                return;
            }

            for (int i = 0; i < armors.size(); i++) {
                output.display((i + 1) + ". " + armors.get(i).getName());
            }

            int a = input.readInt(1, armors.size());
            hero.equipArmor(armors.get(a - 1));
        }
    }

    private void changeGripAction(Hero hero) {
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
    
    private Monster selectMonster() {
        if (monsters.isEmpty()) return null;

        output.display("\nSelect target:");
        for (int i = 0; i < monsters.size(); i++) {
            Monster m = monsters.get(i);
            output.display((i + 1) + ". " + m.getName() +
                             " (HP: " + (int)m.getHp() + ")");
        }

        int choice = input.readInt(1, monsters.size());
        return monsters.get(choice - 1);
    }
    
    private void monstersTurn() {
        output.displaySeparator("MONSTERS' TURN");

        List<Hero> aliveHeroes = new ArrayList<>();
        for (Hero h : party.getHeroes()) {
            if (!h.isFainted()) aliveHeroes.add(h);
        }

        for (Monster monster : monsters) {
            if (aliveHeroes.isEmpty()) break;

            Hero target = aliveHeroes.get((int)(Math.random() * aliveHeroes.size()));
            monster.attack(target);

            if (target.isFainted()) {
                aliveHeroes.remove(target);
            }
        }
    }
    
    private boolean resolveBattle() {
        if (allMonstersDefeated()) {
            output.displayHeader("VICTORY!");

            int expGain = defeatedMonsterCount * 2;

            for (Hero hero : party.getHeroes()) {
                if (!hero.isFainted()) {
                    int goldGain = party.getHighestLevel() * 100;
                    hero.addGold(goldGain);
                    hero.gainExperience(expGain);
                    output.display(hero.getName() + " gained " + goldGain +
                                     " gold and " + expGain + " experience!");
                }
            }

            party.reviveAll();
            return true;
        } else {
            output.displayHeader("DEFEAT...");
            return false;
        }
    }
    
    private boolean isBattleOver() {
        return allMonstersDefeated() || party.allFainted();
    }
    
    private boolean allMonstersDefeated() {
        return monsters.isEmpty();
    }
    
    private void displayBattleState() {
        output.display("\n=== BATTLE STATUS ===");
        output.display("\nHEROES:");
        for (Hero hero : party.getHeroes()) {
            output.display("  " + hero.getName() +
                             " - HP: " + (int)hero.getHp() +
                             " | MP: " + (int)hero.getMp() +
                             (hero.isFainted() ? " [FAINTED]" : ""));
        }

        output.display("\nMONSTERS:");
        for (Monster monster : monsters) {
            output.display("  " + monster.getName() +
                             " - HP: " + (int)monster.getHp());
        }
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
    
    private List<Spell> getSpells(Hero hero) {
        List<Spell> spells = new ArrayList<>();
        for (Item item : hero.getInventory()) {
            if (item instanceof Spell && item.isUsable()) {
                spells.add((Spell) item);
            }
        }
        return spells;
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
}