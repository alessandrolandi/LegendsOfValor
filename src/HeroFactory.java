import java.util.*;

import heroes.Hero;
import heroes.Paladin;
import heroes.Sorcerer;
import heroes.Warrior;

/**
 * Factory class for creating and managing hero instances.
 * Loads hero data from files and provides methods to retrieve heroes by type.
 */
public class HeroFactory {
    private static final String DATA_DIR = "Legends_Monsters_and_Heroes/";

    private static List<Hero> warriorDatabase = new ArrayList<>();
    private static List<Hero> paladinDatabase = new ArrayList<>();
    private static List<Hero> sorcererDatabase = new ArrayList<>();

    static {
        loadAllHeroes();
    }

    private static void loadAllHeroes() {
        loadHeroes("Warriors.txt", "warrior");
        loadHeroes("Paladins.txt", "paladin");
        loadHeroes("Sorcerers.txt", "sorcerer");
    }

    private static void loadHeroes(String filename, String type) {
        // Name/mana/strength/agility/dexterity/starting money/starting experience
        List<String[]> data = DataFileParser.parseFile(DATA_DIR + filename);

        for (String[] parts : data) {
            if (parts.length >= 7) {
                String name = parts[0];
                int mana = DataFileParser.parseInt(parts[1]);
                int strength = DataFileParser.parseInt(parts[2]);
                int agility = DataFileParser.parseInt(parts[3]);
                int dexterity = DataFileParser.parseInt(parts[4]);
                int money = DataFileParser.parseInt(parts[5]);
                int experience = DataFileParser.parseInt(parts[6]);

                Hero hero = createHero(type, name);
                if (hero != null) {
                    hero.setInitialStats(mana, strength, agility, dexterity, money, experience);

                    if (type.equals("warrior")) {
                        warriorDatabase.add(hero);
                    } else if (type.equals("paladin")) {
                        paladinDatabase.add(hero);
                    } else if (type.equals("sorcerer")) {
                        sorcererDatabase.add(hero);
                    }
                }
            }
        }
    }

    private static Hero createHero(String type, String name) {
        switch (type.toLowerCase()) {
            case "warrior":
                return new Warrior(name);
            case "paladin":
                return new Paladin(name);
            case "sorcerer":
                return new Sorcerer(name);
            default:
                return null;
        }
    }

    public static List<Hero> getAllHeroes() {
        List<Hero> all = new ArrayList<>();
        all.addAll(warriorDatabase);
        all.addAll(paladinDatabase);
        all.addAll(sorcererDatabase);
        return all;
    }

    public static List<Hero> getWarriors() {
        return new ArrayList<>(warriorDatabase);
    }

    public static List<Hero> getPaladins() {
        return new ArrayList<>(paladinDatabase);
    }

    public static List<Hero> getSorcerers() {
        return new ArrayList<>(sorcererDatabase);
    }
}
