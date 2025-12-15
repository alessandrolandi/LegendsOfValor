package entities.heroes;

import io.*;
import entities.*;

import java.util.*;

import items.Armor;
import items.Item;
import items.Potion;
import items.Spell;
import items.Weapon;
import items.WeaponGrip;
import entities.monsters.Monster;

/**
 * Abstract base class representing a hero character in the game.
 * Heroes are player-controlled creatures that can equip items, use spells,
 * and engage in combat with monsters.
 * Extends the Creature class to inherit common combat functionality.
 */
public abstract class Hero extends Creature {
    protected int experience;
    protected double mp;
    protected double maxMp;
    protected int strength;
    protected int dexterity;
    protected int agility;
    protected int gold;
    protected Inventory inventory;
    protected Weapon mainHandWeapon;
    protected Weapon offHandWeapon;
    protected WeaponGrip currentGrip;
    protected Armor equippedArmor;

    // Legends of Valor - Position and Lane tracking
    protected int row;
    protected int col;
    protected int lane;  // 0=top, 1=mid, 2=bot
    protected int spawnRow;
    protected int spawnCol;

    public Hero(String name) {
        this(name, new OutputHandler());
    }

    public Hero(String name, OutputHandler output) {
        super(name, 1, output);
        this.experience = 0;
        this.hp = 100;
        this.mp = 100;
        this.maxMp = 100;
        this.strength = 100;
        this.dexterity = 100;
        this.agility = 100;
        this.gold = 1000;
        this.inventory = new Inventory();
        this.currentGrip = WeaponGrip.ONE_HANDED;

        applyStartingBonuses();
    }

    public void setOutputHandler(OutputHandler output) {
        this.output = output;
    }

    public void setInitialStats(int mana, int strength, int agility, int dexterity, int money, int experience) {
        this.mp = mana;
        this.maxMp = mana;
        this.strength = strength;
        this.agility = agility;
        this.dexterity = dexterity;
        this.gold = money;
        this.experience = experience;

        applyStartingBonuses();
    }
    
    protected abstract void applyStartingBonuses();
    protected abstract void applyLevelUpBonuses();
    
    public void gainExperience(int exp) {
        this.experience += exp;
        int expNeeded = level * 10;
        
        while (experience >= expNeeded) {
            levelUp();
            expNeeded = level * 10;
        }
    }
    
    private void levelUp() {
        level++;
        output.display(name + " leveled up to level " + level + "!");

        hp = level * 100;
        
        mp = mp * 1.1;
        
        strength = (int)(strength * 1.05);
        dexterity = (int)(dexterity * 1.05);
        agility = (int)(agility * 1.05);
        
        applyLevelUpBonuses();
    }
    
    @Override
    public double attack(Creature target) {
        boolean isArmed = mainHandWeapon != null;
        WeaponGrip grip = isArmed ? currentGrip : WeaponGrip.ONE_HANDED;

        // Check mana cost for the grip
        if (isArmed) {
            int manaCost = grip.getManaCost();
            if (manaCost > mp) {
                output.display(name + " doesn't have enough mana for this grip!");
                return 0;
            }
            mp -= manaCost;
        }

        if (isArmed && grip == WeaponGrip.DUAL_WIELD && offHandWeapon != null) {
            return dualWieldAttack(target);
        }

        int weaponDamage = isArmed ? mainHandWeapon.getDamage() : 0;
        double damage = grip.calculateDamage(weaponDamage, strength);

        if (isArmed && Math.random() > grip.getAccuracyModifier()) {
            output.display(name + "'s attack missed!");
            return 0;
        }

        if (target.dodge()) {
            output.display(target.getName() + " dodged the attack!");
            return 0;
        }

        double actualDamage = target.takeDamage(damage);

        output.display(name + " attacked " + target.getName() + " for " + (int)actualDamage + " damage!");

        // Apply durability loss
        if (isArmed) {
            mainHandWeapon.use();
        }

        return actualDamage;
    }

