import java.util.Scanner;
import io.*;

/**
 * Abstract base class for all RPG games.
 * Provides core functionality for game initialization, running the game loop,
 * and handling user input/output.
 */
public abstract class RPGGame {
    protected Scanner scanner;
    protected InputHandler input;
    protected OutputHandler output;
    protected boolean isRunning;

    /**
     * Creates a new RPG game instance.
     * Initializes the input/output handlers and sets the game to running state.
     */
    public RPGGame() {
        this.scanner = new Scanner(System.in);
        this.output = new OutputHandler();
        this.input = new InputHandler(scanner, output);
        this.isRunning = true;
    }

    /**
     * Starts the game by displaying welcome message, initializing, and running the main loop.
     */
    public abstract void start();

    /**
     * Displays the welcome message and game instructions to the player.
     */
    protected abstract void displayWelcome();

    /**
     * Initializes the game by setting up the world, characters, and other game elements.
     */
    protected abstract void initializeGame();

    /**
     * Main game loop that processes player commands and updates game state.
     */
    protected abstract void gameLoop();

    /**
     * Processes a single command from the player.
     * @param command The command character entered by the player
     */
    protected abstract void processCommand(char command);

    /**
     * Checks if the game is currently running.
     * @return true if the game is running, false otherwise
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Stops the game by setting the running state to false.
     */
    protected void stopGame() {
        isRunning = false;
    }
}
