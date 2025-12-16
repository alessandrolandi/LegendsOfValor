# CS611 - Assignment IV
## Legends of Valor

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
        RPG GAME COLLECTION
========================================

Select a game to play:

1. Monsters and Heroes
   Classic RPG adventure with heroes,
   monsters, markets, and exploration

2. Legends of Valor
   MOBA-style strategic battle arena
   with 3 lanes and team combat

0. Exit
========================================
Enter your choice: 2
========================================
       LEGENDS OF VALOR
========================================

Welcome to the Arena!

Objective:
- Reach the enemy Nexus to WIN
- Defend your Nexus from monsters

Controls:
  W - Move up    |  A - Move left
  S - Move down  |  D - Move right
  T - Teleport   |  R - Recall to Nexus
  A - Attack     |  C - Cast Spell
  P - Use Potion |  E - Equip Item
  M - Market (at Nexus)
  I - Info       |  Q - Quit

Press Enter to begin...


=== BOARD SETUP ===
3-Lane battle arena created!

=== HERO SELECTION ===

--- Selecting hero for Top Lane ---
1. Gaerdal_Ironhand (Warrior) - HP:100 MP:100 STR:770
2. Sehanine_Monnbow (Warrior) - HP:100 MP:600 STR:770
3. Muamman_Duathall (Warrior) - HP:100 MP:300 STR:990
4. Flandal_Steelskin (Warrior) - HP:100 MP:200 STR:825
5. Undefeated_Yoj (Warrior) - HP:100 MP:400 STR:880
6. Eunoia_Cyn (Warrior) - HP:100 MP:400 STR:770
7. Parzival (Paladin) - HP:100 MP:300 STR:825
8. Sehanine_Moonbow (Paladin) - HP:100 MP:300 STR:825
9. Skoraeus_Stonebones (Paladin) - HP:100 MP:250 STR:715
10. Garl_Glittergold (Paladin) - HP:100 MP:100 STR:660
11. Amaryllis_Astra (Paladin) - HP:100 MP:500 STR:550
12. Caliber_Heist (Paladin) - HP:100 MP:400 STR:440
13. Rillifane_Rallathil (Sorcerer) - HP:100 MP:1300 STR:750
14. Segojan_Earthcaller (Sorcerer) - HP:100 MP:900 STR:800
15. Reign_Havoc (Sorcerer) - HP:100 MP:800 STR:800
16. Reverie_Ashels (Sorcerer) - HP:100 MP:900 STR:800
17. Kalabar (Sorcerer) - HP:100 MP:800 STR:850
18. Skye_Soar (Sorcerer) - HP:100 MP:1000 STR:700
Select hero (1-18): 1
Gaerdal_Ironhand assigned to Top Lane

--- Selecting hero for Mid Lane ---
1. Gaerdal_Ironhand (Warrior) - HP:100 MP:100 STR:770
2. Sehanine_Monnbow (Warrior) - HP:100 MP:600 STR:770
3. Muamman_Duathall (Warrior) - HP:100 MP:300 STR:990
4. Flandal_Steelskin (Warrior) - HP:100 MP:200 STR:825
5. Undefeated_Yoj (Warrior) - HP:100 MP:400 STR:880
6. Eunoia_Cyn (Warrior) - HP:100 MP:400 STR:770
7. Parzival (Paladin) - HP:100 MP:300 STR:825
8. Sehanine_Moonbow (Paladin) - HP:100 MP:300 STR:825
9. Skoraeus_Stonebones (Paladin) - HP:100 MP:250 STR:715
10. Garl_Glittergold (Paladin) - HP:100 MP:100 STR:660
11. Amaryllis_Astra (Paladin) - HP:100 MP:500 STR:550
12. Caliber_Heist (Paladin) - HP:100 MP:400 STR:440
13. Rillifane_Rallathil (Sorcerer) - HP:100 MP:1300 STR:750
14. Segojan_Earthcaller (Sorcerer) - HP:100 MP:900 STR:800
15. Reign_Havoc (Sorcerer) - HP:100 MP:800 STR:800
16. Reverie_Ashels (Sorcerer) - HP:100 MP:900 STR:800
17. Kalabar (Sorcerer) - HP:100 MP:800 STR:850
18. Skye_Soar (Sorcerer) - HP:100 MP:1000 STR:700
Select hero (1-18): 2
Sehanine_Monnbow assigned to Mid Lane

