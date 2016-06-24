package org.academiadecodigo.howlongcanyoulast.client;


import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenWriter;
import com.sun.java.swing.plaf.gtk.GTKConstants;
import com.sun.org.apache.xpath.internal.operations.String;
import org.academiadecodigo.howlongcanyoulast.Scores;
import org.academiadecodigo.howlongcanyoulast.game.GameTime;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.*;


/**
 * Created by codecadet on 20/06/16.
 */
public class ClientWrite implements Runnable  {

    DatagramSocket clientSocket = null;
    Scores score;
    GameTime gameTime;


    public ClientWrite(DatagramSocket clientSocket){

        this.clientSocket = clientSocket;
//        try {
//            this.clientSocket = new DatagramSocket();
//        } catch (SocketException e) {
//            e.printStackTrace();
//        }

        ClientBoard.init("map.txt");

        score = new Scores(4);
        gameTime = new GameTime(4);

    }

    @Override
    public void run() {

        gameTime.setStartTime();

        while(true) {
            Key value = ClientBoard.getKey();
            ClientBoard.draw(gameTime, score);

            if(value != null){

                System.out.println((byte)value.getCharacter());

                byte[] sendBuffer = {(byte)value.getCharacter()};
                DatagramPacket packet;

                try {
                    packet = new DatagramPacket(sendBuffer,sendBuffer.length, InetAddress.getByName("127.0.0.1") ,8080);
                    System.out.println("waiting to send");
                    clientSocket.send(packet);
                    System.out.println("sent");
                } catch (IOException e) {
                    System.out.println("Fail to send packet");
                }
            }
        }
    }

    public byte convertToByte(char direction){
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
