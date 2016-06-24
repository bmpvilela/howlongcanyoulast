package org.academiadecodigo.howlongcanyoulast.game.gameobjects;

import org.academiadecodigo.howlongcanyoulast.game.Position;

/**
 * Created by codecadet on 24/06/16.
 */
public abstract class GameObjects {

    private Position pos;

    public GameObjects(int col, int row){
        this.pos = new Position(col, row);

    }

    public void setPos(Position pos){
        this.pos = pos;
    }

    public Position getPos(){
        return this.pos;
    }

}