--- Selecting hero for Bot Lane ---
1. Gaerdal_Ironhand (Warrior) - HP:100 MP:100 STR:770
2. Sehanine_Monnbow (Warrior) - HP:100 MP:600 STR:770
3. Muamman_Duathall (Warrior) - HP:100 MP:300 STR:990
4. Flandal_Steelskin (Warrior) - HP:100 MP:200 STR:825
5. Undefeated_Yoj (Warrior) - HP:100 MP:400 STR:880
6. Eunoia_Cyn (Warrior) - HP:100 MP:400 STR:770
7. Parzival (Paladin) - HP:100 MP:300 STR:825
8. Sehanine_Moonbow (Paladin) - HP:100 MP:300 STR:825
9. Skoraeus_Stonebones (Paladin) - HP:100 MP:250 STR:715
10. Garl_Glittergold (Paladin) - HP:100 MP:100 STR:660
11. Amaryllis_Astra (Paladin) - HP:100 MP:500 STR:550
12. Caliber_Heist (Paladin) - HP:100 MP:400 STR:440
13. Rillifane_Rallathil (Sorcerer) - HP:100 MP:1300 STR:750
14. Segojan_Earthcaller (Sorcerer) - HP:100 MP:900 STR:800
15. Reign_Havoc (Sorcerer) - HP:100 MP:800 STR:800
16. Reverie_Ashels (Sorcerer) - HP:100 MP:900 STR:800
17. Kalabar (Sorcerer) - HP:100 MP:800 STR:850
18. Skye_Soar (Sorcerer) - HP:100 MP:1000 STR:700
Select hero (1-18): 3
Muamman_Duathall assigned to Bot Lane

 3 monsters have spawned!

Game initialized! Battle begins!

==================================================
ROUND 1
==================================================


N - N - N    N - N - N    I - I - I    N - N - N    N - N - N    I - I - I    N - N - N    N - N - N
|       |    |M1     |    | X X X |    |       |    |M2     |    | X X X |    |       |    |M3     |
N - N - N    N - N - N    I - I - I    N - N - N    N - N - N    I - I - I    N - N - N    N - N - N

C - C - C    P - P - P    I - I - I    P - P - P    P - P - P    I - I - I    K - K - K    P - P - P
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
C - C - C    P - P - P    I - I - I    P - P - P    P - P - P    I - I - I    K - K - K    P - P - P

P - P - P    P - P - P    I - I - I    C - C - C    K - K - K    I - I - I    P - P - P    C - C - C
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
P - P - P    P - P - P    I - I - I    C - C - C    K - K - K    I - I - I    P - P - P    C - C - C

K - K - K    P - P - P    I - I - I    C - C - C    P - P - P    I - I - I    B - B - B    P - P - P
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
K - K - K    P - P - P    I - I - I    C - C - C    P - P - P    I - I - I    B - B - B    P - P - P

K - K - K    P - P - P    I - I - I    P - P - P    B - B - B    I - I - I    P - P - P    K - K - K
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
K - K - K    P - P - P    I - I - I    P - P - P    B - B - B    I - I - I    P - P - P    K - K - K

K - K - K    P - P - P    I - I - I    C - C - C    P - P - P    I - I - I    B - B - B    B - B - B
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
K - K - K    P - P - P    I - I - I    C - C - C    P - P - P    I - I - I    B - B - B    B - B - B

C - C - C    B - B - B    I - I - I    B - B - B    C - C - C    I - I - I    B - B - B    K - K - K
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
C - C - C    B - B - B    I - I - I    B - B - B    C - C - C    I - I - I    B - B - B    K - K - K

N - N - N    N - N - N    I - I - I    N - N - N    N - N - N    I - I - I    N - N - N    N - N - N
|H1     |    |       |    | X X X |    |H2     |    |       |    | X X X |    |H3     |    |       |
N - N - N    N - N - N    I - I - I    N - N - N    N - N - N    I - I - I    N - N - N    N - N - N

Legend: N=Nexus I=Inaccessible P=Plain B=Bush C=Cave K=Koulou | H1/H2/H3=Heroes M#=Monsters

>>> HEROES' TURN <<<

--- Gaerdal_Ironhand's Turn (Lane 1) ---
Position: (7, 0)
HP: 100 | MP: 100

Actions: [W]Move Up [S]Down [A]Left [D]Right
         [T]Teleport [R]Recall [K]Attack [C]Spell
         [P]Potion [E]Equip [M]Market [I]Info [Q]Quit
Choose action: W
Gaerdal_Ironhand moved to (6, 0)

--- Sehanine_Monnbow's Turn (Lane 2) ---
Position: (7, 3)
HP: 100 | MP: 600

Actions: [W]Move Up [S]Down [A]Left [D]Right
         [T]Teleport [R]Recall [K]Attack [C]Spell
         [P]Potion [E]Equip [M]Market [I]Info [Q]Quit
Choose action: W
Sehanine_Monnbow moved to (6, 3)

--- Muamman_Duathall's Turn (Lane 3) ---
Position: (7, 6)
HP: 100 | MP: 300

Actions: [W]Move Up [S]Down [A]Left [D]Right
         [T]Teleport [R]Recall [K]Attack [C]Spell
         [P]Potion [E]Equip [M]Market [I]Info [Q]Quit
