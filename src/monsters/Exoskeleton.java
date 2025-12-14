package monsters;

/**
 * Exoskeleton monster class with enhanced defense.
 * Exoskeletons are heavily armored creatures that resist damage.
 */
public class Exoskeleton extends Monster {
    private static final String[] EXOSKELETON_NAMES = {
        "Giant Bug", "Armored Beetle", "Crystal Scorpion", "Iron Mantis",
        "Stone Golem", "Metal Spider", "Chitinous Beast"
    };

    public Exoskeleton(String name, int level) {
        super(name, level);
    }

    public Exoskeleton(int level) {
        super(EXOSKELETON_NAMES[(int)(Math.random() * EXOSKELETON_NAMES.length)], level);
    }

    @Override
    protected void applyMonsterBonuses() {
        // Exoskeletons have increased defense
        defense = (int)(defense * 1.5);
    }
}