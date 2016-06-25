package org.academiadecodigo.howlongcanyoulast.utilities;

import com.googlecode.lanterna.terminal.Terminal;

/**
 * Created by brunovilela on 19/06/16.
 */
public enum EnumColors {

    BLACK (Terminal.Color.BLACK),
    BLUE (Terminal.Color.BLUE),
    RED (Terminal.Color.RED),
    MAGENTA (Terminal.Color.MAGENTA),
    GREEN (Terminal.Color.GREEN),
    CYAN (Terminal.Color.CYAN),
    YELLOW (Terminal.Color.YELLOW),
    WHITE (Terminal.Color.WHITE);

    private Terminal.Color color;

    EnumColors (Terminal.Color color){
        this.color = color;
    }

    public Terminal.Color getColor() {
        return color;
    }

    public static Terminal.Color getColorById(int id) {
        return EnumColors.values()[id].getColor();
    }
}
