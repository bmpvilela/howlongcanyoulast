package org.academiadecodigo.howlongcanyoulast.game.gameobjects;

import org.academiadecodigo.howlongcanyoulast.game.Position;
import org.academiadecodigo.howlongcanyoulast.game.gameobjects.GameObjects;

/**
 * Created by codecadet on 24/06/16.
 */
public class Flag extends GameObjects {

    private boolean flagTaken;

    public Flag(int col, int row){
        super(col,row);
    }


    public void setFlagTaken(boolean taken){
        this.flagTaken = taken;
    }

    public boolean isFlagTaken(){
        return flagTaken;
    }
}
