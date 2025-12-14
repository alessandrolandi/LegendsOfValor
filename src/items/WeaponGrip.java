package items;

/**
 * Enum representing different weapon grips with varying effects.
 * Each grip provides different damage multipliers, mana costs, and accuracy modifiers.
 */
public enum WeaponGrip {

    ONE_HANDED(1.0, 0, 1.0, false),
    TWO_HANDED(1.5, 5, 1.0, false),
    DUAL_WIELD(1.0, 0, 0.8, true);

    private final double damageMultiplier;
    private final int manaCost;
    private final double accuracyModifier;
    private final boolean requiresOffhand;

    WeaponGrip(double damageMultiplier, int manaCost,
               double accuracyModifier, boolean requiresOffhand) {
        this.damageMultiplier = damageMultiplier;
        this.manaCost = manaCost;
        this.accuracyModifier = accuracyModifier;
        this.requiresOffhand = requiresOffhand;
    }

    public double calculateDamage(int baseWeaponDamage, int heroStrength) {
        return (heroStrength + baseWeaponDamage) * 0.05 * damageMultiplier;
    }

    public int getManaCost() {
        return manaCost;
    }

    public double getAccuracyModifier() {
        return accuracyModifier;
    }

    public boolean requiresOffhand() {
        return requiresOffhand;
    }
}
