package org.academiadecodigo.howlongcanyoulast;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenWriter;
import com.googlecode.lanterna.terminal.Terminal;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Static class that defines the game screen and draws its components.
 * It's also responsible for display the information about the time and scores of the game.
 */
public final class Field {

    // Screen size
    private static int width;
    private static int height;

    // Used to store map file
    private static String[] map;

    // Used to write to screen
    private static Screen screen;

    // Screen wrapper that preserves default options
    private static ScreenWriter screenWriter;

    //This class is not supposed to be instantiated
    private Field() {
    }

    /**
     * Initializes the Screen
     *
     * @param width  screen width
     * @param height screen height
     */
    public static void init(int width, int height) {

        // Create the GUI
        screen = TerminalFacade.createScreen();

        // Set field size
        Field.width = width;
        Field.height = height;
        screen.getTerminal().setCursorVisible(false); // Not Working
        screen.getTerminal().getTerminalSize().setColumns(width);
        screen.getTerminal().getTerminalSize().setRows(height);

        // Default screen writing options
        screenWriter = new ScreenWriter(screen);
        screenWriter.setBackgroundColor(Terminal.Color.RED);
        screenWriter.setForegroundColor(Terminal.Color.WHITE);

        screen.startScreen();

        map = FileTools.fileRead("map.txt");
    }

    /**
     * Displays the game objects and all information about the current game
     *
     * @param gameTime Class that handles with every time registries of the game
     * @param scores Class that defines the position of each score
     */
    public static void draw(GameTime gameTime, Scores scores) {
        screen.clear();

        drawMap(map);
        drawTime(gameTime.getColPos(), gameTime.getRowPos(), gameTime.getGameTime(), gameTime);
        drawScores(gameTime, scores);

        screenWriter.setBackgroundColor(Terminal.Color.RED);
        screen.refresh();
    }

    /**
     * Position and drawing the score information
     *
     * @param gameTime Times for each player
     * @param scores Position of each score
     */
    public static void drawScores(GameTime gameTime, Scores scores) {
        HashMap<String, Integer> playersTimes = gameTime.getPlayerFlagTime();
//TODO Change to be more automatic
        for (int i = 0; i < scores.getScores().length; i++) {
                score(scores.getScores()[i][0], scores.getScores()[i][1],
                        "Player" + (i + 1) + ": " + playersTimes.get("Player" + (i + 1)));
        }
    }

    /**
     * Draw the score display in a given position
     *
     * @param colPos Column position
     * @param rowPos Row position
     * @param playerInfo Player name + time flag
     */
    private static void score(int colPos, int rowPos, String playerInfo) {
        screenWriter.setBackgroundColor(EnumColors.getColorById(7));
        screenWriter.setForegroundColor(EnumColors.getColorById(0));

        screenWriter.drawString(colPos, rowPos, playerInfo);
    }

    /**
     * Draw a map on the screen and sets a color that represents the boundaries
     * and the walls
     *
     * @param map Array of characters representing the map
     */
    public static void drawMap(String[] map){
        int row = 0;
        for (String value: map) {
            for (int col = 0; col < value.length(); col++) {
                if (value.charAt(col) != '0') {
                    screenWriter.setBackgroundColor(EnumColors.getColorById(Character.getNumericValue(value.charAt(col))));
                    screenWriter.setForegroundColor(EnumColors.getColorById(Character.getNumericValue(value.charAt(col))));

                    screenWriter.drawString(col, row, "\u2588"); // █ BLOCK CHAR
                }
            }
            row++;
        }
    }

    /**
     * Draw the text on the center of the screen
     */
    public static void simpleDraw(String[] text) {
        screen.clear();

        screenWriter.setBackgroundColor(EnumColors.getColorById(0));
        screenWriter.setForegroundColor(EnumColors.getColorById(7));

        // Draw all string of the array
        for (int i = 0; i < text.length; i++) {
            screenWriter.drawString((width / 2) - GameTextType.getText(GameTextType.WAITING)[0].length() / 2, (height / 2 - (text.length / 2)) + i, text[i]);
        }

        screenWriter.setBackgroundColor(Terminal.Color.RED);
        screen.refresh();
    }

    /**
     * Draw the time of the game
     *
     * @param colPos Column position
     * @param rowPos Row position
     * @param elapsedTime Time passed since the beginning of the game
     */
    private static void drawTime(int colPos, int rowPos, String elapsedTime, GameTime gameTime) {
        int foregroundColor = 0;
        int backgroundColor = 7;

        if (gameTime.isLast10Seconds()) {
            foregroundColor = 7;
            backgroundColor = 2;
        }

        screenWriter.setBackgroundColor(EnumColors.getColorById(backgroundColor));
        screenWriter.setForegroundColor(EnumColors.getColorById(foregroundColor));

        screenWriter.drawString(colPos, rowPos, elapsedTime);
    }

    /**
     * Animate an array of strings on the screen
     *
     * @param text Text to animate
     * @param stopAnimationAt Where the text stops
     */
    public static void animation(String[] text, int stopAnimationAt) {
        // Start point of the text
        // Used to move text width
        int rowVelocity = width + 1;

        long lastTime = 0;

        while (rowVelocity >= stopAnimationAt) {
            long currentTime = System.nanoTime();

            if (currentTime > lastTime) {
                rowVelocity--;

                screen.clear();

                screenWriter.setBackgroundColor(EnumColors.getColorById(0));
                screenWriter.setForegroundColor(EnumColors.getColorById(7));

                // Draw all string of the array
                for (int i = 0; i < text.length; i++) {
                    screenWriter.drawString(rowVelocity, (height / 2 - (text.length / 2)) + i, text[i]);
                }

                screenWriter.setBackgroundColor(Terminal.Color.RED);
                screen.refresh();

                lastTime = currentTime + 10000000;
            }
        }
    }

    /**
     * Getter
     *
     * @return Screen width(Rows)
     */
    public static int getWidth() {
        return width;
    }

}