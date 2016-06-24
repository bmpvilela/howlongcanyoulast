package org.academiadecodigo.howlongcanyoulast.game.gameobjects;

import org.academiadecodigo.howlongcanyoulast.game.Position;
import org.academiadecodigo.howlongcanyoulast.game.gameobjects.GameObjects;

/**
 * Created by codecadet on 24/06/16.
 */
public class Flag extends GameObjects {

    private Position pos;


    public Flag(int col, int row){
        super(col,row);
    }


    public void setPos(Position pos){
        this.pos = pos;
    }

    public Position getPos(){
        return this.pos;
    }

}
