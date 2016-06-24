package org.academiadecodigo.howlongcanyoulast.game;

import org.academiadecodigo.howlongcanyoulast.game.gameobjects.Player;
import org.academiadecodigo.howlongcanyoulast.server.UDPServer;
import org.academiadecodigo.howlongcanyoulast.utilities.Direction;
import org.academiadecodigo.howlongcanyoulast.utilities.EnumColors;
import org.academiadecodigo.howlongcanyoulast.utilities.FileTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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

    private ConcurrentHashMap<String, Player> positionsList;   //Players positions - Key(String) is Player Name
    private ArrayList<Position> wallsLocations;                 //Walls location for collisions
    private int numPlayers;
    private String[] playerNames;
    private LinkedList<Position> playerStartPositions;

    private UDPServer myServer;


    public Game(int cols, int rows) {

        this.cols = cols;
        this.rows = rows;
        playerNames = new String[4];
        myServer = new UDPServer(this);
        new Thread(myServer);
        playerStartPositions = new LinkedList<>();
    }

    public void init(int totalPlayers) {
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

    /**
     * Init game
     */

    public void init() {

        //TODO FOR TEST REMOVE
        String[] map = FileTools.fileRead("map.txt");
        //TODO ---------

        storeInitialInfo(map);

        System.out.println("-- All Players Connected --");

        //add players name (key) and position to HashMap
        positionsList = new ConcurrentHashMap<>();


    }


    /**
     * Add players to the ConcurrentHashMap
     * @param name player name to be created
     */
    public void putPlayer(String name){

        synchronized (playerStartPositions) {

            Position position = playerStartPositions.remove();
            positionsList.put(name, new Player(name, position.getCol(),position.getRow()));

        }
    }

    /**
     *
     * @param name Player name, Is his Inet Address
     * @param whereTo Direction where he wants to move
     * @return the updated position, Either the position where he was in case of failure or the new position if the check's succeeds
     */

    private Position movePlayer(String name, Direction whereTo) {

        Position playerPos = positionsList.get(name).getPos();

        switch (whereTo) {

            case UP:
                if (!CollisionDetector.wallCollision(new Position(playerPos.getCol() - 1, playerPos.getRow()), wallsLocations)) {
                    playerPos.setCol(playerPos.getCol() - 1);
                }
                break;

            case DOWN:
                if (!CollisionDetector.wallCollision(new Position(playerPos.getCol() + 1, playerPos.getRow()), wallsLocations)) {
                    playerPos.setCol(playerPos.getCol() + 1);
                }
                break;

            case LEFT:
                if (!CollisionDetector.wallCollision(new Position(playerPos.getCol(), playerPos.getRow() - 1), wallsLocations)) {
                    playerPos.setRow(playerPos.getRow() - 1);
                }
                break;
            case RIGHT:
                if (!CollisionDetector.wallCollision(new Position(playerPos.getCol(), playerPos.getRow() + 1), wallsLocations)) {
                    playerPos.setRow(playerPos.getRow() + 1);
                }
                break;
            default:
                System.out.println("Something went wrong in the movePlayer()!");
                return null;

        }

        return playerPos;


    }


    /**
     * Check collisions between players
     *
     * @return
     */
    private boolean collisionsWithPlayers(String myPlayerName, Position myPos) {


        for (HashMap.Entry<String, Player> entry : positionsList.entrySet())

        //If they don't have the same name then check if there is any with the same position
            if (!myPlayerName.equals(entry.getKey())) return entry.getValue().getPos().equals(myPos);

        return false;
    }

    /**
     * Assemble string with all players positions
     *
     * @return
     */
    public String assemblePlayersInfo() {

        String positions = "";
        for (ConcurrentHashMap.Entry<String, Player> entry : positionsList.entrySet()) {
            positions = positions + entry.getKey() + ":" + entry.getValue().getPos().getCol() + ":" + entry.getValue().getPos().getRow() + " ";
        }

        return positions;
    }

    /**
     * Get position from a specific player
     *
     * @return
     */
    private Position getPlayerPosition(String playerName) {
        return positionsList.get(playerName).getPos();
    }

    private void storeInitialInfo(String[] str) {
        wallsLocations = new ArrayList<>();
        Position pos;
        String tempString;

        for (int rows = 0; rows < wallsLocations.size(); rows++) {
            tempString = str[rows];
            for (int cols = 0; cols < tempString.length(); cols++) {
                if (tempString.charAt(cols) == 1 || tempString.charAt(cols) == 7){
                    wallsLocations.add(new Position(cols, rows));
                } else if(tempString.charAt(cols) == EnumColors.GREEN.ordinal()){
                    playerStartPositions.add(new Position(cols,rows));

                }
            }
        }
    }


}
