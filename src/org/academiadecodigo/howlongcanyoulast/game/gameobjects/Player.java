package org.academiadecodigo.howlongcanyoulast.game.gameobjects;

import org.academiadecodigo.howlongcanyoulast.game.Position;
import org.academiadecodigo.howlongcanyoulast.game.gameobjects.GameObjects;

/**
 * Created by codecadet on 24/06/16.
 */
public class Player extends GameObjects {

    private String name;
    private boolean hasFlag;



    public Player(String name, int col, int row) {
        super(col,row);
        this.name = name;

    }

    public void setHasFlag(Boolean flag){
        this.hasFlag = flag;
    }


    public String getName(){
        return name;
    }

    public boolean hasFlag(){
        return hasFlag;
    }
}
