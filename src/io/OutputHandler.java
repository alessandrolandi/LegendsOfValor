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
        String border = "-".repeat(length);

        display(Color.BOLD_RED + "+" + border + "+"+ Color.RESET);
        display(Color.BOLD_RED + "|  " + title + "  |" + Color.RESET);
        display(Color.BOLD_RED + "+" + border + "+" + Color.RESET);
    }

    public void displaySeparator(String title) {
        display("\n" + Color.BOLD_RED + "--- " + title + " ---" + Color.RESET);
    }

    public void displayError(String message) {
        display(Color.BOLD_RED + "ERROR: " + message + Color.RESET);
    }


    public void displaySuccess(String message) {
        display(Color.BOLD_GREEN + "✓ " + message + Color.RESET);
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
        display(Color.BOLD_YELLOW + "\n=== BATTLE STATUS ===" + Color.RESET);
        display(Color.BOLD_GREEN + "\nHEROES:" + Color.RESET);
        display(heroesInfo);
        display(Color.BOLD_RED + "\nMONSTERS:" + Color.RESET);
        display(monstersInfo);
    }
}
