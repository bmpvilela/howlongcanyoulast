package org.academiadecodigo.howlongcanyoulast.client;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by codecadet on 20/06/16.
 */
public class Client {

    private Socket clientSocket = null;

    public Client(int port){
        try {
            //TODO change InetAdress to the server one
            clientSocket = new Socket(InetAddress.getByName("192.168.1.21"), port);

        } catch (IOException e) {
            System.out.println("Could not reach server");
        }
    }



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

    public byte[] receiveFromServer(){
        byte[] serverData = new byte[1000];

        try {

                BufferedInputStream in = new BufferedInputStream(clientSocket.getInputStream());

                in.read(serverData);


        } catch (IOException e) {
            System.out.println(" fail to receive data from server");
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
