package org.academiadecodigo.howlongcanyoulast;

import org.academiadecodigo.howlongcanyoulast.client.Controller;
import org.academiadecodigo.howlongcanyoulast.client.Write;
import org.academiadecodigo.howlongcanyoulast.game.Game;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by brunovilela on 19/06/16.
 */
public class Main {

    public static void main(String[] args) throws UnknownHostException, SocketException {

        if (args.length > 0) {

            switch (args[0].toUpperCase()) {
                case "CLIENT":
                    Controller controller = new Controller(InetAddress.getByName("127.0.0.1") ,8080);
                    break;

                case "SERVER":
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Game game = new Game(100, 25);
                            game.init(4);
                        }
                    }).start();
                    break;

                default:
                    System.out.println("[ERROR] Invalid argument");
            }
        }else{
            System.out.println("[ERROR] Invalid argument");
        }
    }
}
