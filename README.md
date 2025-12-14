# CS611 - Assignment IV
## Legends: Monsters and Heroes

------------------------------------------------------------
- Alessandro Landi
- **Email:** al6723@bu.edu
- **Student ID:** U63309114
------------------------------------------------------------

### Overview

This is a turn-based RPG game where a party of heroes battles monsters in an 8x8 grid world. The game features:
- **Proper inheritance hierarchy** with RPGGame → HeroesAndMonsters → Game
- **Creature abstraction** allowing heroes and monsters to attack uniformly through polymorphism
- **Inventory management system** with dedicated Inventory class
- **Comprehensive documentation** with Javadoc comments on all classes
- Turn-based combat with weapons, spells, armor, and potions
- Market system for buying and selling equipment
- Multiple hero and monster types with unique abilities

### Files

#### Core Game Files
- **App.java** — Entry point for the game; initializes and starts the game.
- **RPGGame.java** — Abstract base class for all RPG games. Provides core functionality for game initialization, running the game loop, and handling user input/output.
- **HeroesAndMonsters.java** — Extends RPGGame to provide specific functionality for managing parties of heroes that battle against monsters.
- **Game.java** — Main game controller that extends HeroesAndMonsters. Manages the game loop, world navigation, and player commands. Handles hero selection, world creation, and coordinates between different game systems (battles, markets, inventory).
- **World.java** — Manages the 8x8 game world grid, tile placement, and party movement. Generates the world with random distribution of market tiles, inaccessible tiles, and common tiles.
- **Party.java** — Manages the group of heroes (1-3 heroes). Tracks party-wide operations like regeneration, revival, and collective stats.
- **Inventory.java** — Manages a collection of items with methods to add, remove, and query items by type (weapons, armor, potions, spells).

#### Battle System
- **Battle.java**
  - Implements the turn-based combat system between heroes and monsters.
  - Manages round progression, hero/monster turns, and victory/defeat conditions.
  - Handles combat actions: attacking, spell casting, potion usage, equipment changes, and weapon grip modifications.
  - Dynamically shows/hides menu options based on hero state (e.g., "Change weapon grip" only appears when weapon is equipped).
  - Awards gold and experience upon victory.

#### Character System

##### Base Classes
- **base/Creature.java** — Abstract base class for all creatures (heroes and monsters). Provides common combat functionality including:
  - Abstract methods for attack, takeDamage, dodge, and displayStats
  - Common properties: name, level, HP, fainted status
  - Allows polymorphic interactions between heroes and monsters in combat

##### Heroes
- **heroes/Hero.java** — Abstract base class for all hero types. Extends Creature. Manages:
  - Stats (HP, MP, strength, dexterity, agility, level, experience, gold)
  - Combat mechanics (attacking with/without weapons, spell casting, dodging)
  - Equipment system (weapons, armor, weapon grips)
  - Inventory management
  - Leveling system with stat scaling

- **heroes/Warrior.java** — Warrior class with bonuses to strength and agility (10% starting bonus, 5% per level).
- **heroes/Paladin.java** — Paladin class with bonuses to strength and dexterity (10% starting bonus, 5% per level).
- **heroes/Sorcerer.java** — Sorcerer class with bonuses to dexterity and agility (10% starting bonus, 5% per level).
- **heroes/Inventory.java** — Item inventory management class specific to heroes. Provides filtered access to items by category.

##### Monsters
- **monsters/Monster.java** — Abstract base class for all monsters. Extends Creature. Manages:
  - Stats (HP, level, damage, defense, dodge chance)
  - Combat mechanics (attacking heroes, taking damage, dodging)
  - Spell effects (damage reduction, defense reduction, dodge reduction)
  - HP initialized at level × 100

- **monsters/Dragon.java** — Dragon type with 20% increased base damage.
- **monsters/Exoskeleton.java** — Exoskeleton type with 10% increased defense.
- **monsters/Spirit.java** — Spirit type with 10% increased dodge chance.

