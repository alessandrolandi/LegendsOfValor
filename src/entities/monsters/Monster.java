package entities.monsters;

import io.*;
import entities.*;

import entities.heroes.Hero;
import items.SpellType;

/**
 * Abstract base class representing a monster in the game.
 * Monsters are enemy creatures that heroes battle against.
 * Extends the Creature class to inherit common combat functionality.
 */
public abstract class Monster extends Creature {
    protected int baseDamage;
    protected int defense;
    protected int dodgeChance;

    // Legends of Valor - Position and Lane tracking
    protected int row;
    protected int col;
    protected int lane;  // 0=top, 1=mid, 2=bot

    public Monster(String name, int level) {
        this(name, level, new OutputHandler());
    }

    public Monster(String name, int level, OutputHandler output) {
        super(name, level, output);
        this.hp = level * 100;

        applyMonsterBonuses();
    }


    public void setStats(int damage, int defense, int dodgeChance) {
        this.baseDamage = damage;
        this.defense = defense;
        this.dodgeChance = dodgeChance;

        applyMonsterBonuses();
    }

    protected abstract void applyMonsterBonuses();

    @Override
    public double attack(Creature target) {
        if (target.dodge()) {
            output.display(target.getName() + " dodged " + name + "'s attack!");
            return 0;
        }

        double actualDamage = target.takeDamage(baseDamage * 0.10);

        output.display(name + " attacked " + target.getName() +
                         " for " + (int)actualDamage + " damage!");

        if (target.isFainted()) {
            output.display(target.getName() + " has fainted!");
        }

        return baseDamage;
    }

    @Override
    public double takeDamage(double damage) {
        double defenseReduction = Math.sqrt(defense) / 50.0;
        defenseReduction = Math.min(defenseReduction, 0.85);
        double actualDamage = damage * (1 - defenseReduction);

        hp -= actualDamage;

        if (hp < 0) hp = 0;

        return actualDamage;
    }

    @Override
    public boolean dodge() {
        double chance = dodgeChance * 0.01;
        return Math.random() < chance;
    }
    
    public void applySpellEffect(SpellType type) {
        switch (type) {
            case ICE:
                baseDamage = (int)(baseDamage * 0.9);
                output.display(name + "'s damage was reduced!");
                break;
            case FIRE:
                defense = (int)(defense * 0.9);
                output.display(name + "'s defense was reduced!");
                break;
            case LIGHTNING:
                dodgeChance = (int)(dodgeChance * 0.9);
                output.display(name + "'s dodge chance was reduced!");
                break;
        }
    }
    
    public boolean isDefeated() {
        return hp <= 0;
    }

    @Override
    public void displayStats() {
        output.display("=== " + name + " (" + getClass().getSimpleName() + ") ===");
        output.display("Level: " + level);
        output.display("HP: " + (int)hp);
        output.display("Damage: " + baseDamage);
        output.display("Defense: " + defense);
        output.display("Dodge Chance: " + dodgeChance + "%");
    }

    // Getters
    public int getBaseDamage() { return baseDamage; }
    public int getDefense() { return defense; }
    public int getDodgeChance() { return dodgeChance; }

    // Legends of Valor - Position and Lane Methods
    /**
     * Sets the monster's position on the board.
     */
    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Sets the monster's lane assignment.
     */
    public void setLane(int lane) {
        this.lane = lane;
    }

    /**
     * Moves the monster one step toward the heroes' Nexus (south/down).
     */
    public void moveForward() {
        this.row++;
    }

    // Position getters for Legends of Valor
    public int getRow() { return row; }
    public int getCol() { return col; }
    public int getLane() { return lane; }
}
