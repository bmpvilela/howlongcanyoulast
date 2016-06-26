package org.academiadecodigo.howlongcanyoulast.client;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

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

    public void setPlayersData(String data){

        // TODO Andre var don't do nothing... data nao estava a ser usada

        //rawPlayersData = "";
        Board.setAllPlayersPositions(dividPositionsData(data));
    }

    private String[] dividPositionsData(String playersPositions){

        String[] splitedPlayers = playersPositions.split("\\s+"); //1st split by spaces (IP1:x:y IP2:x:y ...)
        String[] tempData;
        String[] allData = new String[12]; //for store 2nd split by : (IP1 and x and y)

        // TODO Andre mexi aqui coloquei o i mais mais dentro do for porque tem de fazer um update todas as vezes que mexe num tempdata
        int i = 0;
        for (int count = 0; count < splitedPlayers.length; count++) {
            tempData = splitedPlayers[count].split("[:]"); //2nd split

            for (int tempDataCount = 0; tempDataCount < tempData.length; tempDataCount++) {
                allData[i] = tempData[tempDataCount]; //store each split
                i++;
            }
        }

        return allData;
    }

    public void inicialPositions(String inicialPositions){
        dividPositionsData(inicialPositions);
    }

    public void initMap(String map){

        String[] mapToFeed = map.split(" ");

        Board.init(mapToFeed);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    Board.draw();
                }
            }
        }).start();

    }

}

//TODO         Board.init("map.txt");