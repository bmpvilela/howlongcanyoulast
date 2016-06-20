package org.academiadecodigo.howlongcanyoulast;

import org.academiadecodigo.howlongcanyoulast.client.Client;
import org.academiadecodigo.howlongcanyoulast.utilities.MazeGenerator;

/**
 * Created by brunovilela on 19/06/16.
 */
public class Main {

    public static void main(String[] args) {

        //TODO Choose client or server by args

        System.out.println("Generate");
        MazeGenerator.GenerateMaze();
        System.out.println("Generated");
        Game game = new Game(100, 25);
        game.init();
        game.start();
    }

}
