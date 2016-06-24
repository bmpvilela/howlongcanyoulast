package org.academiadecodigo.howlongcanyoulast.game;

import org.academiadecodigo.howlongcanyoulast.server.UDPServer;

import org.academiadecodigo.howlongcanyoulast.utilities.EnumColors;
import org.academiadecodigo.howlongcanyoulast.utilities.FileTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.academiadecodigo.howlongcanyoulast.Scores;

/**
 * Created by codecadet on 20/06/16.
 */
public class Game {

    private int cols;
    private int rows;
    private GameTime gameTime;
    private Scores scores;

    private ConcurrentHashMap<String,Position> positionsList;   //Players positions - Key(String) is Player Name
    private ArrayList<Position> wallsLocations;                 //Walls location for collisions
    private int numPlayers;
    private String[] playerNames;

    private UDPServer myServer;

    public Game(int cols, int rows) {

        this.cols = cols;
        this.rows = rows;
        playerNames = new String[4];
        myServer = new UDPServer(this);
        new Thread(myServer);
    }

    public void init(int totalPlayers){

    }

    public void start() {

    }

    /**
     * Init game
     */
    public void init(){

        //TODO FOR TEST REMOVE
        String[] map = FileTools.fileRead("map.txt");
        //TODO ---------

        storeWallsLocations(map);

        System.out.println("-- All Players Connected --");

        //add players name (key) and position to HashMap
        positionsList = new ConcurrentHashMap<>();
        for (String name: playerNames){
            positionsList.put(name,new Position());
        }

    }

    /**
     * Check collisions with walls
     * @return
     */
    private void collisionsWithMap(){
        // server.setAllPlayerPositions(assemblePlayersInfo()); //TODO Server in game
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
                if (tempString.indexOf(cols) == EnumColors.WHITE.ordinal() || tempString.indexOf(cols) == EnumColors.MAGENTA.ordinal()) wallsLocations.add(new Position(cols,rows));
            }
        }
    }
}
