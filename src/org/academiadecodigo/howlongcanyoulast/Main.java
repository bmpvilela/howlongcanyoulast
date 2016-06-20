package org.academiadecodigo.howlongcanyoulast;

import org.academiadecodigo.howlongcanyoulast.server.Server;

/**
 * Created by brunovilela on 19/06/16.
 */
public class Main {

    public static void main(String[] args) {

        //TODO Choose client or server by args

        /*Game game = new Game(100,25);
        game.init();
        game.start();*/

        Server server = new Server();
        server.start();


    }

}