    private double dualWieldAttack(Creature target) {
        double totalDamage = 0;

        // Main hand attack
        double mainDamage = currentGrip.calculateDamage(mainHandWeapon.getDamage(), strength);
        if (Math.random() <= currentGrip.getAccuracyModifier() && !target.dodge()) {
            double actualDamage = target.takeDamage(mainDamage);
            totalDamage += actualDamage;
            output.display(name + " hit with main hand for " + (int)actualDamage + " damage!");

            mainHandWeapon.use();
        } else {
            output.display(name + "'s main hand attack missed!");
        }

        // Off-hand attack
        double offDamage = currentGrip.calculateDamage(offHandWeapon.getDamage(), strength);
        if (Math.random() <= currentGrip.getAccuracyModifier() && !target.dodge()) {
            double actualDamage = target.takeDamage(offDamage);
            totalDamage += actualDamage;
            output.display(name + " hit with off hand for " + (int)actualDamage + " damage!");

            offHandWeapon.use();
        } else {
            output.display(name + "'s off hand attack missed!");
        }

        return totalDamage;
    }
    
    public double castSpell(Spell spell, Monster target) {
        if (spell.getManaCost() > mp) {
            output.display(name + " doesn't have enough mana!");
            return 0;
        }
        
        double damage = spell.getBaseDamage() + (dexterity / 10000.0) * spell.getBaseDamage();
        
        if (target.dodge()) {
            output.display(target.getName() + " dodged the spell!");
            mp -= spell.getManaCost();
            return 0;
        }
        
        double actualDamage = target.takeDamage(damage);
        target.applySpellEffect(spell.getType());
        
        mp -= spell.getManaCost();
        
        output.display(name + " cast " + spell.getName() + " on " + 
                         target.getName() + " for " + (int)actualDamage + " damage!");
        
        spell.use();
        return actualDamage;
    }
    
    public void usePotion(Potion potion) {
        potion.applyEffect(this);
        output.display(name + " used " + potion.getName() + "!");
        potion.use();
    }
    
    public void equipWeapon(Weapon weapon) {
        this.mainHandWeapon = weapon;
        // Auto-select appropriate grip
        if (weapon.canUseGrip(currentGrip)) {
            output.display(name + " equipped " + weapon.getName() + " (" + currentGrip.name() + ")");
        } else {
            currentGrip = weapon.getAllowedGrips().iterator().next();
            output.display(name + " equipped " + weapon.getName() + " (" + currentGrip.name() + ")");
        }
    }

    public void equipOffhandWeapon(Weapon weapon) {
        if (weapon.getHandsRequired() != 1) {
            output.display("Can only equip one-handed weapons in offhand!");
            return;
        }
        this.offHandWeapon = weapon;
        output.display(name + " equipped " + weapon.getName() + " in offhand");
    }

    public void setGrip(WeaponGrip grip) {
        if (mainHandWeapon == null) {
            output.display("No weapon equipped!");
            return;
        }

        if (!mainHandWeapon.canUseGrip(grip)) {
            output.display(mainHandWeapon.getName() + " cannot be used with " + grip.name() + " grip!");
            return;
        }

        if (grip == WeaponGrip.DUAL_WIELD && offHandWeapon == null) {
            output.display("Need an offhand weapon for dual wielding!");
            return;
        }

        this.currentGrip = grip;
        output.display(name + " switched to " + grip.name() + " grip");
    }
    
    public void equipArmor(Armor armor) {
        this.equippedArmor = armor;
        output.display(name + " equipped " + armor.getName());
    }

    @Override
    public double takeDamage(double damage) {
        double armorValue = equippedArmor != null ? equippedArmor.getReduction() : 0;

        // Calculate damage reduction from armor (percentage-based)
        double armorReduction = armorValue / (armorValue + 100.0);
        double actualDamage = damage * (1 - armorReduction);

        hp -= actualDamage;

        if (hp <= 0) {
            hp = 0;
            faint();
        }

        return actualDamage;
    }

    @Override
    public boolean dodge() {
        double dodgeChance = agility * 0.0002;
        return Math.random() < dodgeChance;
    }
    
    public void faint() {
        isFainted = true;
        output.display(name + " has fainted!");
    }
    
    public void revive() {
        isFainted = false;
        hp = level * 100 * 0.5;
        mp = mp * 0.5;
        output.display(name + " has been revived!");
    }
    
