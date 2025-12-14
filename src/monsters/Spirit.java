package monsters;

/**
 * Spirit monster class with enhanced dodge ability.
 * Spirits are evasive creatures that are difficult to hit in combat.
 */
public class Spirit extends Monster {
    private static final String[] SPIRIT_NAMES = {
        "Phantom", "Wraith", "Specter", "Ghost",
        "Shadow Spirit", "Ethereal Being", "Wisp"
    };

    public Spirit(String name, int level) {
        super(name, level);
    }

    @Override
    protected void applyMonsterBonuses() {
        // Spirits have increased dodge ability
        dodgeChance = (int)(dodgeChance * 1.3);
    }
}