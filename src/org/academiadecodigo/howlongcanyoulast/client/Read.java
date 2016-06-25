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

        byte[] serverData = new byte[1000];

        while(true){
            System.out.println("w");
            System.out.println(receiveFromServer(serverData));
            controller.setPlayersData(receiveFromServer(serverData));
            //TODO Start time
            System.out.println("s");
        }
    }

    public byte[] receiveFromServer(byte[] serverData) {
        try {
            // Create and receive UDP datagram packet from the socket
            DatagramPacket receivePacket = new DatagramPacket(serverData, serverData.length);
            System.out.println("waiting to receive from server");
            clientSocket.receive(receivePacket); // blocks while packet not received
            System.out.println("received");

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serverData;
    }

    public void display(byte[] serverData){
        System.out.println(serverData);
    }

    public byte[] getPlayerPosition(){
        return playersPositions;
    }
}