Choose action: W
Muamman_Duathall moved to (6, 6)

>>> MONSTERS' TURN <<<
Casper advanced to (1, 1)
Blinky advanced to (1, 4)
Casper advanced to (1, 7)

==================================================
ROUND 2
==================================================


N - N - N    N - N - N    I - I - I    N - N - N    N - N - N    I - I - I    N - N - N    N - N - N
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
N - N - N    N - N - N    I - I - I    N - N - N    N - N - N    I - I - I    N - N - N    N - N - N

C - C - C    P - P - P    I - I - I    P - P - P    P - P - P    I - I - I    K - K - K    P - P - P
|       |    |M1     |    | X X X |    |       |    |M2     |    | X X X |    |       |    |M3     |
C - C - C    P - P - P    I - I - I    P - P - P    P - P - P    I - I - I    K - K - K    P - P - P

P - P - P    P - P - P    I - I - I    C - C - C    K - K - K    I - I - I    P - P - P    C - C - C
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
P - P - P    P - P - P    I - I - I    C - C - C    K - K - K    I - I - I    P - P - P    C - C - C

K - K - K    P - P - P    I - I - I    C - C - C    P - P - P    I - I - I    B - B - B    P - P - P
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
K - K - K    P - P - P    I - I - I    C - C - C    P - P - P    I - I - I    B - B - B    P - P - P

K - K - K    P - P - P    I - I - I    P - P - P    B - B - B    I - I - I    P - P - P    K - K - K
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
K - K - K    P - P - P    I - I - I    P - P - P    B - B - B    I - I - I    P - P - P    K - K - K

K - K - K    P - P - P    I - I - I    C - C - C    P - P - P    I - I - I    B - B - B    B - B - B
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
K - K - K    P - P - P    I - I - I    C - C - C    P - P - P    I - I - I    B - B - B    B - B - B

C - C - C    B - B - B    I - I - I    B - B - B    C - C - C    I - I - I    B - B - B    K - K - K
|H1     |    |       |    | X X X |    |H2     |    |       |    | X X X |    |H3     |    |       |
C - C - C    B - B - B    I - I - I    B - B - B    C - C - C    I - I - I    B - B - B    K - K - K

N - N - N    N - N - N    I - I - I    N - N - N    N - N - N    I - I - I    N - N - N    N - N - N
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
N - N - N    N - N - N    I - I - I    N - N - N    N - N - N    I - I - I    N - N - N    N - N - N

Legend: N=Nexus I=Inaccessible P=Plain B=Bush C=Cave K=Koulou | H1/H2/H3=Heroes M#=Monsters

>>> HEROES' TURN <<<

--- Gaerdal_Ironhand's Turn (Lane 1) ---
Position: (6, 0)
HP: 100 | MP: 100

Actions: [W]Move Up [S]Down [A]Left [D]Right
         [T]Teleport [R]Recall [K]Attack [C]Spell
         [P]Potion [E]Equip [M]Market [I]Info [Q]Quit
Choose action: W
Gaerdal_Ironhand moved to (5, 0)

--- Sehanine_Monnbow's Turn (Lane 2) ---
Position: (6, 3)
HP: 100 | MP: 600

Actions: [W]Move Up [S]Down [A]Left [D]Right
         [T]Teleport [R]Recall [K]Attack [C]Spell
         [P]Potion [E]Equip [M]Market [I]Info [Q]Quit
Choose action: W
Sehanine_Monnbow moved to (5, 3)

--- Muamman_Duathall's Turn (Lane 3) ---
Position: (6, 6)
HP: 100 | MP: 300

Actions: [W]Move Up [S]Down [A]Left [D]Right
         [T]Teleport [R]Recall [K]Attack [C]Spell
         [P]Potion [E]Equip [M]Market [I]Info [Q]Quit
Choose action: W
Muamman_Duathall moved to (5, 6)

>>> MONSTERS' TURN <<<
Casper advanced to (2, 1)
Blinky advanced to (2, 4)
Casper advanced to (2, 7)

==================================================
ROUND 3
==================================================


N - N - N    N - N - N    I - I - I    N - N - N    N - N - N    I - I - I    N - N - N    N - N - N
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
N - N - N    N - N - N    I - I - I    N - N - N    N - N - N    I - I - I    N - N - N    N - N - N

C - C - C    P - P - P    I - I - I    P - P - P    P - P - P    I - I - I    K - K - K    P - P - P
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
C - C - C    P - P - P    I - I - I    P - P - P    P - P - P    I - I - I    K - K - K    P - P - P

P - P - P    P - P - P    I - I - I    C - C - C    K - K - K    I - I - I    P - P - P    C - C - C
|       |    |M1     |    | X X X |    |       |    |M2     |    | X X X |    |       |    |M3     |
P - P - P    P - P - P    I - I - I    C - C - C    K - K - K    I - I - I    P - P - P    C - C - C

