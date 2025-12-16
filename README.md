# CS611 - Assignment V

## Legends of Valor (and Legends: Monsters and Heroes)

---

- **Team Members:** Alessandro Landi / Renwei Li / Nandini Nandan Narvekar
- **Email:** al6723@bu.edu / lir14@bu.edu / nnandini@bu.edu
- **Student ID:** U63309114 / U08183479 / U25345416

---

### Overview

This project implements **Legends of Valor**, a MOBA-style (Multiplayer Online Battle Arena) strategy game played on a console grid. It is built upon the framework of the previous "Legends: Monsters and Heroes" assignment.

The application now features a **Game Menu** that allows the player to choose between:

1.  **Monsters and Heroes:** The classic dungeon crawler exploration game.
2.  **Legends of Valor:** The new strategic game featuring a 3-lane map, Nexus bases, and wave-based monster attacks.

**Key Features of Legends of Valor:**

- **3-Lane Map:** An 8x8 grid divided into Top, Mid, and Bot lanes, separated by inaccessible walls.
- **Strategic Terrain:** Special tiles (Bush, Cave, Koulou) providing stat buffs to heroes.
- **Objective-Based Gameplay:** The goal is to march a hero into the enemy's Nexus while preventing monsters from reaching the hero's Nexus.
- **Advanced Movement:** Includes Teleporting between lanes and Recalling to base.
- **Monster AI:** Monsters automatically spawn and march toward the hero base.

### Files

#### Game Control & Entry

- **App.java** — Entry point. Launches the GameMenu.
- **GameMenu.java** — Handles the main menu selection, allowing the user to switch between "Monsters and Heroes" and "Legends of Valor".
- **RPGGame.java** — Abstract base class defining the structure (start, game loop) for both game modes.

#### Concrete Game Implementations

- **LegendsOfValorGame.java** — The core controller for the Legends of Valor game. Manages:
  - Round-based logic (Hero Turn -> Monster Turn -> Regeneration).
  - Hero spawning and lane assignment.
  - Periodic monster spawning (waves).
  - Victory and Defeat condition checks.
- **HeroesAndMonstersGame.java** — The controller for the previous exploration-based game.

#### World & Tile System

- **board/Board.java** — Abstract base class for grid management.
- **board/LegendsOfValorBoard.java** — Generates the specific 8x8 MOBA map. Handles the placement of lanes, walls, and random buff tiles.
- **board/Tile.java** — Abstract base class. Now acts as a container for `List<Hero>` and `List<Monster>`, allowing multiple entities on one tile.
- **board/NexusTile.java** — Represents the base. Allows buying/selling (Market) for heroes. Target for monsters.
- **board/BushTile.java** — Increases Hero Dexterity by 10%.
- **board/CaveTile.java** — Increases Hero Agility by 10%.
- **board/KoulouTile.java** — Increases Hero Strength by 10%.
- **board/PlainTile.java** — Represents a standard accessible tile.
- **board/InaccessibleTile.java** — Represents walls/barriers between lanes.

#### Character System (Entities)

- **entities/Creature.java** — Abstract base class for all combatants.
- **entities/heroes/Hero.java** — Updated to support Legends of Valor features:
  - teleport() logic.
  - recall() logic.
  - Terrain bonus application (applyStrengthBonus, etc.).
  - Lane tracking.
- **entities/monsters/Monster.java** — Updated to support Legends of Valor features:
  - moveForward() AI (marches south automatically).
  - Lane tracking.
- **entities/heroes/Warrior.java, Paladin.java, Sorcerer.java** — Concrete hero classes.
- **entities/monsters/Dragon.java, Exoskeleton.java, Spirit.java** — Concrete monster classes.

#### Item & Market System

- **items/Item.java**, **Weapon.java**, **Armor.java**, **Potion.java**, **Spell.java** — Represents the inventory items.
- **market/Market.java** — Handles the logic for buying and selling items. In Legends of Valor, this is accessible only at the Nexus.
- **factories/HeroFactory.java**, **MonsterFactory.java**, **ItemFactory.java** — Data-driven factories that parse text files to create objects.

#### Utility & I/O

- **io/InputHandler.java** — robust input validation for integers, menus, and strings.
- **io/OutputHandler.java** — Formatted console display.
- **factories/DataFileParser.java** — Utility for reading configuration files.

### Game Rules: Legends of Valor

1.  **Setup:** The player selects 3 heroes. Each hero is assigned to a specific lane (Top, Mid, or Bot).
2.  **The Map:**
    - **Row 7 (Bottom):** Heroes' Nexus. Heroes spawn here. Monsters try to reach here.
    - **Row 0 (Top):** Monsters' Nexus. Monsters spawn here. Heroes try to reach here.
    - **Lanes:** Separated by "I" (Inaccessible) walls.
3.  **Terrain Bonuses:**
    - **B (Bush):** +10% Dexterity.
    - **C (Cave):** +10% Agility.
    - **K (Koulou):** +10% Strength.
4.  **Combat:** Combat can occur if a target is in the same cell or an adjacent cell (Range 1).
5.  **Monster AI:** Monsters appear in waves (every 8 rounds). They will attack if a hero is in range; otherwise, they move one step forward (South).
6.  **Win Condition:** A Hero enters the Monsters' Nexus (Row 0).
7.  **Lose Condition:** A Monster enters the Heroes' Nexus (Row 7).

### Controls (Legends of Valor)

Since this is a strategy game, the controls are expanded compared to the previous assignment:

- **Movement:**
  - `W` / `A` / `S` / `D`: Move Up / Left / Down / Right.
- **Special Moves:**
  - `T`: **Teleport**. Move to an adjacent space of a hero in a _different_ lane.
  - `R`: **Recall**. Instantly return to your Nexus spawn point.
- **Combat:**
  - `K`: **Attack**. Strike a monster in range with your weapon.
  - `C`: **Cast Spell**. Use a spell from inventory on a monster in range.
  - `P`: **Potion**. Use a potion from inventory.
  - `E`: **Equip**. Change Armor/Weapon (Mainly via Market/Status).
- **System:**
  - `M`: **Market**. Enter the shop (Only available when standing on a Nexus tile).
  - `I`: **Info**. Display current hero stats.
  - `Q`: **Quit**. Exit the game.

### How to Compile & Run

1.  **Navigate to the source directory:**

    ```bash
    cd src
    ```

2.  **Compile all files:**

    ```bash
    javac -d bin src/*.java src/board/*.java src/combat/*.java src/entities/*.java src/entities/heroes/*.java src/entities/monsters/*.java src/factories/*.java src/io/*.java src/game/*.java src/items/*.java src/market/*.java
    ```

3.  **Run the Game:**
    Navigate to bin directory through cd bin
    ```bash
    
    java App
    ```

4.  **Select Game Mode:**
    The menu will appear. Type `2` to play **Legends of Valor**.


### Game Controls
```
Controls:
  W - Move up    |  A - Move left
  S - Move down  |  D - Move right
  T - Teleport   |  R - Recall to Nexus
  A - Attack     |  C - Cast Spell
  P - Use Potion |  E - Equip Item
  M - Market (at Nexus)
  I - Info       |  Q - Quit
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
