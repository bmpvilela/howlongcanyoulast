package org.academiadecodigo.howlongcanyoulast;

import org.academiadecodigo.howlongcanyoulast.server.UDPServer;
import org.academiadecodigo.howlongcanyoulast.utilities.DificultyType;
import org.academiadecodigo.howlongcanyoulast.utilities.MazeGenerator;

/**
 * Created by brunovilela on 19/06/16.
 */
public class Main {

    public static void main(String[] args) {

        //TODO Choose client or server by args

        MazeGenerator mz = new MazeGenerator(80,30, DificultyType.NORMAL);
        mz.GenerateMap();
        Game game = new Game(100, 25);
        game.init(4);

        UDPServer server = new UDPServer();
        server.start();

    }

}
