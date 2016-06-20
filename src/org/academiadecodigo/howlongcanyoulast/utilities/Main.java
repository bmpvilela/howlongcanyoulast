package org.academiadecodigo.howlongcanyoulast.utilities;

import com.googlecode.lanterna.screen.Screen;
import org.academiadecodigo.howlongcanyoulast.client.Client;
import org.academiadecodigo.howlongcanyoulast.client.KeyboardClientInput;
import org.academiadecodigo.howlongcanyoulast.utilities.Game;

/**
 * Created by brunovilela on 19/06/16.
 */
public class Main {

    public static void main(String[] args) {

        //TODO Choose client or server by args

        Game game = new Game(100,25);
        game.init();
        game.start();
        Client c1 = new Client(8080);
        c1.start();


    }

}
