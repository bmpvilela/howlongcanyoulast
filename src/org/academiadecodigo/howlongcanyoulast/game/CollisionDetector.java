package org.academiadecodigo.howlongcanyoulast.game;

import org.academiadecodigo.howlongcanyoulast.client.Board;
import org.academiadecodigo.howlongcanyoulast.game.gameobjects.Flag;
import org.academiadecodigo.howlongcanyoulast.game.gameobjects.Player;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by codecadet on 24/06/16.
 */
public abstract class CollisionDetector {


    public static boolean wallCollision(Position position, ArrayList<Position> positions){

        return positions.contains(position);

    }

    public static void flagCollision(Flag flag, ConcurrentHashMap<String, Player> playerMap){

        for (Player player : playerMap.values()) {
            if (flag.getPos().getCol() == player.getPos().getCol() &&
                    flag.getPos().getRow() == player.getPos().getRow()) {
                player.setHasFlag(true);
            }
        }

    }



}
