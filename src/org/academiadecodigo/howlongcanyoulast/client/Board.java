package org.academiadecodigo.howlongcanyoulast.client;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenWriter;
import com.googlecode.lanterna.terminal.Terminal;
import org.academiadecodigo.howlongcanyoulast.Scores;
import org.academiadecodigo.howlongcanyoulast.game.GameTime;
import org.academiadecodigo.howlongcanyoulast.utilities.EnumColors;
import org.academiadecodigo.howlongcanyoulast.utilities.FileTools;

import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Static class that defines the game screen and draws its components.
 * It's also responsible for display the information about the time and scores of the game.
 */
public final class Board {

    public static int width;
    public static int height;

    private static Key key;

    // Used to store map file
    private static String[] map;

    // Used to write to screen
    private static Screen screen;

    // Screen wrapper that preserves default options
    private static ScreenWriter screenWriter;

    private static String[] allPlayersPositions;
    //Amauri: the position flag will have
    private static int[] flagPosition;

    private static GameTime gameTime;

    private static Scores scores;

    //This class is not supposed to be instantiated
    private Board() {
    }

    /**
     * Initializes the Screen
     * and draws the map
     *
     * @param mapFromServer Generated map
     */
    public static void init(String[] mapFromServer) {

        map = mapFromServer;

        // Create the GUI
        screen = TerminalFacade.createScreen();

        // Set field size
        width = map[0].length();
        height = map.length;

        gameTime = new GameTime(4); //TODO
        scores = new Scores(4); //TODO

        //TODO Amauri
        screen.getTerminal().setCursorVisible(false); // Not Working
        screen.getTerminal().getTerminalSize().setColumns(width);
        screen.getTerminal().getTerminalSize().setRows(height);

        // Default screen writing options
        screenWriter = new ScreenWriter(screen);
        screenWriter.setBackgroundColor(Terminal.Color.RED);
        screenWriter.setForegroundColor(Terminal.Color.WHITE);

        screen.startScreen();
        System.out.println(key);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    key = screen.readInput();
                }
            }
        }).start();


    }

    /**
     * Displays a group of cars in the screen
     */
    public static void draw() {
        screen.clear();

        drawMap(map);
        drawTime(gameTime.getColPos(), gameTime.getRowPos(), gameTime.getGameTime());
        drawScores();
        drawPlayers();
        //System.out.println(""+ flagPosition[0] +" "+ flagPosition[1]);
        drawFlag();

        screenWriter.setBackgroundColor(Terminal.Color.RED);
        screen.refresh();
    }

    //TODO Amauri
    private static void drawFlag(){
        screenWriter.setBackgroundColor(EnumColors.RED.getColor());
        screenWriter.setForegroundColor(EnumColors.GREEN.getColor());
        screenWriter.setForegroundColor(EnumColors.YELLOW.getColor());
        screenWriter.drawString(flagPosition[0], flagPosition[1], "\u2588");
    }

    private static void drawPlayers() {

        if (allPlayersPositions != null) {
            //System.out.println(Arrays.toString(allPlayersPositions));
            // TODO Andre player movimento, AKA desenhar

            for (int i = 0; i < allPlayersPositions.length; i += 3) {

                if (allPlayersPositions[i] == null) break;

                screenWriter.setBackgroundColor(EnumColors.RED.getColor());
                screenWriter.setForegroundColor(EnumColors.GREEN.getColor());
                screenWriter.drawString(Integer.parseInt(allPlayersPositions[i + 1]), Integer.parseInt(allPlayersPositions[i + 2]), "\u2588");

                try {

                    if (("/" + InetAddress.getLocalHost().getHostAddress()).equals(allPlayersPositions[i])) {
                        screenWriter.setForegroundColor(EnumColors.YELLOW.getColor());
                    }

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }

                screenWriter.drawString(Integer.parseInt(allPlayersPositions[i + 1]), Integer.parseInt(allPlayersPositions[i + 2]), "\u2588");
            }
        }
    }

    /**
     * Position and drawing the score information
     */
    public static void drawScores() {
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
     * @param colPos     Column position
     * @param rowPos     Row position
     * @param playerInfo Player name + time flag
     */
    private static void score(int colPos, int rowPos, String playerInfo) {
        screenWriter.setBackgroundColor(EnumColors.WHITE.getColor());
        screenWriter.setForegroundColor(EnumColors.BLACK.getColor());

        screenWriter.drawString(colPos, rowPos, playerInfo);
    }

    public static void drawMap(String[] map) {

        int row = 0;
        for (String value : map) {
            for (int col = 0; col < value.length(); col++) {
                if (value.charAt(col) != '0') {
                    screenWriter.setBackgroundColor(EnumColors.getColorById(Integer.parseInt(value.charAt(col) + "")));
                    screenWriter.setForegroundColor(EnumColors.getColorById(Integer.parseInt(value.charAt(col) + "")));

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

        screenWriter.setBackgroundColor(EnumColors.BLACK.getColor());
        screenWriter.setForegroundColor(EnumColors.WHITE.getColor());

        // Draw all string of the array
        for (int i = 0; i < text.length; i++) {
            screenWriter.drawString((width / 2) - text[0].length() / 2, (height / 2 - (text.length / 2)) + i, text[i]);
        }

        screenWriter.setBackgroundColor(Terminal.Color.RED);
        screen.refresh();
    }

    /**
     * Draw the time of the game
     *
     * @param colPos      Column position
     * @param rowPos      Row position
     * @param elapsedTime Time passed since the beginning of the game
     */
    private static void drawTime(int colPos, int rowPos, String elapsedTime) {

        Terminal.Color foregroundColor = EnumColors.BLACK.getColor();
        Terminal.Color backgroundColor = EnumColors.WHITE.getColor();

        if (gameTime.isLast10Seconds()) {
            foregroundColor = EnumColors.WHITE.getColor();
            backgroundColor = EnumColors.RED.getColor();
        }

        screenWriter.setBackgroundColor(backgroundColor);
        screenWriter.setForegroundColor(foregroundColor);

        screenWriter.drawString(colPos, rowPos, elapsedTime);
    }

    /**
     * Animate an array of strings on the screen
     *
     * @param text            Text to animate
     * @param stopAnimationAt Where the text stops
     */
    public static void animation(String[] text, int stopAnimationAt) {
        // Start point of the text
        // Used to move text width
        int rowVelocity = 100;

        long lastTime = 0;

        while (rowVelocity >= stopAnimationAt) {
            long currentTime = System.nanoTime();

            if (currentTime > lastTime) {
                rowVelocity--;

                screen.clear();

                screenWriter.setBackgroundColor(EnumColors.BLACK.getColor());
                screenWriter.setForegroundColor(EnumColors.WHITE.getColor());

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

    public static Key getKey() {
        return key;
    }

    public static void setAllPlayersPositions(String[] positions) {
        allPlayersPositions = positions;
    }

    public static void setFlagPosition(int[] position){flagPosition = position;}
}