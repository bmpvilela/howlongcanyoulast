package org.academiadecodigo.howlongcanyoulast.game;

import org.academiadecodigo.howlongcanyoulast.utilities.FileTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.academiadecodigo.howlongcanyoulast.Scores;
import org.academiadecodigo.howlongcanyoulast.utilities.Field;

/**
 * Created by codecadet on 20/06/16.
 */
public class Game {

    private int cols;
    private int rows;
    private Field field;
    private GameTime gameTime;
    private Scores scores;

    public Game(int cols, int rows) {

        this.cols = cols;
        this.rows = rows;
    }

    public void init(int totalPlayers){
        // Field.draw();
        // Field.init(cols,rows);

        Field.init("map2.txt");

        gameTime = new GameTime(totalPlayers);
        scores = new Scores(totalPlayers);

        Field.simpleDraw(GameTextType.getText(GameTextType.WAITING));

        // TODO remove thread sleep
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int stopAnimationAt = GameTextType.getText(GameTextType.READY)[0].length();
        Field.animation(GameTextType.getText(GameTextType.READY), -stopAnimationAt);

        stopAnimationAt = GameTextType.getText(GameTextType.GO)[0].length();
        Field.animation(GameTextType.getText(GameTextType.GO), -stopAnimationAt);

        gameTime.setStartTime();

        while (!gameTime.isGameOver()) {
            Field.draw(gameTime, scores);
        }

        stopAnimationAt = (Field.getWidth() / 2) - (GameTextType.getText(GameTextType.TIMEOUT)[0].length() / 2);
        Field.animation(GameTextType.getText(GameTextType.TIMEOUT), stopAnimationAt);
    }

    public void start() {

    }

    private ConcurrentHashMap<String,Position> positionsList;   //Players positions - Key(String) is Player Name
    private ArrayList<Position> wallsLocations;                 //Walls location for collisions
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