K - K - K    P - P - P    I - I - I    C - C - C    P - P - P    I - I - I    B - B - B    P - P - P
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
K - K - K    P - P - P    I - I - I    C - C - C    P - P - P    I - I - I    B - B - B    P - P - P

K - K - K    P - P - P    I - I - I    P - P - P    B - B - B    I - I - I    P - P - P    K - K - K
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
K - K - K    P - P - P    I - I - I    P - P - P    B - B - B    I - I - I    P - P - P    K - K - K

K - K - K    P - P - P    I - I - I    C - C - C    P - P - P    I - I - I    B - B - B    B - B - B
|H1     |    |       |    | X X X |    |H2     |    |       |    | X X X |    |H3     |    |       |
K - K - K    P - P - P    I - I - I    C - C - C    P - P - P    I - I - I    B - B - B    B - B - B

C - C - C    B - B - B    I - I - I    B - B - B    C - C - C    I - I - I    B - B - B    K - K - K
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
C - C - C    B - B - B    I - I - I    B - B - B    C - C - C    I - I - I    B - B - B    K - K - K

N - N - N    N - N - N    I - I - I    N - N - N    N - N - N    I - I - I    N - N - N    N - N - N
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
N - N - N    N - N - N    I - I - I    N - N - N    N - N - N    I - I - I    N - N - N    N - N - N

Legend: N=Nexus I=Inaccessible P=Plain B=Bush C=Cave K=Koulou | H1/H2/H3=Heroes M#=Monsters

>>> HEROES' TURN <<<

--- Gaerdal_Ironhand's Turn (Lane 1) ---
Position: (5, 0)
HP: 100 | MP: 100

Actions: [W]Move Up [S]Down [A]Left [D]Right
         [T]Teleport [R]Recall [K]Attack [C]Spell
         [P]Potion [E]Equip [M]Market [I]Info [Q]Quit
Choose action: W
Gaerdal_Ironhand moved to (4, 0)

--- Sehanine_Monnbow's Turn (Lane 2) ---
Position: (5, 3)
HP: 100 | MP: 600

Actions: [W]Move Up [S]Down [A]Left [D]Right
         [T]Teleport [R]Recall [K]Attack [C]Spell
         [P]Potion [E]Equip [M]Market [I]Info [Q]Quit
Choose action: W
Sehanine_Monnbow moved to (4, 3)

--- Muamman_Duathall's Turn (Lane 3) ---
Position: (5, 6)
HP: 100 | MP: 300

Actions: [W]Move Up [S]Down [A]Left [D]Right
         [T]Teleport [R]Recall [K]Attack [C]Spell
         [P]Potion [E]Equip [M]Market [I]Info [Q]Quit
Choose action: W
Muamman_Duathall moved to (4, 6)

>>> MONSTERS' TURN <<<
Casper advanced to (3, 1)
Blinky advanced to (3, 4)
Casper advanced to (3, 7)

==================================================
ROUND 4
==================================================


N - N - N    N - N - N    I - I - I    N - N - N    N - N - N    I - I - I    N - N - N    N - N - N
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
N - N - N    N - N - N    I - I - I    N - N - N    N - N - N    I - I - I    N - N - N    N - N - N

C - C - C    P - P - P    I - I - I    P - P - P    P - P - P    I - I - I    K - K - K    P - P - P
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
C - C - C    P - P - P    I - I - I    P - P - P    P - P - P    I - I - I    K - K - K    P - P - P

P - P - P    P - P - P    I - I - I    C - C - C    K - K - K    I - I - I    P - P - P    C - C - C
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
P - P - P    P - P - P    I - I - I    C - C - C    K - K - K    I - I - I    P - P - P    C - C - C

K - K - K    P - P - P    I - I - I    C - C - C    P - P - P    I - I - I    B - B - B    P - P - P
|       |    |M1     |    | X X X |    |       |    |M2     |    | X X X |    |       |    |M3     |
K - K - K    P - P - P    I - I - I    C - C - C    P - P - P    I - I - I    B - B - B    P - P - P

K - K - K    P - P - P    I - I - I    P - P - P    B - B - B    I - I - I    P - P - P    K - K - K
|H1     |    |       |    | X X X |    |H2     |    |       |    | X X X |    |H3     |    |       |
K - K - K    P - P - P    I - I - I    P - P - P    B - B - B    I - I - I    P - P - P    K - K - K

K - K - K    P - P - P    I - I - I    C - C - C    P - P - P    I - I - I    B - B - B    B - B - B
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
K - K - K    P - P - P    I - I - I    C - C - C    P - P - P    I - I - I    B - B - B    B - B - B

