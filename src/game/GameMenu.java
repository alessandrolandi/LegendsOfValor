package game;

import io.*;
import java.util.Scanner;
import io.Color;

/**
 * Game menu for selecting between different game modes.
 * Handles menu display and game selection logic.
 */
public class GameMenu {
    private Scanner scanner;
    private OutputHandler output;
    private InputHandler input;

    public GameMenu() {
        this.scanner = new Scanner(System.in);
        this.output = new OutputHandler();
        this.input = new InputHandler(scanner, output);
    }

    /**
     * Starts the game menu loop.
     */
    public void start() {
        while (true) {
            displayMainMenu();
            int choice = input.readMenuChoice("Enter your choice: ", 2);

            if (choice == 0) {
                output.display("\nThank you for playing! Goodbye!");
                break;
            }

            switch (choice) {
                case 1:
                    playHeroesAndMonsters();
                    break;
                case 2:
                    playLegendsOfValor();
                    break;
                default:
                    output.display("Invalid choice. Please try again.");
            }

            output.display("\n");
        }

        scanner.close();
    }

    /**
     * Displays the main game selection menu.
     */
    private void displayMainMenu() {
        output.display(Color.BOLD_YELLOW + "\n========================================" + Color.RESET);
        output.display(Color.BOLD_YELLOW + "        RPG GAME COLLECTION" + Color.RESET);
        output.display(Color.BOLD_YELLOW + "========================================" + Color.RESET);
        output.display("\nSelect a game to play:");
        output.display("");
        output.display("1. Monsters and Heroes");
        output.display("   Classic RPG adventure with heroes,");
        output.display("   monsters, markets, and exploration");
        output.display("");
        output.display("2. Legends of Valor");
        output.display("   MOBA-style strategic battle arena");
        output.display("   with 3 lanes and team combat");
        output.display("");
        output.display("0. Exit");
        output.display("========================================");
    }

    /**
     * Starts the Legends: Monsters and Heroes game.
     */
    private void playHeroesAndMonsters() {
        HeroesAndMonstersGame game = new HeroesAndMonstersGame(scanner, output);
        game.start();
    }

    /**
     * Starts the Legends of Valor game.
     */
    private void playLegendsOfValor() {
        LegendsOfValorGame lovGame = new LegendsOfValorGame(scanner, output);
        lovGame.start();
    }
}
