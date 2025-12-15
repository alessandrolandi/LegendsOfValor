package factories;

import java.util.*;

import entities.monsters.Dragon;
import entities.monsters.Exoskeleton;
import entities.monsters.Monster;
import entities.monsters.Spirit;

/**
 * Factory class for creating and managing monster instances.
 * Loads monster data from files and generates monsters for battles.
 */
public class MonsterFactory {
    private static final Random random = new Random();
    private static final String DATA_DIR = "Legends_Monsters_and_Heroes/";

    private static List<Monster> dragonDatabase = new ArrayList<>();
    private static List<Monster> exoskeletonDatabase = new ArrayList<>();
    private static List<Monster> spiritDatabase = new ArrayList<>();

    private static Map<Monster, int[]> originalStats = new HashMap<>();

    static {
        loadAllMonsters();
    }

    private static void loadAllMonsters() {
        loadMonsters("Dragons.txt", "dragon");
        loadMonsters("Exoskeletons.txt", "exoskeleton");
        loadMonsters("Spirits.txt", "spirit");
    }

    private static void loadMonsters(String filename, String type) {
        // Name/level/damage/defense/dodge chance
        List<String[]> data = DataFileParser.parseFile(DATA_DIR + filename);

        for (String[] parts : data) {
            if (parts.length >= 5) {
                String name = parts[0];
                int level = DataFileParser.parseInt(parts[1]);
                int damage = DataFileParser.parseInt(parts[2]);
                int defense = DataFileParser.parseInt(parts[3]);
                int dodgeChance = DataFileParser.parseInt(parts[4]);

                Monster monster = createMonster(type, name, level);
                if (monster != null) {
                    originalStats.put(monster, new int[]{damage, defense, dodgeChance});

                    monster.setStats(damage, defense, dodgeChance);

                    if (type.equals("dragon")) {
                        dragonDatabase.add(monster);
                    } else if (type.equals("exoskeleton")) {
                        exoskeletonDatabase.add(monster);
                    } else if (type.equals("spirit")) {
                        spiritDatabase.add(monster);
                    }
                }
            }
        }
    }

    private static Monster createMonster(String type, String name, int level) {
        switch (type.toLowerCase()) {
            case "dragon":
                return new Dragon(name, level);
            case "exoskeleton":
                return new Exoskeleton(name, level);
            case "spirit":
                return new Spirit(name, level);
            default:
                return null;
        }
    }

    public static List<Monster> createMonsters(int count, int level) {
        List<Monster> monsters = new ArrayList<>();
        List<Monster> allMonsters = new ArrayList<>();
        allMonsters.addAll(dragonDatabase);
        allMonsters.addAll(exoskeletonDatabase);
        allMonsters.addAll(spiritDatabase);

        if (allMonsters.isEmpty()) {
            return monsters; 
        }

        // Filter monsters at the exact requested level
        List<Monster> suitableMonsters = new ArrayList<>();
        for (Monster m : allMonsters) {
            if (m.getLevel() == level) {
                suitableMonsters.add(m);
            }
        }

        // If no suitable monsters, use all monsters
        if (suitableMonsters.isEmpty()) {
            suitableMonsters = allMonsters;
        }

        // Select random monsters and create fresh instances
        for (int i = 0; i < count; i++) {
            Monster template = suitableMonsters.get(random.nextInt(suitableMonsters.size()));

            String type = template.getClass().getSimpleName().toLowerCase();
            Monster newMonster = createMonster(type, template.getName(), template.getLevel());

            if (newMonster != null) {
                int[] stats = originalStats.get(template);
                if (stats != null) {
                    newMonster.setStats(stats[0], stats[1], stats[2]);
                }
                monsters.add(newMonster);
            }
        }

        return monsters;
    }

    /**
     * Creates a single random monster of the specified level.
     * Used by Legends of Valor for spawning.
     */
    public static Monster createRandomMonster(int level) {
        List<Monster> monsters = createMonsters(1, level);
        return monsters.isEmpty() ? null : monsters.get(0);
    }
}