package org.academiadecodigo.howlongcanyoulast.utilities;

/**
 * Created by codecadet on 21/06/16.
 */
public enum DificultyType {

    BABY,
    EASY,
    NORMAL,
    IMPOSSIBRU;

    /**
     * Check if a value is a valid DificultyType
     * @param value
     * @return
     */
    static public boolean isMember(String value) {
        for (DificultyType me : DificultyType.values()) {
            if (me.name().equalsIgnoreCase(value))
                return true;
        }
        return false;
    }

    public static DificultyType getDificultById(String dif) {
        return DificultyType.valueOf(dif);
    }
}
