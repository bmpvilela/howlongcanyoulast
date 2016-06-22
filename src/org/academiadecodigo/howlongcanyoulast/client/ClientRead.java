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
public class Client {

   // private Socket clientSocket = null;
    private DatagramSocket clientSocket =null;

    public Client(int port){
        try {
            clientSocket = new DatagramSocket(port);

        } catch (SocketException e) {
            System.out.println("Fail to create socket");
        }


    }
     /*   try {
            //TODO change InetAdress to the server one
           // clientSocket = new Socket(InetAddress.getByName("192.168.1.21"), port);

        } catch (IOException e) {
            System.out.println("Could not reach server");
        }
    }*/

    public void start(){

        Thread t = new Thread(new KeyboardClientInput(clientSocket));
        t.start();

        while(clientSocket.isConnected()){
            display(receiveFromServer());
        }
        try {
            if(clientSocket != null) {
                clientSocket.close();
            }
        } catch (IOException e) {
            System.out.println("fail to close socket");
        }

    }

    public byte[] receiveFromServer() {
        byte[] serverData = new byte[1024];

        try {
            // Create and receive UDP datagram packet from the socket
            DatagramPacket receivePacket = new DatagramPacket(serverData, serverData.length);
            clientSocket.receive(receivePacket); // blocks while packet not received

            System.out.println(new String(receivePacket.getData()));

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serverData;
    }


    public void display(byte[] serverData){
        System.out.println(serverData.toString());

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
