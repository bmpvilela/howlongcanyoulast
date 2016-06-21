package org.academiadecodigo.howlongcanyoulast;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenWriter;
import com.googlecode.lanterna.terminal.Terminal;

public final class Field {

    public static int width;
    public static int height;

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
     *
     */
    public static void init(String path) {

        map = FileTools.fileRead(path);

        // Create the GUI
        screen = TerminalFacade.createScreen();

        // Set field size
        Field.width = map[0].length();
        Field.height = map.length;
        screen.getTerminal().setCursorVisible(false); // Not Working
        screen.getTerminal().getTerminalSize().setColumns(width);
        screen.getTerminal().getTerminalSize().setRows(height);

        // Default screen writing options
        screenWriter = new ScreenWriter(screen);
        screenWriter.setBackgroundColor(Terminal.Color.RED);
        screenWriter.setForegroundColor(Terminal.Color.WHITE);

        screen.startScreen();


    }

    /**
     * Displays a group of cars in the screen
     *
     * @param
     */
    public static void draw() {
        screen.clear();

        drawMap(map);
        screenWriter.setBackgroundColor(Terminal.Color.RED);
        screen.refresh();
    }

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

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }
}