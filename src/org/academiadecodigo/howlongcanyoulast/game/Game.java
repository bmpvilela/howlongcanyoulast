package org.academiadecodigo.howlongcanyoulast.game;

import org.academiadecodigo.howlongcanyoulast.client.Board;
import org.academiadecodigo.howlongcanyoulast.game.gameobjects.Flag;
import org.academiadecodigo.howlongcanyoulast.game.gameobjects.Player;
import org.academiadecodigo.howlongcanyoulast.server.UDPServer;
import org.academiadecodigo.howlongcanyoulast.utilities.Direction;
import org.academiadecodigo.howlongcanyoulast.utilities.FileTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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

    private ConcurrentHashMap<String, Player> positionsList;   //Players positions - Key(String) is Player Name
    private ArrayList<Position> wallsLocations;                 //Walls location for collisions
    private Flag flag;                                          //flag object
    private String[] playerNames;
    private LinkedList<Position> playerStartPositions;

    private UDPServer myServer;


    public Game(int cols, int rows) {

        this.cols = cols;
        this.rows = rows;
        playerNames = new String[UDPServer.MAX_PLAYERS];
        myServer = new UDPServer(this);
        new Thread(myServer).start();
        playerStartPositions = new LinkedList<>();
    }

    public void init(int totalPlayers){

        synchronized (myServer.getClientList()) {
            positionsList = new ConcurrentHashMap<>();
            String[] map = FileTools.fileRead("map2.txt");
            storeInitialInfo(map);
            gameTime = new GameTime(totalPlayers);
            scores = new Scores(totalPlayers);
           //TODO maybe this method shouldnt be here putFlag();

            while (myServer.getPlayerAmount() != UDPServer.MAX_PLAYERS-2) {
                try {

                    myServer.sendToAll("waiting");
                    myServer.getClientList().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            String map2 = "";
            for (int i = 0; i < map.length; i++) {
                map2 += map[i] + " ";

            }
            System.out.println(map2);
            myServer.sendToAll(map2);

        }
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
//TODO Amauri
    public void putFlag(){
        flag = new Flag(cols/2, rows/2);
        int[] initialPosition = {flag.getPos().getCol(),flag.getPos().getRow()};
        Board.setFlagPosition(initialPosition);
    }

    /**
     *
     * @param name Player name, Is his Inet Address
     * @param whereTo Direction where he wants to move
     * @return the updated position, Either the position where he was in case of failure or the new position if the check's succeeds
     */

    public void movePlayer(String name, Direction whereTo) {

        Position playerPos = positionsList.get(name).getPos();
        System.out.println("Col: " + playerPos.getCol() + " Row: " + playerPos.getRow());

        switch (whereTo) {

            case LEFT:
                if (!CollisionDetector.wallCollision(new Position(playerPos.getCol() - 1, playerPos.getRow()), wallsLocations)) {
                    playerPos.setCol(playerPos.getCol() - 1);
                }
                break;

            case RIGHT:
                if (!CollisionDetector.wallCollision(new Position(playerPos.getCol() + 1, playerPos.getRow()), wallsLocations)) {
                    playerPos.setCol(playerPos.getCol() + 1);
                }
                break;

            case UP:
                if (!CollisionDetector.wallCollision(new Position(playerPos.getCol(), playerPos.getRow() - 1), wallsLocations)) {
                    playerPos.setRow(playerPos.getRow() - 1);
                }
                break;
            case DOWN:
                if (!CollisionDetector.wallCollision(new Position(playerPos.getCol(), playerPos.getRow() + 1), wallsLocations)) {
                    playerPos.setRow(playerPos.getRow() + 1);
                }
                break;
            default:
                System.out.println("Something went wrong in the movePlayer()!");
                break;

        }

        System.out.println("Updated Col: " + playerPos.getCol() + " Updated Row: " + playerPos.getRow());
        positionsList.get(name).setPos(playerPos);

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
        String tempString;


        for (int rows = 0; rows < str.length; rows++) {
            tempString = str[rows];
            for (int cols = 0; cols < tempString.length(); cols++) {

                if (tempString.charAt(cols) == '1' || tempString.charAt(cols) == '7'){
                    wallsLocations.add(new Position(cols, rows));
                } if(tempString.charAt(cols) == '4'){
                    playerStartPositions.add(new Position(cols,rows));

                }
            }
        }
    }

    public Flag getFlag() {
        return flag;
    }
}
