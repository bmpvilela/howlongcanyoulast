package org.academiadecodigo.howlongcanyoulast.client;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

/**
 * Created by codecadet on 20/06/16.
 */
public class Read implements Runnable{

    private DatagramSocket clientSocket =null;
    private byte[] playersPositions;
    private Controller controller;

    public Read(DatagramSocket clientSocket, Controller controller){

        this.controller = controller;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run(){


        while(true) {
            System.out.println("w");

            String fromServer = receiveFromServer();

            System.out.println("Data from Server: " + fromServer);

            if (fromServer.length() > 100) {
                controller.initMap(fromServer);
            } else {
                controller.setPlayersData(fromServer);
                //TODO Start time
                System.out.println("s");
            }
        }
    }

    public String receiveFromServer() {
        DatagramPacket receivePacket = null;
        byte[] serverData = new byte[4000];

        try {
            // Create and receive UDP datagram packet from the socket
            receivePacket = new DatagramPacket(serverData, serverData.length);
            System.out.println("waiting to receive from server");
            clientSocket.receive(receivePacket); // blocks while packet not received
            System.out.println("received");

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(receivePacket.getData(), receivePacket.getOffset(), receivePacket.getLength());

    }

    public void display(byte[] serverData){
        System.out.println(serverData);
    }

    public byte[] getPlayerPosition(){
        return playersPositions;
    }
}
