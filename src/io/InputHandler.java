package io;

import java.util.Scanner;

/**
 * Handles user input with validation and error handling.
 * Provides methods for reading various types of input with prompts.
 */
public class InputHandler {
    private Scanner scanner;
    private OutputHandler output;

    public InputHandler(Scanner scanner, OutputHandler output) {
        this.scanner = scanner;
        this.output = output;
    }

    public String readLine() {
        return scanner.nextLine();
    }

    public String readLine(String prompt) {
        output.displayInline(prompt);
        return scanner.nextLine();
    }

    public int readInt(int min, int max) {
        while (true) {
            try {
                String input = scanner.nextLine().trim().toLowerCase();

                // Check for quit command
                if (input.equals("q")) {
                    return -999;
                }

                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
                output.display("Enter number between " + min + " and " + max + " (or 'q' to quit): ");
            } catch (NumberFormatException e) {
                output.display("Invalid input. Enter a number (or 'q' to quit): ");
            }
        }
    }


    public int readInt(String prompt, int min, int max) {
        output.displayInline(prompt);
        return readInt(min, max);
    }

    public boolean readYesNo(String prompt) {
        output.displayInline(prompt + " (y/n): ");
        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("yes")) {
                return true;
            } else if (input.equals("n") || input.equals("no")) {
                return false;
            }
            output.display("Please enter 'y' or 'n': ");
        }
    }

    public int readMenuChoice(int options) {
        return readInt(0, options);
    }

    public int readMenuChoice(String prompt, int options) {
        return readInt(prompt, 0, options);
    }

    public void waitForEnter(String message) {
        output.display(message);
        scanner.nextLine();
    }

    public void close() {
        scanner.close();
    }
}