#### Item System
- **Item.java** — Abstract base class for all items with common properties (name, price, level requirement, uses).

- **items/Weapon.java**
  - Represents weapons with damage, required hands, and allowed grips.
  - Supports multiple grip styles: ONE_HANDED, TWO_HANDED, DUAL_WIELD.
  - Tracks durability through use count.

- **items/WeaponGrip.java** — Enum defining weapon grip styles with different damage multipliers, mana costs, and accuracy modifiers:
  - ONE_HANDED: 1.0x damage, 0 mana, 100% accuracy
  - TWO_HANDED: 1.5x damage, 5 mana, 100% accuracy
  - DUAL_WIELD: 1.0x damage per weapon, 0 mana, 80% accuracy

- **items/Armor.java** — Armor pieces providing damage reduction through defense stat.

- **items/Spell.java** — Spells that deal damage and apply debuffs to monsters. Limited uses (3 per spell).

- **items/SpellType.java** — Enum defining spell types:
  - ICE: Reduces monster damage by 10%
  - FIRE: Reduces monster defense by 10%
  - LIGHTNING: Reduces monster dodge chance by 10%

- **items/Potion.java** — Consumable items that restore or boost hero stats. Single-use.

- **items/PotionType.java** — Enum defining potion effects:
  - HEALTH: Restores HP
  - MANA: Restores MP
  - STRENGTH: Increases strength
  - AGILITY: Increases agility
  - DEXTERITY: Increases dexterity

#### World/Tile System
- **Tile.java** — Abstract base class for all tile types on the world map.
- **CommonTile.java** — Regular tiles where battles can occur (30% chance when entering).
- **MarketTile.java** — Tiles containing markets where heroes can buy/sell items.
- **InaccessibleTile.java** — Blocked tiles that cannot be traversed.

#### Market System
- **Market.java** — Marketplace interface where heroes can:
  - Buy weapons, armor, spells, and potions
  - Sell items for half their purchase price
  - Each hero manages their own inventory and gold

#### Factory Classes
- **HeroFactory.java** — Loads hero data from configuration files and creates hero instances. Parses hero stats from Warriors.txt, Paladins.txt, and Sorcerers.txt.

- **MonsterFactory.java** — Loads monster data and creates monster instances for battles. Ensures fresh monster instances for each battle with proper stat initialization. Reads from Dragons.txt, Exoskeletons.txt, and Spirits.txt.

- **ItemFactory.java** — Loads item data from configuration files:
  - Weaponry.txt: Weapon definitions
  - Armory.txt: Armor definitions
  - FireSpells.txt, IceSpells.txt, LightningSpells.txt: Spell definitions
  - Potions.txt: Potion definitions
  - Provides starter weapons and armor for new heroes

#### Utility Classes
- **io/InputHandler.java** — Handles all user input with validation:
  - Menu choices with range validation
  - Integer input with min/max bounds
  - Yes/No confirmations
  - String input
  - Supports 'q' to quit at any menu

- **io/OutputHandler.java** — Manages all console output formatting:
  - Headers with decorative borders
  - Menu display
  - Separators and lines
  - General message display

- **DataFileParser.java** — Parses tab-delimited data files from the Legends_Monsters_and_Heroes directory. Handles integer parsing and data validation.

### How to Compile & Run

- Compile all source files:
  ```bash
  cd LegendsOfValor
  javac -d bin src/*.java src/base/*.java src/heroes/*.java src/monsters/*.java src/items/*.java src/io/*.java
  ```

- Run the game:
  ```bash
  cd bin
  java App
  ```

  Or compile and run from src directory:
  ```bash
  cd src
  javac *.java base/*.java heroes/*.java monsters/*.java items/*.java io/*.java
  java App
  ```

### Game Controls
```
W/w - Move up
A/a - Move left
S/s - Move down
D/d - Move right
I/i - Show party information
E/e - Manage equipment and inventory
M/m - Enter market (when on market tile)
Q/q - Quit game
```

