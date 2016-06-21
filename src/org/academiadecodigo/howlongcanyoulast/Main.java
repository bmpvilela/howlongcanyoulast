package org.academiadecodigo.howlongcanyoulast;

import org.academiadecodigo.howlongcanyoulast.game.Game;

/**
 * Created by brunovilela on 19/06/16.
 */
public class Main {

    public static void main(String[] args) {

        //TODO Choose client or server by args

//        int[] size = FileTools.gridSize("map.txt");
//
//        System.out.println(size[0]+":"+size[1]);
//
//        Game game = new Game(size[0],size[1]);
//        game.init();
//        game.start();

        Game game = new Game();
        game.init();


        System.out.println(game.assemblePlayersInfo());

    }

}
