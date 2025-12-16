package io;

/**
 * Handles output display to the console.
 * Provides formatted output methods for messages, menus, and game information.
 */
public class OutputHandler {
    public void display(String message) {
        System.out.println(message);
    }

    public void displayInline(String message) {
        System.out.print(message);
    }

    public void displayLine() {
        System.out.println();
    }

    public void displayHeader(String title) {
        int length = title.length() + 4;
        String border = "═".repeat(length);

        display("+" + border + "+");
        display("|  " + title + "  |");
        display("+" + border + "+");
    }

    public void displaySeparator(String title) {
        display("\n--- " + title + " ---");
    }

    public void displayError(String message) {
        display("ERROR: " + message);
    }


    public void displaySuccess(String message) {
        display("✓ " + message);
    }

    public void displayMenu(String title, String... options) {
        if (title != null && !title.isEmpty()) {
            display("\n" + title);
        }
        for (int i = 0; i < options.length; i++) {
            display((i + 1) + ". " + options[i]);
        }
    }

    public void displayBattleStatus(String heroesInfo, String monstersInfo) {
        display("\n=== BATTLE STATUS ===");
        display("\nHEROES:");
        display(heroesInfo);
        display("\nMONSTERS:");
        display(monstersInfo);
    }
}