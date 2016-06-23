package org.academiadecodigo.howlongcanyoulast.client;

import org.academiadecodigo.howlongcanyoulast.utilities.DificultyType;
import org.academiadecodigo.howlongcanyoulast.utilities.Game;
import org.academiadecodigo.howlongcanyoulast.utilities.MazeGenerator;

/**
 * Created by AmauriNakashima on 21/06/2016.
 */
public class Tester {
    public static void main(String[] args) {
        MazeGenerator mz = new MazeGenerator(80,30, DificultyType.BABY);
        mz.GenerateMap();
        Game game = new Game(100, 25);
        game.init(4);
        game.start();
        ClientRead clientRead = new ClientRead(8080);
        clientRead.start();
    }
}
