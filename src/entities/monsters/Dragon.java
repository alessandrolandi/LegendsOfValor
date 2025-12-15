package entities.monsters;


/**
 * Dragon monster class with enhanced base damage.
 * Dragons are powerful offensive creatures with high attack power.
 */
public class Dragon extends Monster {
    private static final String[] DRAGON_NAMES = {
        "Red Dragon", "Blue Dragon", "Ancient Wyrm", "Fire Drake",
        "Storm Dragon", "Shadow Dragon", "Crystal Dragon"
    };

    public Dragon(String name, int level) {
        super(name, level);
    }

    public Dragon(int level) {
        super(DRAGON_NAMES[(int)(Math.random() * DRAGON_NAMES.length)], level);
    }

    @Override
    protected void applyMonsterBonuses() {
        // Dragons have increased base damage
        baseDamage = (int)(baseDamage * 1.2);
    }
}