### Sample Input & Output
```text
========================================
   LEGENDS: MONSTERS AND HEROES
========================================

Welcome, brave adventurer!

Controls:
  W/w - Move up
  A/a - Move left
  S/s - Move down
  D/d - Move right
  I/i - Show information
  E/e - Manage equipment and inventory
  M/m - Enter market (when on market tile)
  Q/q - Quit game

Press Enter to continue...


=== HERO SELECTION ===
Choose 1-3 heroes for your party:

1. Gaerdal_Ironhand (Warrior)
   Level: 1 | HP: 100 | MP: 100
   STR: 770 | DEX: 600 | AGI: 550
   Gold: 1354

2. Sehanine_Monnbow (Warrior)
   Level: 1 | HP: 100 | MP: 600
   STR: 770 | DEX: 500 | AGI: 880
   Gold: 2500

3. Muamman_Duathall (Warrior)
   Level: 1 | HP: 100 | MP: 300
   STR: 990 | DEX: 750 | AGI: 550
   Gold: 2546

4. Flandal_Steelskin (Warrior)
   Level: 1 | HP: 100 | MP: 200
   STR: 825 | DEX: 700 | AGI: 715
   Gold: 2500

5. Undefeated_Yoj (Warrior)
   Level: 1 | HP: 100 | MP: 400
   STR: 880 | DEX: 700 | AGI: 440
   Gold: 2500

6. Eunoia_Cyn (Warrior)
   Level: 1 | HP: 100 | MP: 400
   STR: 770 | DEX: 600 | AGI: 880
   Gold: 2500

7. Parzival (Paladin)
   Level: 1 | HP: 100 | MP: 300
   STR: 825 | DEX: 770 | AGI: 650
   Gold: 2500

8. Sehanine_Moonbow (Paladin)
   Level: 1 | HP: 100 | MP: 300
   STR: 825 | DEX: 770 | AGI: 700
   Gold: 2500

9. Skoraeus_Stonebones (Paladin)
   Level: 1 | HP: 100 | MP: 250
   STR: 715 | DEX: 385 | AGI: 600
   Gold: 2500

10. Garl_Glittergold (Paladin)
   Level: 1 | HP: 100 | MP: 100
   STR: 660 | DEX: 440 | AGI: 500
   Gold: 2500

11. Amaryllis_Astra (Paladin)
   Level: 1 | HP: 100 | MP: 500
   STR: 550 | DEX: 550 | AGI: 500
   Gold: 2500

12. Caliber_Heist (Paladin)
   Level: 1 | HP: 100 | MP: 400
   STR: 440 | DEX: 440 | AGI: 400
   Gold: 2500

13. Rillifane_Rallathil (Sorcerer)
   Level: 1 | HP: 100 | MP: 1300
   STR: 750 | DEX: 550 | AGI: 495
   Gold: 2500

14. Segojan_Earthcaller (Sorcerer)
   Level: 1 | HP: 100 | MP: 900
   STR: 800 | DEX: 715 | AGI: 550
   Gold: 2500

15. Reign_Havoc (Sorcerer)
   Level: 1 | HP: 100 | MP: 800
   STR: 800 | DEX: 880 | AGI: 880
   Gold: 2500

16. Reverie_Ashels (Sorcerer)
   Level: 1 | HP: 100 | MP: 900
   STR: 800 | DEX: 440 | AGI: 770
   Gold: 2500

17. Kalabar (Sorcerer)
   Level: 1 | HP: 100 | MP: 800
   STR: 850 | DEX: 660 | AGI: 440
   Gold: 2500

18. Skye_Soar (Sorcerer)
   Level: 1 | HP: 100 | MP: 1000
   STR: 700 | DEX: 550 | AGI: 440
   Gold: 2500


How many heroes will join your party? (1-3): 1

Select hero #1 (1-18): 1
Selected: Gaerdal_Ironhand the Warrior

World created! Your adventure begins...


=== WORLD MAP ===
Legend: P=Party, M=Market, X=Inaccessible, .=Common

P . . M M M . . 
. . M X . X X M 
. M . X . M . . 
M M . . M . X . 
. . M M M . X . 
. . M . X . X M 
. M M . X M . . 
. X . . M . . . 

Party position: (0, 0)

Enter command: d
Party moved.

=== WORLD MAP ===
Legend: P=Party, M=Market, X=Inaccessible, .=Common

. P . M M M . . 
. . M X . X X M 
. M . X . M . . 
M M . . M . X . 
. . M M M . X . 
. . M . X . X M 
. M M . X M . . 
. X . . M . . . 

Party position: (0, 1)

Enter command: d
Party moved.

=== WORLD MAP ===
Legend: P=Party, M=Market, X=Inaccessible, .=Common

. . P M M M . . 
. . M X . X X M 
. M . X . M . . 
M M . . M . X . 
. . M M M . X . 
. . M . X . X M 
. M M . X M . . 
. X . . M . . . 

Party position: (0, 2)

Enter command: s
Party moved.

=== WORLD MAP ===
Legend: P=Party, M=Market, X=Inaccessible, .=Common

. . . M M M . . 
. . P X . X X M 
. M . X . M . . 
M M . . M . X . 
. . M M M . X . 
. . M . X . X M 
. M M . X M . . 
. X . . M . . . 

Party position: (1, 2)

Enter command: m

=== WELCOME TO THE MARKET ===

Choose a hero to shop with (or 0 to exit):
1. Gaerdal_Ironhand (Gold: 1354)
0. Exit market
1

=== Gaerdal_Ironhand's Shop Menu ===
Gold: 1354

1. Buy items
2. Sell items
3. View inventory
0. Back
1

=== ITEMS FOR SALE ===

1.
Sword [Weapon]
  Price: 500 gold
  Required Level: 1
  Damage: 800
  Hands Required: 1
  Uses: 10/10

2.
Platinum_Shield [Armor]
  Price: 150 gold
  Required Level: 1
  Damage Reduction: 200
  Uses: 10/10

0. Back

Select item to buy (0 to cancel): 1
Purchased Sword!

=== Gaerdal_Ironhand's Shop Menu ===
Gold: 854

1. Buy items
2. Sell items
3. View inventory
0. Back
1

=== ITEMS FOR SALE ===

1.
Platinum_Shield [Armor]
  Price: 150 gold
  Required Level: 1
  Damage Reduction: 200
  Uses: 10/10

0. Back

Select item to buy (0 to cancel): 2
Enter number between 0 and 1 (or 'q' to quit): 
1
Purchased Platinum_Shield!

=== Gaerdal_Ironhand's Shop Menu ===
Gold: 704

1. Buy items
2. Sell items
3. View inventory
0. Back
0

Choose a hero to shop with (or 0 to exit):
1. Gaerdal_Ironhand (Gold: 704)
0. Exit market
0

=== WORLD MAP ===
Legend: P=Party, M=Market, X=Inaccessible, .=Common

. . . M M M . . 
. . P X . X X M 
. M . X . M . . 
M M . . M . X . 
. . M M M . X . 
. . M . X . X M 
. M M . X M . . 
. X . . M . . . 

Party position: (1, 2)

Enter command: s
Party moved.

=== WORLD MAP ===
Legend: P=Party, M=Market, X=Inaccessible, .=Common

. . . M M M . . 
. . M X . X X M 
. M P X . M . . 
M M . . M . X . 
. . M M M . X . 
. . M . X . X M 
. M M . X M . . 
. X . . M . . . 

Party position: (2, 2)

Enter command: s
Party moved.

=== WORLD MAP ===
Legend: P=Party, M=Market, X=Inaccessible, .=Common

. . . M M M . . 
. . M X . X X M 
. M . X . M . . 
M M P . M . X . 
. . M M M . X . 
. . M . X . X M 
. M M . X M . . 
. X . . M . . . 

Party position: (3, 2)

Enter command: d
Party moved.

Monsters appear!
╔══════════════════╗
║  BATTLE BEGINS!  ║
╚══════════════════╝


=== BATTLE STATUS ===

HEROES:
  Gaerdal_Ironhand - HP: 100 | MP: 100

MONSTERS:
  Blinky - HP: 100

═══ ROUND 1 ═══

═══ HEROES' TURN ═══

Gaerdal_Ironhand's turn:
HP: 100 | MP: 100

Choose action:
1. Attack
2. Cast spell
3. Use potion
4. Equip weapon/armor
5. View stats
Enter choice: 1

Select target:
1. Blinky (HP: 100)
1
Blinky dodged the attack!

═══ MONSTERS' TURN ═══
Blinky attacked Gaerdal_Ironhand for 45 damage!

Heroes regenerated 10% HP and MP!

═══ ROUND 2 ═══

═══ HEROES' TURN ═══

Gaerdal_Ironhand's turn:
HP: 60 | MP: 100

Choose action:
1. Attack
2. Cast spell
3. Use potion
4. Equip weapon/armor
5. View stats
Enter choice: 4

1. Equip main hand weapon
2. Equip off hand weapon
3. Equip armor
0. Back
Enter choice: 1
1. Sword
1
Gaerdal_Ironhand equipped Sword (ONE_HANDED)

Choose action:
1. Attack
2. Cast spell
3. Use potion
4. Equip weapon/armor
5. Change weapon grip
6. View stats
Enter choice: 4

1. Equip main hand weapon
2. Equip off hand weapon
3. Equip armor
0. Back
Enter choice: 3
1. Platinum_Shield
1
Gaerdal_Ironhand equipped Platinum_Shield

Choose action:
1. Attack
2. Cast spell
3. Use potion
4. Equip weapon/armor
5. Change weapon grip
6. View stats
Enter choice: 1

Select target:
1. Blinky (HP: 100)
1
Gaerdal_Ironhand attacked Blinky for 49 damage!

═══ MONSTERS' TURN ═══
Blinky attacked Gaerdal_Ironhand for 15 damage!

Heroes regenerated 10% HP and MP!

═══ ROUND 3 ═══

═══ HEROES' TURN ═══

Gaerdal_Ironhand's turn:
HP: 50 | MP: 100

Choose action:
1. Attack
2. Cast spell
3. Use potion
4. Equip weapon/armor
5. Change weapon grip
6. View stats
Enter choice: 1

Select target:
1. Blinky (HP: 50)
1
Blinky dodged the attack!

═══ MONSTERS' TURN ═══
Blinky attacked Gaerdal_Ironhand for 15 damage!

Heroes regenerated 10% HP and MP!

═══ ROUND 4 ═══

═══ HEROES' TURN ═══

Gaerdal_Ironhand's turn:
HP: 38 | MP: 100

Choose action:
1. Attack
2. Cast spell
3. Use potion
4. Equip weapon/armor
5. Change weapon grip
6. View stats
Enter choice: 1

Select target:
1. Blinky (HP: 50)
1
Gaerdal_Ironhand attacked Blinky for 49 damage!

═══ MONSTERS' TURN ═══
Blinky attacked Gaerdal_Ironhand for 15 damage!

Heroes regenerated 10% HP and MP!

═══ ROUND 5 ═══

═══ HEROES' TURN ═══

Gaerdal_Ironhand's turn:
HP: 25 | MP: 100

Choose action:
1. Attack
2. Cast spell
3. Use potion
4. Equip weapon/armor
5. Change weapon grip
6. View stats
Enter choice: 1

Select target:
1. Blinky (HP: 1)
1
Gaerdal_Ironhand attacked Blinky for 49 damage!
Blinky was defeated!
╔════════════╗
║  VICTORY!  ║
╚════════════╝
Gaerdal_Ironhand gained 100 gold and 2 experience!

=== WORLD MAP ===
Legend: P=Party, M=Market, X=Inaccessible, .=Common

. . . M M M . . 
. . M X . X X M 
. M . X . M . . 
M M . P M . X . 
. . M M M . X . 
. . M . X . X M 
. M M . X M . . 
. X . . M . . . 

Party position: (3, 3)

Enter command: q
Are you sure you want to quit? (y/n): n

=== WORLD MAP ===
Legend: P=Party, M=Market, X=Inaccessible, .=Common

. . . M M M . . 
. . M X . X X M 
. M . X . M . . 
M M . P M . X . 
. . M M M . X . 
. . M . X . X M 
. M M . X M . . 
. X . . M . . . 

Party position: (3, 3)

Enter command: e

=== INVENTORY MANAGEMENT ===

=== Gaerdal_Ironhand's Inventory ===
HP: 25 | MP: 100 | Gold: 804

Choose action:
1. Equip weapon
2. Equip armor
3. Use potion
4. Change weapon grip
5. View inventory
0. Back
Enter choice: 3
No potions in inventory!

=== Gaerdal_Ironhand's Inventory ===
HP: 25 | MP: 100 | Gold: 804

Choose action:
1. Equip weapon
2. Equip armor
3. Use potion
4. Change weapon grip
5. View inventory
0. Back
Enter choice: 0

=== WORLD MAP ===
Legend: P=Party, M=Market, X=Inaccessible, .=Common

. . . M M M . . 
. . M X . X X M 
. M . X . M . . 
M M . P M . X . 
. . M M M . X . 
. . M . X . X M 
. M M . X M . . 
. X . . M . . . 

Party position: (3, 3)

Enter command: q
Are you sure you want to quit? (y/n): y

Thanks for playing! Goodbye!
```

