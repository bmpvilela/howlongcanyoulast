package org.academiadecodigo.howlongcanyoulast.utilities;

/**
 * Created by codecadet on 21/06/16.
 */
public enum Direction {

    UP('U'),
    DOWN('D'),
    LEFT('L'),
    RIGHT('R');

    char dir;

    Direction(char dir) {
        this.dir = dir;
    }

    public static Direction getDir(char dir) {

        switch (dir){
            case 'U':
                return UP;
            case 'D':
                return DOWN;
            case 'L':
                return LEFT;
            case 'R':
                return RIGHT;
            default:
                return null;
        }

    }

}