C - C - C    B - B - B    I - I - I    B - B - B    C - C - C    I - I - I    B - B - B    K - K - K
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
C - C - C    B - B - B    I - I - I    B - B - B    C - C - C    I - I - I    B - B - B    K - K - K

N - N - N    N - N - N    I - I - I    N - N - N    N - N - N    I - I - I    N - N - N    N - N - N
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
N - N - N    N - N - N    I - I - I    N - N - N    N - N - N    I - I - I    N - N - N    N - N - N

Legend: N=Nexus I=Inaccessible P=Plain B=Bush C=Cave K=Koulou | H1/H2/H3=Heroes M#=Monsters

>>> HEROES' TURN <<<

--- Gaerdal_Ironhand's Turn (Lane 1) ---
Position: (4, 0)
HP: 100 | MP: 100

Actions: [W]Move Up [S]Down [A]Left [D]Right
         [T]Teleport [R]Recall [K]Attack [C]Spell
         [P]Potion [E]Equip [M]Market [I]Info [Q]Quit
Choose action: D
Gaerdal_Ironhand moved to (4, 1)

--- Sehanine_Monnbow's Turn (Lane 2) ---
Position: (4, 3)
HP: 100 | MP: 600

Actions: [W]Move Up [S]Down [A]Left [D]Right
         [T]Teleport [R]Recall [K]Attack [C]Spell
         [P]Potion [E]Equip [M]Market [I]Info [Q]Quit
Choose action: D
Sehanine_Monnbow moved to (4, 4)

--- Muamman_Duathall's Turn (Lane 3) ---
Position: (4, 6)
HP: 100 | MP: 300

Actions: [W]Move Up [S]Down [A]Left [D]Right
         [T]Teleport [R]Recall [K]Attack [C]Spell
         [P]Potion [E]Equip [M]Market [I]Info [Q]Quit
Choose action: D
Muamman_Duathall moved to (4, 7)

>>> MONSTERS' TURN <<<
Casper attacks Gaerdal_Ironhand!
Gaerdal_Ironhand dodged Casper's attack!
Blinky attacks Sehanine_Monnbow!
Blinky attacked Sehanine_Monnbow for 45 damage!
Casper attacks Muamman_Duathall!
Casper attacked Muamman_Duathall for 10 damage!

==================================================
ROUND 5
==================================================


N - N - N    N - N - N    I - I - I    N - N - N    N - N - N    I - I - I    N - N - N    N - N - N
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
N - N - N    N - N - N    I - I - I    N - N - N    N - N - N    I - I - I    N - N - N    N - N - N

C - C - C    P - P - P    I - I - I    P - P - P    P - P - P    I - I - I    K - K - K    P - P - P
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
C - C - C    P - P - P    I - I - I    P - P - P    P - P - P    I - I - I    K - K - K    P - P - P

P - P - P    P - P - P    I - I - I    C - C - C    K - K - K    I - I - I    P - P - P    C - C - C
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
P - P - P    P - P - P    I - I - I    C - C - C    K - K - K    I - I - I    P - P - P    C - C - C

K - K - K    P - P - P    I - I - I    C - C - C    P - P - P    I - I - I    B - B - B    P - P - P
|       |    |M1     |    | X X X |    |       |    |M2     |    | X X X |    |       |    |M3     |
K - K - K    P - P - P    I - I - I    C - C - C    P - P - P    I - I - I    B - B - B    P - P - P

K - K - K    P - P - P    I - I - I    P - P - P    B - B - B    I - I - I    P - P - P    K - K - K
|       |    |H1     |    | X X X |    |       |    |H2     |    | X X X |    |       |    |H3     |
K - K - K    P - P - P    I - I - I    P - P - P    B - B - B    I - I - I    P - P - P    K - K - K

K - K - K    P - P - P    I - I - I    C - C - C    P - P - P    I - I - I    B - B - B    B - B - B
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
K - K - K    P - P - P    I - I - I    C - C - C    P - P - P    I - I - I    B - B - B    B - B - B

C - C - C    B - B - B    I - I - I    B - B - B    C - C - C    I - I - I    B - B - B    K - K - K
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
C - C - C    B - B - B    I - I - I    B - B - B    C - C - C    I - I - I    B - B - B    K - K - K

N - N - N    N - N - N    I - I - I    N - N - N    N - N - N    I - I - I    N - N - N    N - N - N
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
N - N - N    N - N - N    I - I - I    N - N - N    N - N - N    I - I - I    N - N - N    N - N - N

Legend: N=Nexus I=Inaccessible P=Plain B=Bush C=Cave K=Koulou | H1/H2/H3=Heroes M#=Monsters

>>> HEROES' TURN <<<

--- Gaerdal_Ironhand's Turn (Lane 1) ---
Position: (4, 1)
HP: 100 | MP: 100

