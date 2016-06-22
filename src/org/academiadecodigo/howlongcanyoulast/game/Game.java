package org.academiadecodigo.howlongcanyoulast.game;

import org.academiadecodigo.howlongcanyoulast.server.Server;
import org.academiadecodigo.howlongcanyoulast.utilities.FileTools;

import java.util.ArrayList;
import java.util.HashMap;
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
    private ConcurrentHashMap<String,Position> positionsList; //Players postisions - Key(String) is Player Name
    private ArrayList<Position> wallsLocations;
    private int numPlayers;
    private String[] playerNames;

    /**
     * Init game
     */
    public void init(){

        //TODO FOR TEST REMOVE
        String[] map = FileTools.fileRead("map.txt");
        //TODO ---------

        storeWallsLocations(map);

        //start server thread
        server = new Server();
        Thread tServer = new Thread(server);
        tServer.start();

        //wait for min number of players
        while ((numPlayers = server.getClientNames().length) <= 2){
            // do nothing
        }

        System.out.println("-- All Players Connected --");

        //add players name (key) and position to HashMap
        positionsList = new ConcurrentHashMap<>();
        playerNames = server.getClientNames();
        for (String name: playerNames){
            positionsList.put(name,new Position());
        }

    }

    /**
     * Check collisions with walls
     * @return
     */
    private void collisionsWithMap(){


    }

    /**
     * Check collisions between players
     * @return
     */
    private boolean collisionsWithPlayers(String myPlayerName, Position myPos){

        for (HashMap.Entry<String, Position> entry : positionsList.entrySet())
            if (!myPlayerName.equals(entry.getKey())) return entry.equals(myPos);

        return false;
    }

    /**
     * Assemble string with all players positions
     * @return
     */
    public String assemblePlayersInfo(){

        String positions="";
        for (ConcurrentHashMap.Entry<String, Position> entry : positionsList.entrySet()) {
            positions = positions + entry.getKey()+":"+entry.getValue().getCol()+":"+entry.getValue().getRow()+" ";
        }

        return positions;
    }

    /**
     * Get position from a specific player
     * @return
     */
    private Position getPlayerPosition(String playerName){
        return positionsList.get(playerName);
    }

    private void storeWallsLocations(String[] str){
        wallsLocations = new ArrayList<>();
        Position pos;
        String tempString;

        for (int rows = 0 ; rows < wallsLocations.size(); rows++) {
            tempString = str[0];
            for (int cols = 0; cols < tempString.length(); cols++) {
                if (tempString.indexOf(cols)!=0) wallsLocations.add(new Position(cols,rows));
            }
        }
    }
}