    public void regenerate() {
        if (!isFainted) {
            hp = Math.min(hp * 1.1, level * 100);
            // Regenerate 10% of max MP (not current MP) to avoid 0 MP trap
            mp = Math.min(mp + maxMp * 0.1, maxMp);
        }
    }
    
    public void addItem(Item item) {
        inventory.addItem(item);
    }

    public void removeItem(Item item) {
        inventory.removeItem(item);
    }
    
    public void addGold(int amount) {
        gold += amount;
    }
    
    public boolean spendGold(int amount) {
        if (gold >= amount) {
            gold -= amount;
            return true;
        }
        return false;
    }

    @Override
    public void displayStats() {
        output.display("=== " + name + " (" + getClass().getSimpleName() + ") ===");
        output.display("Level: " + level + " | XP: " + experience + "/" + (level * 10));
        output.display("HP: " + (int)hp + " | MP: " + (int)mp);
        output.display("Strength: " + strength + " | Dexterity: " + dexterity + " | Agility: " + agility);
        output.display("Gold: " + gold);
        output.display("Main Hand: " + (mainHandWeapon != null ? mainHandWeapon.getName() : "None"));
        output.display("Off Hand: " + (offHandWeapon != null ? offHandWeapon.getName() : "None"));
        output.display("Grip: " + currentGrip.name());
        output.display("Armor: " + (equippedArmor != null ? equippedArmor.getName() : "None"));
        output.display("Status: " + (isFainted ? "Fainted" : "Active"));
    }

    // Terrain Bonus Methods for Legends of Valor
    /**
     * Applies a strength bonus (e.g., from Koulou tile).
     */
    public void applyStrengthBonus(double multiplier) {
        strength = (int)(strength * (1 + multiplier));
    }

    /**
     * Removes a strength bonus.
     */
    public void removeStrengthBonus(double multiplier) {
        strength = (int)(strength / (1 + multiplier));
    }

    /**
     * Applies a dexterity bonus (e.g., from Bush tile).
     */
    public void applyDexterityBonus(double multiplier) {
        dexterity = (int)(dexterity * (1 + multiplier));
    }

    /**
     * Removes a dexterity bonus.
     */
    public void removeDexterityBonus(double multiplier) {
        dexterity = (int)(dexterity / (1 + multiplier));
    }

    /**
     * Applies an agility bonus (e.g., from Cave tile).
     */
    public void applyAgilityBonus(double multiplier) {
        agility = (int)(agility * (1 + multiplier));
    }

    /**
     * Removes an agility bonus.
     */
    public void removeAgilityBonus(double multiplier) {
        agility = (int)(agility / (1 + multiplier));
    }

    // Legends of Valor - Position and Lane Methods
    /**
     * Sets the hero's position on the board.
     */
    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Sets the hero's lane assignment.
     */
    public void setLane(int lane) {
        this.lane = lane;
    }

    /**
     * Sets the hero's spawn position (used for Recall).
     */
    public void setSpawnPosition(int row, int col) {
        this.spawnRow = row;
        this.spawnCol = col;
    }

    /**
     * Returns the hero to their spawn position (Recall action).
     */
    public void recall() {
        this.row = spawnRow;
        this.col = spawnCol;
    }

    // Getters
    public double getMp() { return mp; }
    public int getStrength() { return strength; }
    public int getDexterity() { return dexterity; }
    public int getAgility() { return agility; }
    public int getGold() { return gold; }
    public List<Item> getInventory() { return inventory.getItems(); }
    public Inventory getInventoryObject() { return inventory; }
    public boolean isFainted() { return isFainted; }
    public Weapon getMainHandWeapon() { return mainHandWeapon; }
    public Weapon getOffHandWeapon() { return offHandWeapon; }
    public WeaponGrip getCurrentGrip() { return currentGrip; }
    public Armor getEquippedArmor() { return equippedArmor; }

    // Position getters for Legends of Valor
    public int getRow() { return row; }
    public int getCol() { return col; }
    public int getLane() { return lane; }
    public int getSpawnRow() { return spawnRow; }
    public int getSpawnCol() { return spawnCol; }
}