Actions: [W]Move Up [S]Down [A]Left [D]Right
         [T]Teleport [R]Recall [K]Attack [C]Spell
         [P]Potion [E]Equip [M]Market [I]Info [Q]Quit
Choose action: W
Cannot move there!

--- Sehanine_Monnbow's Turn (Lane 2) ---
Position: (4, 4)
HP: 60 | MP: 600

Actions: [W]Move Up [S]Down [A]Left [D]Right
         [T]Teleport [R]Recall [K]Attack [C]Spell
         [P]Potion [E]Equip [M]Market [I]Info [Q]Quit
Choose action: A
Sehanine_Monnbow moved to (4, 3)

--- Muamman_Duathall's Turn (Lane 3) ---
Position: (4, 7)
HP: 99 | MP: 300

Actions: [W]Move Up [S]Down [A]Left [D]Right
         [T]Teleport [R]Recall [K]Attack [C]Spell
         [P]Potion [E]Equip [M]Market [I]Info [Q]Quit
Choose action: A
Muamman_Duathall moved to (4, 6)

>>> MONSTERS' TURN <<<
Casper attacks Gaerdal_Ironhand!
Casper attacked Gaerdal_Ironhand for 10 damage!
Blinky attacks Sehanine_Monnbow!
Blinky attacked Sehanine_Monnbow for 45 damage!
Casper attacks Muamman_Duathall!
Casper attacked Muamman_Duathall for 10 damage!

==================================================
ROUND 6
==================================================


N - N - N    N - N - N    I - I - I    N - N - N    N - N - N    I - I - I    N - N - N    N - N - N
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
N - N - N    N - N - N    I - I - I    N - N - N    N - N - N    I - I - I    N - N - N    N - N - N

C - C - C    P - P - P    I - I - I    P - P - P    P - P - P    I - I - I    K - K - K    P - P - P
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
C - C - C    P - P - P    I - I - I    P - P - P    P - P - P    I - I - I    K - K - K    P - P - P

P - P - P    P - P - P    I - I - I    C - C - C    K - K - K    I - I - I    P - P - P    C - C - C
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
P - P - P    P - P - P    I - I - I    C - C - C    K - K - K    I - I - I    P - P - P    C - C - C

K - K - K    P - P - P    I - I - I    C - C - C    P - P - P    I - I - I    B - B - B    P - P - P
|       |    |M1     |    | X X X |    |       |    |M2     |    | X X X |    |       |    |M3     |
K - K - K    P - P - P    I - I - I    C - C - C    P - P - P    I - I - I    B - B - B    P - P - P

K - K - K    P - P - P    I - I - I    P - P - P    B - B - B    I - I - I    P - P - P    K - K - K
|       |    |H1     |    | X X X |    |H2     |    |       |    | X X X |    |H3     |    |       |
K - K - K    P - P - P    I - I - I    P - P - P    B - B - B    I - I - I    P - P - P    K - K - K

K - K - K    P - P - P    I - I - I    C - C - C    P - P - P    I - I - I    B - B - B    B - B - B
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
K - K - K    P - P - P    I - I - I    C - C - C    P - P - P    I - I - I    B - B - B    B - B - B

C - C - C    B - B - B    I - I - I    B - B - B    C - C - C    I - I - I    B - B - B    K - K - K
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
C - C - C    B - B - B    I - I - I    B - B - B    C - C - C    I - I - I    B - B - B    K - K - K

N - N - N    N - N - N    I - I - I    N - N - N    N - N - N    I - I - I    N - N - N    N - N - N
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
N - N - N    N - N - N    I - I - I    N - N - N    N - N - N    I - I - I    N - N - N    N - N - N

Legend: N=Nexus I=Inaccessible P=Plain B=Bush C=Cave K=Koulou | H1/H2/H3=Heroes M#=Monsters

>>> HEROES' TURN <<<

--- Gaerdal_Ironhand's Turn (Lane 1) ---
Position: (4, 1)
HP: 99 | MP: 100

Actions: [W]Move Up [S]Down [A]Left [D]Right
         [T]Teleport [R]Recall [K]Attack [C]Spell
         [P]Potion [E]Equip [M]Market [I]Info [Q]Quit
Choose action: T

Select target hero to teleport near:
2. Sehanine_Monnbow at (4, 3) Lane 2
3. Muamman_Duathall at (4, 6) Lane 3
Select (0 to cancel): 2
Gaerdal_Ironhand teleported to (3, 3)!

--- Sehanine_Monnbow's Turn (Lane 2) ---
Position: (4, 3)
HP: 17 | MP: 600

Actions: [W]Move Up [S]Down [A]Left [D]Right
         [T]Teleport [R]Recall [K]Attack [C]Spell
         [P]Potion [E]Equip [M]Market [I]Info [Q]Quit
Choose action: T

