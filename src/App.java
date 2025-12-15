import game.GameMenu;

/**
 * Main application entry point for RPG Games.
 * Provides a menu to select between different games:
 * - Legends: Monsters and Heroes (RPG)
 * - Legends of Valor (MOBA-style game)
 */
public class App {
    public static void main(String[] args) {
        GameMenu menu = new GameMenu();
        menu.start();
    }
}
