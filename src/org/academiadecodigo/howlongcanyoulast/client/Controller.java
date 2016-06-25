package org.academiadecodigo.howlongcanyoulast.client;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

/**
 * Created by codecadet on 25/06/16.
 */

public class Controller {

    private String playersPositions;
    private Read clientRead;
    private Write clientWrite;
    private DatagramSocket clientSocket;
    private String rawPlayersData;

    public Controller(InetAddress serverAdress, int port) throws SocketException {
        clientSocket = new DatagramSocket();
        clientWrite = new Write(serverAdress,port,this,clientSocket);
        new Thread(clientWrite).start();

        clientRead = new Read(clientSocket,this);
        new Thread(clientRead).start();
    }

    public void setPlayersData(byte[] data){

        rawPlayersData = new String(data, StandardCharsets.UTF_8);
        Board.setAllPlayersPositions(dividPositionsData(rawPlayersData));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Board.draw();
    }

    private String[] dividPositionsData(String playersPositions){

        String[] splitedPlayers = playersPositions.split("\\s+"); //1st split by spaces (IP1:x:y IP2:x:y ...)
        String[] tempData;
        String[] allData = new String[12]; //for store 2nd split by : (IP1 and x and y)

        int i = 0;
        for (int count = 0; count < splitedPlayers.length; count++) {
            tempData = splitedPlayers[count].split("[:]"); //2nd split

            for (int tempDataCount = 0; tempDataCount < tempData.length; tempDataCount++)
                allData[i] = tempData[tempDataCount]; //store each split

            i++;
        }
        return allData;
    }

    public void inicialPositions(String inicialPositions){
        dividPositionsData(inicialPositions);
    }
}

//TODO         Board.init("map.txt");