Select target hero to teleport near:
1. Gaerdal_Ironhand at (3, 3) Lane 1
3. Muamman_Duathall at (4, 6) Lane 3
Select (0 to cancel): 3
Sehanine_Monnbow teleported to (3, 6)!

--- Muamman_Duathall's Turn (Lane 3) ---
Position: (4, 6)
HP: 97 | MP: 300

Actions: [W]Move Up [S]Down [A]Left [D]Right
         [T]Teleport [R]Recall [K]Attack [C]Spell
         [P]Potion [E]Equip [M]Market [I]Info [Q]Quit
Choose action: T

Select target hero to teleport near:
1. Gaerdal_Ironhand at (3, 3) Lane 1
2. Sehanine_Monnbow at (3, 6) Lane 2
Select (0 to cancel): 2
Muamman_Duathall teleported to (2, 6)!

>>> MONSTERS' TURN <<<
Casper advanced to (4, 1)
Blinky attacks Gaerdal_Ironhand!
Blinky attacked Gaerdal_Ironhand for 45 damage!
Casper attacks Sehanine_Monnbow!
Casper attacked Sehanine_Monnbow for 10 damage!

==================================================
ROUND 7
==================================================


N - N - N    N - N - N    I - I - I    N - N - N    N - N - N    I - I - I    N - N - N    N - N - N
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
N - N - N    N - N - N    I - I - I    N - N - N    N - N - N    I - I - I    N - N - N    N - N - N

C - C - C    P - P - P    I - I - I    P - P - P    P - P - P    I - I - I    K - K - K    P - P - P
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
C - C - C    P - P - P    I - I - I    P - P - P    P - P - P    I - I - I    K - K - K    P - P - P

P - P - P    P - P - P    I - I - I    C - C - C    K - K - K    I - I - I    P - P - P    C - C - C
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |H3     |    |       |
P - P - P    P - P - P    I - I - I    C - C - C    K - K - K    I - I - I    P - P - P    C - C - C

K - K - K    P - P - P    I - I - I    C - C - C    P - P - P    I - I - I    B - B - B    P - P - P
|       |    |       |    | X X X |    |H1     |    |M2     |    | X X X |    |H2     |    |M3     |
K - K - K    P - P - P    I - I - I    C - C - C    P - P - P    I - I - I    B - B - B    P - P - P

K - K - K    P - P - P    I - I - I    P - P - P    B - B - B    I - I - I    P - P - P    K - K - K
|       |    |M1     |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
K - K - K    P - P - P    I - I - I    P - P - P    B - B - B    I - I - I    P - P - P    K - K - K

K - K - K    P - P - P    I - I - I    C - C - C    P - P - P    I - I - I    B - B - B    B - B - B
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
K - K - K    P - P - P    I - I - I    C - C - C    P - P - P    I - I - I    B - B - B    B - B - B

C - C - C    B - B - B    I - I - I    B - B - B    C - C - C    I - I - I    B - B - B    K - K - K
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
C - C - C    B - B - B    I - I - I    B - B - B    C - C - C    I - I - I    B - B - B    K - K - K

N - N - N    N - N - N    I - I - I    N - N - N    N - N - N    I - I - I    N - N - N    N - N - N
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
N - N - N    N - N - N    I - I - I    N - N - N    N - N - N    I - I - I    N - N - N    N - N - N

Legend: N=Nexus I=Inaccessible P=Plain B=Bush C=Cave K=Koulou | H1/H2/H3=Heroes M#=Monsters

>>> HEROES' TURN <<<

--- Gaerdal_Ironhand's Turn (Lane 1) ---
Position: (3, 3)
HP: 59 | MP: 100

Actions: [W]Move Up [S]Down [A]Left [D]Right
         [T]Teleport [R]Recall [K]Attack [C]Spell
         [P]Potion [E]Equip [M]Market [I]Info [Q]Quit
Choose action: W
Gaerdal_Ironhand moved to (2, 3)

--- Sehanine_Monnbow's Turn (Lane 2) ---
Position: (3, 6)
HP: 7 | MP: 600

Actions: [W]Move Up [S]Down [A]Left [D]Right
         [T]Teleport [R]Recall [K]Attack [C]Spell
         [P]Potion [E]Equip [M]Market [I]Info [Q]Quit
Choose action: W
Cannot move there!

--- Muamman_Duathall's Turn (Lane 3) ---
Position: (2, 6)
HP: 100 | MP: 300

Actions: [W]Move Up [S]Down [A]Left [D]Right
         [T]Teleport [R]Recall [K]Attack [C]Spell
         [P]Potion [E]Equip [M]Market [I]Info [Q]Quit
Choose action: W
Muamman_Duathall moved to (1, 6)