### Architecture & Design

#### Inheritance Hierarchy
The game follows a clear object-oriented inheritance structure:

```
RPGGame (abstract base for all RPG games)
  └── HeroesAndMonsters (abstract base for heroes vs monsters games)
        └── Game (concrete implementation)

Creature (abstract base for all combatants)
  ├── Hero (abstract base for all heroes)
  │     ├── Warrior
  │     ├── Paladin
  │     └── Sorcerer
  └── Monster (abstract base for all monsters)
        ├── Dragon
        ├── Exoskeleton
        └── Spirit
```

#### Key Design Principles
- **Polymorphism**: Heroes and monsters both extend Creature, allowing them to attack each other using the same interface
- **Encapsulation**: Inventory class encapsulates item management logic
- **Abstraction**: Abstract base classes (Creature, Hero, Monster, RPGGame) define common behavior while allowing subclass-specific implementations

### Design Patterns Used
1. **Factory Pattern** — HeroFactory, MonsterFactory, and ItemFactory encapsulate object creation logic.
2. **Template Method Pattern** — Abstract classes (RPGGame, HeroesAndMonsters, Creature, Hero, Monster, Item, Tile) define common structure with subclass-specific implementations.
3. **Strategy Pattern** — WeaponGrip enum implements different combat strategies (damage, accuracy, mana cost).
4. **Singleton Pattern** — Factory classes use static initialization blocks to load data once.
5. **Inheritance Pattern** — Multi-level inheritance hierarchy from RPGGame → HeroesAndMonsters → Game, and Creature → Hero/Monster → specific types.

### Data Files
The game reads from configuration files in the `Legends_Monsters_and_Heroes/` directory:
- Warriors.txt, Paladins.txt, Sorcerers.txt (hero stats)
- Dragons.txt, Exoskeletons.txt, Spirits.txt (monster stats)
- Weaponry.txt, Armory.txt (equipment stats)
- FireSpells.txt, IceSpells.txt, LightningSpells.txt (spell stats)
- Potions.txt (potion stats)

All files use tab-delimited format with headers describing the data structure.
