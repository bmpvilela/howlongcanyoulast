package org.academiadecodigo.howlongcanyoulast.game;

import org.academiadecodigo.howlongcanyoulast.server.Server;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by codecadet on 20/06/16.
 */
public class Game {

    //TODO mapGeneration()
    //TODO timers()
    //TODO score()
    //TODO workInputs()
    //TODO generateObjects() //TODO init()
    //TODO colisionDetector() //TODO
    //TODO position() //TODO

    private Server server;
    private ConcurrentHashMap<String,Position> positionsList;
    private int numPlayers;
    private String[] playerNames;

    public void init(){

        //start server thread
        server = new Server();
        Thread tServer = new Thread(server);
        tServer.start();

        //wait for min number of players
        while ((numPlayers = server.getClientNames().length) <= 2){
            // do nothing
        }

        System.out.println("-- all players connected --");

        //add players name (key) and position to HashMap
        positionsList = new ConcurrentHashMap<>();
        playerNames = server.getClientNames();
        for (String name: playerNames){
            positionsList.put(name,new Position());
        }

    }

    private void colisionDetection(){


    }

    /**
     * Assemble string with all players positions
     * @return
     */
    public String assemblePlayersInfo(){

        String positions=null;
        for (ConcurrentHashMap.Entry<String, Position> entry : positionsList.entrySet()) {
            positions = positions + entry.getKey()+":"+entry.getValue().getCol()+":"+entry.getValue().getRow()+" ";
        }

        return positions;
    }

}
