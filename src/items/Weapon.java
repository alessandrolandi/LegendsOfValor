package items;

import java.util.*;

/**
 * Represents a weapon item that can be equipped by heroes.
 * Weapons have damage values and can be wielded in different grips.
 */
public class Weapon extends Item {
    private int damage;
    private int handsRequired;
    private Set<WeaponGrip> allowedGrips;

    public Weapon(String name, int price, int requiredLevel, int damage, int handsRequired) {
        super(name, price, requiredLevel, 10);
        this.damage = damage;
        this.handsRequired = handsRequired;
        this.allowedGrips = new HashSet<>();

        // Set allowed grips based on hands required
        if (handsRequired == 1) {
            allowedGrips.add(WeaponGrip.ONE_HANDED);
            allowedGrips.add(WeaponGrip.TWO_HANDED);
            allowedGrips.add(WeaponGrip.DUAL_WIELD);
        } else {
            allowedGrips.add(WeaponGrip.TWO_HANDED);
        }
    }

    @Override
    public void displayInfo() {
        output.display(name + " [Weapon]");
        output.display("  Price: " + price + " gold");
        output.display("  Required Level: " + requiredLevel);
        output.display("  Damage: " + damage);
        output.display("  Hands Required: " + handsRequired);
        output.display("  Uses: " + uses + "/" + maxUses);
    }

    public int getDamage() { return damage; }
    public int getHandsRequired() { return handsRequired; }

    public boolean canUseGrip(WeaponGrip grip) {
        return allowedGrips.contains(grip);
    }

    public Set<WeaponGrip> getAllowedGrips() {
        return new HashSet<>(allowedGrips);
    }
}

