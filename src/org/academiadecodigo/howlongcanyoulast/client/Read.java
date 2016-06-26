package org.academiadecodigo.howlongcanyoulast.client;

import org.academiadecodigo.howlongcanyoulast.game.GameTextType;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;

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
            String fromServer = receiveFromServer();

            System.out.println("Data from Server: " + fromServer);

            // TODO Andre removi souts, acho eu.... Ver so pelo sim pelo nao
            if (fromServer.length() > 100) {
                System.out.println("tste");
                controller.initMap(fromServer);
            } else if (fromServer.equals("waiting")) {

                Board.simpleDraw(GameTextType.getText(GameTextType.WAITING));

            } else if (fromServer.contains("flag")) {

                String[] flagPos = fromServer.split("flag")[1].split(":");
                Board.setFlagPosition(new int[]{Integer.parseInt(flagPos[0]), Integer.parseInt(flagPos[1])});

            } else {
                controller.setPlayersData(fromServer);
                //TODO Start time
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
