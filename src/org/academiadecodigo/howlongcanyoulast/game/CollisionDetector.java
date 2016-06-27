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


    public static boolean wallCollision(Position position, ArrayList<Position> positions) {

        return positions.contains(position);

    }

    /**
     * Iterates over the hashmap and flag's the player with the flag
     * @param flag gameobject
     * @param player check for one player
     * @return true if found false if not
     */
    public static boolean flagCollision(Flag flag, Player player) {

            if (flag.getPos().getCol() == player.getPos().getCol() &&
                    flag.getPos().getRow() == player.getPos().getRow()) {
                return true;

            }

        //System.out.println(flag.getPos().getCol() + " " + flag.getPos().getRow());
        return false;

    }

    public static boolean flagPlayerCollision(Flag flag, Player challenger) {

            if (flagCollision(flag, challenger)) {
                return true;
            }

        return false;
    }


}
