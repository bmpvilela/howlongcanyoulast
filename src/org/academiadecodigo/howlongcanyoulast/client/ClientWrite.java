package org.academiadecodigo.howlongcanyoulast.client;


import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenWriter;
import com.sun.java.swing.plaf.gtk.GTKConstants;
import com.sun.org.apache.xpath.internal.operations.String;
import org.academiadecodigo.howlongcanyoulast.utilities.Field;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.*;


/**
 * Created by codecadet on 20/06/16.
 */
public class ClientWrite implements Runnable  {
    //TODO input()
    //TODO sendToServer()
    DatagramSocket clientSocket = null;




    public ClientWrite(DatagramSocket clientSocket){
       // this.clientSocket = clientSocket;
        try {
            this.clientSocket = new DatagramSocket(8085);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        while(true) {
            Key value = Field.getScreen().readInput();
            if(value != null){
                System.out.println(value.getCharacter());
                byte[] sendBuffer = {convertToByte(value.getCharacter())};
                DatagramPacket packet;

                try {
                    packet = new DatagramPacket(sendBuffer,sendBuffer.length, InetAddress.getByName("127.0.0.1") ,8080);
                    clientSocket.send(packet);
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
