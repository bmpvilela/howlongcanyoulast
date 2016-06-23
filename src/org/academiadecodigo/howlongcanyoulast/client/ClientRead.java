package org.academiadecodigo.howlongcanyoulast.client;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

/**
 * Created by codecadet on 20/06/16.
 */
public class ClientRead {

   // private Socket clientSocket = null;
    private DatagramSocket clientSocket =null;

    public ClientRead(int port){
        try {
            clientSocket = new DatagramSocket();

        } catch (SocketException e) {
            System.out.println("Fail to create socket");
        }
    }


    public void start(){

        Thread t = new Thread(new ClientWrite(clientSocket));
        t.start();

        while(true){
            display(receiveFromServer());
        }
    }


    public byte[] receiveFromServer() {
        byte[] serverData = new byte[1000];
        try {
            // Create and receive UDP datagram packet from the socket
            DatagramPacket receivePacket = new DatagramPacket(serverData, serverData.length);
            System.out.println("waiting to receive from server");
            clientSocket.receive(receivePacket); // blocks while packet not received
            System.out.println("received");

            //System.out.println(serverData);

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


    public byte[] byteToSend(ArrayList<Byte> data){
        byte[] clientData;
        clientData = new byte[data.size()];
        for(int i = 0; i < data.size(); i++){
            clientData[i] = data.get(i);
        }
        return clientData;

    }

    //TODO receiveFromServer()
    //TODO display()

}
