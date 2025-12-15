package factories;

import java.util.*;

import items.Armor;
import items.Item;
import items.Potion;
import items.PotionType;
import items.Spell;
import items.SpellType;
import items.Weapon;

/**
 * Factory class for creating and managing item instances.
 * Loads item data from files and generates market inventories.
 */
public class ItemFactory {
    private static final Random random = new Random();
    private static final String DATA_DIR = "Legends_Monsters_and_Heroes/";

    private static List<Weapon> weaponDatabase = new ArrayList<>();
    private static List<Armor> armorDatabase = new ArrayList<>();
    private static List<Spell> spellDatabase = new ArrayList<>();
    private static List<Potion> potionDatabase = new ArrayList<>();

    static {
        loadAllData();
    }

    private static void loadAllData() {
        loadWeapons();
        loadArmor();
        loadSpells();
        loadPotions();
    }

    private static void loadWeapons() {
        // Name/cost/level/damage/required hands
        List<String[]> data = DataFileParser.parseFile(DATA_DIR + "Weaponry.txt");
        for (String[] parts : data) {
            if (parts.length >= 5) {
                String name = parts[0];
                int price = DataFileParser.parseInt(parts[1]);
                int level = DataFileParser.parseInt(parts[2]);
                int damage = DataFileParser.parseInt(parts[3]);
                int hands = DataFileParser.parseInt(parts[4]);

                weaponDatabase.add(new Weapon(name, price, level, damage, hands));
            }
        }
    }

    private static void loadArmor() {
        // Name/cost/required level/damage reduction
        List<String[]> data = DataFileParser.parseFile(DATA_DIR + "Armory.txt");
        for (String[] parts : data) {
            if (parts.length >= 4) {
                String name = parts[0];
                int price = DataFileParser.parseInt(parts[1]);
                int level = DataFileParser.parseInt(parts[2]);
                int reduction = DataFileParser.parseInt(parts[3]);

                armorDatabase.add(new Armor(name, price, level, reduction));
            }
        }
    }

    private static void loadSpells() {
        loadSpellsFromFile("FireSpells.txt", SpellType.FIRE);
        loadSpellsFromFile("IceSpells.txt", SpellType.ICE);
        loadSpellsFromFile("LightningSpells.txt", SpellType.LIGHTNING);
    }

    private static void loadSpellsFromFile(String filename, SpellType type) {
        // Name/cost/required level/damage/mana cost
        List<String[]> data = DataFileParser.parseFile(DATA_DIR + filename);
        for (String[] parts : data) {
            if (parts.length >= 5) {
                String name = parts[0];
                int price = DataFileParser.parseInt(parts[1]);
                int level = DataFileParser.parseInt(parts[2]);
                int damage = DataFileParser.parseInt(parts[3]);
                int manaCost = DataFileParser.parseInt(parts[4]);

                spellDatabase.add(new Spell(name, price, level, damage, manaCost, type));
            }
        }
    }

    private static void loadPotions() {
        // Name/cost/required level/attribute increase/attribute affected
        List<String[]> data = DataFileParser.parseFile(DATA_DIR + "Potions.txt");
        for (String[] parts : data) {
            if (parts.length >= 5) {
                String name = parts[0];
                int price = DataFileParser.parseInt(parts[1]);
                int level = DataFileParser.parseInt(parts[2]);
                int effect = DataFileParser.parseInt(parts[3]);
                String attribute = parts[4];

                PotionType type = mapAttributeToType(attribute);
                potionDatabase.add(new Potion(name, price, level, type, effect));
            }
        }
    }

    private static PotionType mapAttributeToType(String attribute) {
        if (attribute.contains("Health")) return PotionType.HEALTH;
        if (attribute.contains("Mana")) return PotionType.MANA;
        if (attribute.contains("Strength")) return PotionType.STRENGTH;
        if (attribute.contains("Agility")) return PotionType.AGILITY;
        return PotionType.HEALTH; // default
    }

    public static List<Item> generateMarketInventory(int itemCount) {
        List<Item> items = new ArrayList<>();

        // Select random items from databases
        for (int i = 0; i < itemCount; i++) {
            int type = random.nextInt(4);
            switch (type) {
                case 0:
                    if (!weaponDatabase.isEmpty()) {
                        items.add(weaponDatabase.get(random.nextInt(weaponDatabase.size())));
                    }
                    break;
                case 1:
                    if (!armorDatabase.isEmpty()) {
                        items.add(armorDatabase.get(random.nextInt(armorDatabase.size())));
                    }
                    break;
                case 2:
                    if (!spellDatabase.isEmpty()) {
                        items.add(spellDatabase.get(random.nextInt(spellDatabase.size())));
                    }
                    break;
                case 3:
                    if (!potionDatabase.isEmpty()) {
                        items.add(potionDatabase.get(random.nextInt(potionDatabase.size())));
                    }
                    break;
            }
        }

        return items;
    }
}