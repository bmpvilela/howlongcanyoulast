package org.academiadecodigo.howlongcanyoulast.game;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by codecadet on 24/06/16.
 */
public abstract class CollisionDetector {


    public static boolean wallCollision(Position position, ArrayList<Position> positions){

        return positions.contains(position);

    }

//    public static boolean flagCollision(String name, ConcurrentHashMap<String, Position> playerMap){
//
//
//
//    }



}
