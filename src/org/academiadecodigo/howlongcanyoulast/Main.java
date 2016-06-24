package org.academiadecodigo.howlongcanyoulast;

import org.academiadecodigo.howlongcanyoulast.server.UDPServer;
import org.academiadecodigo.howlongcanyoulast.game.Game;
import org.academiadecodigo.howlongcanyoulast.utilities.DificultyType;
import org.academiadecodigo.howlongcanyoulast.utilities.MazeGenerator;

/**
 * Created by brunovilela on 19/06/16.
 */
public class Main {

    public static void main(String[] args) {

        //TODO Choose client or server by args


        new MazeGenerator("map2.txt", DificultyType.NORMAL).GenerateMap();
        Game game = new Game(50, 15);
        game.init(4);

//        UDPServer server = new UDPServer(game);
//        server.run();

    }

}
