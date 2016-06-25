package org.academiadecodigo.howlongcanyoulast;

import org.academiadecodigo.howlongcanyoulast.client.ClientWrite;
import org.academiadecodigo.howlongcanyoulast.game.Game;
import org.academiadecodigo.howlongcanyoulast.utilities.DificultyType;
import org.academiadecodigo.howlongcanyoulast.utilities.MazeGenerator;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


/**
 * Created by AmauriNakashima on 21/06/2016.
 */
public class Tester {
    public static void main(String[] args) throws SocketException, UnknownHostException {


        new Thread(new Runnable() {
            @Override
            public void run() {
                Game game = new Game(100, 25);
                game.init(4);
            }
        }).start();
        ClientWrite clientWrite = new ClientWrite(InetAddress.getByName("192.168.1.17") ,8080);
        clientWrite.run();
    }
}
