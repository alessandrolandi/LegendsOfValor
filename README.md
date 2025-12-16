# CS611 - Assignment V

## Legends of Valor (and Legends: Monsters and Heroes)

---

- **Team Members:** Alessandro Landi / Renwei Li / [Teammate's Name]
- **Email:** al6723@bu.edu / lir14@bu.edu / [Teammate's Email]
- **Student ID:** U63309114 / U08183479 / [Teammate's ID]

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
    javac *.java board/*.java combat/*.java entities/*.java entities/heroes/*.java entities/monsters/*.java factories/*.java game/*.java io/*.java items/*.java market/*.java
    ```

3.  **Run the Game:**

    ```bash
    java App
    ```

4.  **Select Game Mode:**
    The menu will appear. Type `2` to play **Legends of Valor**.

### Sample Input & Output
