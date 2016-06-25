package org.academiadecodigo.howlongcanyoulast.client;

import com.googlecode.lanterna.input.Key;
import org.academiadecodigo.howlongcanyoulast.Scores;
import org.academiadecodigo.howlongcanyoulast.game.GameTime;

import java.io.IOException;
import java.net.*;


/**
 * Created by codecadet on 20/06/16.
 */
public class Write implements Runnable {

    DatagramSocket clientSocket = null;
    private InetAddress serverAdress;
    private int port;
    private Controller controller;

    Scores score;
    GameTime gameTime;

    public Write(InetAddress serverAdress, int port, Controller controller, DatagramSocket clientSocket) throws SocketException {

        this.controller = controller;
        this.serverAdress = serverAdress;
        this.port = port;
        this.clientSocket = clientSocket;

        System.out.println("Created");

    }

    @Override
    public void run() {

        try {

            clientSocket.send(new DatagramPacket(new byte[10], 10, serverAdress, port));


            while (true) {
                Key value = Board.getKey();

                if (value != null) {
                    System.out.println((byte) value.getCharacter());

                    byte[] sendBuffer = {(byte) value.getCharacter()};
                    DatagramPacket packet;

                    packet = new DatagramPacket(sendBuffer, sendBuffer.length, serverAdress, port);
                    System.out.println("waiting to send");
                    clientSocket.send(packet);
                    System.out.println("sent");

                }
            }
        } catch (IOException e) {
            System.out.println("Fail to send packet");
        }
    }

    public byte convertToByte(char direction) {
        switch (direction) {
            case 'L':
                return 1;

            case 'R':
                return 2;

            case 'U':
                return 3;

            case 'D':
                return 4;
            default:
                return 0;
        }
    }
}
