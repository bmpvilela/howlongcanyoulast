package org.academiadecodigo.howlongcanyoulast.utilities;

import com.googlecode.lanterna.terminal.Terminal;

/**
 * Created by brunovilela on 19/06/16.
 */
public enum EnumColors {

    BLACK (0,Terminal.Color.BLACK),
    BLUE (1,Terminal.Color.BLUE),
    RED (2, Terminal.Color.RED),
    MAGENTA (3, Terminal.Color.MAGENTA),
    GREEN (4, Terminal.Color.GREEN),
    CYAN (5 ,Terminal.Color.CYAN),
    YELLOW (6, Terminal.Color.YELLOW),
    WHITE (7, Terminal.Color.WHITE);

    private int numColor;
    private Terminal.Color color;

    EnumColors (int numColor,Terminal.Color color){
        this.numColor = numColor;
        this.color = color;
    }

    public static Terminal.Color getColorById(int id) {

        //BLACK.ordinal();

        for(EnumColors e : values()) {
            if (e.numColor == id) return e.color;
        }
        return null;
    }
}
