package org.academiadecodigo.howlongcanyoulast.client;

import org.academiadecodigo.howlongcanyoulast.game.Game;
import org.academiadecodigo.howlongcanyoulast.utilities.DificultyType;
import org.academiadecodigo.howlongcanyoulast.utilities.MazeGenerator;


/**
 * Created by AmauriNakashima on 21/06/2016.
 */
public class Tester {
    public static void main(String[] args) {
        Game game = new Game(100, 25);
        game.init(4);
        game.start();
        ClientRead clientRead = new ClientRead(8080);
        clientRead.start();
    }
}