>>> MONSTERS' TURN <<<
Casper advanced to (5, 1)
Blinky attacks Gaerdal_Ironhand!
Blinky attacked Gaerdal_Ironhand for 45 damage!
Casper attacks Sehanine_Monnbow!
Sehanine_Monnbow has fainted!
Casper attacked Sehanine_Monnbow for 10 damage!
Sehanine_Monnbow has fainted!
Sehanine_Monnbow has been defeated!
Sehanine_Monnbow has been revived!
Sehanine_Monnbow respawned at Nexus!

==================================================
ROUND 8
==================================================


N - N - N    N - N - N    I - I - I    N - N - N    N - N - N    I - I - I    N - N - N    N - N - N
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
N - N - N    N - N - N    I - I - I    N - N - N    N - N - N    I - I - I    N - N - N    N - N - N

C - C - C    P - P - P    I - I - I    P - P - P    P - P - P    I - I - I    K - K - K    P - P - P
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |H3     |    |       |
C - C - C    P - P - P    I - I - I    P - P - P    P - P - P    I - I - I    K - K - K    P - P - P

P - P - P    P - P - P    I - I - I    C - C - C    K - K - K    I - I - I    P - P - P    C - C - C
|       |    |       |    | X X X |    |H1     |    |       |    | X X X |    |       |    |       |
P - P - P    P - P - P    I - I - I    C - C - C    K - K - K    I - I - I    P - P - P    C - C - C

K - K - K    P - P - P    I - I - I    C - C - C    P - P - P    I - I - I    B - B - B    P - P - P
|       |    |       |    | X X X |    |       |    |M2     |    | X X X |    |       |    |M3     |
K - K - K    P - P - P    I - I - I    C - C - C    P - P - P    I - I - I    B - B - B    P - P - P

K - K - K    P - P - P    I - I - I    P - P - P    B - B - B    I - I - I    P - P - P    K - K - K
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
K - K - K    P - P - P    I - I - I    P - P - P    B - B - B    I - I - I    P - P - P    K - K - K

K - K - K    P - P - P    I - I - I    C - C - C    P - P - P    I - I - I    B - B - B    B - B - B
|       |    |M1     |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
K - K - K    P - P - P    I - I - I    C - C - C    P - P - P    I - I - I    B - B - B    B - B - B

C - C - C    B - B - B    I - I - I    B - B - B    C - C - C    I - I - I    B - B - B    K - K - K
|       |    |       |    | X X X |    |       |    |       |    | X X X |    |       |    |       |
C - C - C    B - B - B    I - I - I    B - B - B    C - C - C    I - I - I    B - B - B    K - K - K

N - N - N    N - N - N    I - I - I    N - N - N    N - N - N    I - I - I    N - N - N    N - N - N
|       |    |       |    | X X X |    |H2     |    |       |    | X X X |    |       |    |       |
N - N - N    N - N - N    I - I - I    N - N - N    N - N - N    I - I - I    N - N - N    N - N - N

Legend: N=Nexus I=Inaccessible P=Plain B=Bush C=Cave K=Koulou | H1/H2/H3=Heroes M#=Monsters

>>> HEROES' TURN <<<

--- Gaerdal_Ironhand's Turn (Lane 1) ---
Position: (2, 3)
HP: 15 | MP: 100

Actions: [W]Move Up [S]Down [A]Left [D]Right
         [T]Teleport [R]Recall [K]Attack [C]Spell
         [P]Potion [E]Equip [M]Market [I]Info [Q]Quit
Choose action: W
Gaerdal_Ironhand moved to (1, 3)

--- Sehanine_Monnbow's Turn (Lane 2) ---
Position: (7, 3)
HP: 50 | MP: 300

Actions: [W]Move Up [S]Down [A]Left [D]Right
         [T]Teleport [R]Recall [K]Attack [C]Spell
         [P]Potion [E]Equip [M]Market [I]Info [Q]Quit
Choose action: W
Sehanine_Monnbow moved to (6, 3)

--- Muamman_Duathall's Turn (Lane 3) ---
Position: (1, 6)
HP: 100 | MP: 300

Actions: [W]Move Up [S]Down [A]Left [D]Right
         [T]Teleport [R]Recall [K]Attack [C]Spell
         [P]Potion [E]Equip [M]Market [I]Info [Q]Quit
Choose action: W
Muamman_Duathall moved to (0, 6)

VICTORY! A hero reached the monster Nexus!

==================================================
GAME OVER
==================================================
Final Stats:
Gaerdal_Ironhand - Level 1 | Gold: 1354
Sehanine_Monnbow - Level 1 | Gold: 2500
Muamman_Duathall - Level 1 | Gold: 2546



========================================
        RPG GAME COLLECTION
========================================

Select a game to play:

1. Monsters and Heroes
   Classic RPG adventure with heroes,
   monsters, markets, and exploration

2. Legends of Valor
   MOBA-style strategic battle arena
   with 3 lanes and team combat

0. Exit
========================================
Enter your choice: 0

Thank you for playing! Goodbye!
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
