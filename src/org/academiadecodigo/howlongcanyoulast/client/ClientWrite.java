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
    private InetAddress serverAdress;
    private int port;




    public ClientWrite(InetAddress serverAdress, int port ) throws SocketException {


        this.serverAdress = serverAdress;
        this.port = port;
        clientSocket = new DatagramSocket();
        new Thread(new ClientRead(clientSocket)).start();
        System.out.println("Created");
//        this.clientSocket = clientSocket;
//        try {
//            this.clientSocket = new DatagramSocket();
//        } catch (SocketException e) {
//           e.printStackTrace();
//        }
    }

    @Override
    public void run() {

        while(true) {
            Key value = Field.getScreen().readInput();
            if(value != null){
                System.out.println((byte)value.getCharacter());

                byte[] sendBuffer = {(byte)value.getCharacter()};
                DatagramPacket packet;

                try {
                    packet = new DatagramPacket(sendBuffer,sendBuffer.length, serverAdress, port);
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
