package org.academiadecodigo.howlongcanyoulast.client;

import com.googlecode.lanterna.input.Key;
import org.academiadecodigo.howlongcanyoulast.Scores;
import org.academiadecodigo.howlongcanyoulast.game.GameTime;

import java.io.IOException;
import java.net.*;


/**
 * Created by codecadet on 20/06/16.
 */
public class ClientWrite implements Runnable  {

    private String playersPositions;
    DatagramSocket clientSocket = null;
    Scores score;
    GameTime gameTime;

    public ClientWrite(DatagramSocket clientSocket){

        this.clientSocket = clientSocket;
//        try {
//            this.clientSocket = new DatagramSocket();
//        } catch (SocketException e) {
//            e.printStackTrace();
//        }

        ClientBoard.init("map.txt");

        score = new Scores(4);
        gameTime = new GameTime(4);

    }

    @Override
    public void run() {

        gameTime.setStartTime();

        while(true) {
            Key value = ClientBoard.getKey();

            if (playersPositions != null) ClientBoard.setAllPlayersPositions(dividPositionsData(playersPositions));
            ClientBoard.draw(gameTime, score);

            if(value != null){

                System.out.println((byte)value.getCharacter());

                byte[] sendBuffer = {(byte)value.getCharacter()};
                DatagramPacket packet;

                try {
                    packet = new DatagramPacket(sendBuffer,sendBuffer.length, clientSocket.getInetAddress() ,clientSocket.getPort());
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

    public void setPlayersPositions(String playersPositions){
        this.playersPositions = playersPositions;
    }

    //TODO best location for method? here or clientboard
    private String[] dividPositionsData(String playersPositions){

        String[] splitedPlayers = playersPositions.split("\\s+"); //1st split by spaces (IP1:x:y IP2:x:y ...)
        String[] tempData = new String[3];
        String[] allData = new String[12]; //for store 2nd split by : (IP1 and x and y)

        int i = 0;
        for (int count = 0; count < splitedPlayers.length; count++) {
            tempData = splitedPlayers[count].split("[.]"); //2nd split

            for (int tempDataCount = 0; tempDataCount < tempData.length; tempDataCount++)
                allData[i] = tempData[tempDataCount]; //store each split

            i++;
        }
    return allData;
    }
}
