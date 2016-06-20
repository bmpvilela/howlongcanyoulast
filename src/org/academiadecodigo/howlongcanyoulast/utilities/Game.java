package org.academiadecodigo.howlongcanyoulast.utilities;

import org.academiadecodigo.howlongcanyoulast.utilities.Field;

/**
 * Created by brunovilela on 19/06/16.
 */
public class Game {

    private int cols;
    private int rows;
    private Field field;

    public Game (int cols, int rows){

        this.cols=cols;
        this.rows = rows;

    }

    public void init(){

        Field.init(cols,rows);
        Field.draw();

    }

    public void start(){

    }

}
