package org.academiadecodigo.howlongcanyoulast.game;

import org.academiadecodigo.howlongcanyoulast.client.Board;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by andre on 20/06/2016.
 *
 * Class responsible for the game time
 * and to save the time that players have the flag
 */
public class GameTime {

    // Position of the timer on the screen
    private int rowPos;
    private int colPos;

    // Reference when the game started
    private long startTime;

    // Reference for the time passed
    private long elapsedTime;

    // HashMap that keeps the amount of time that each player has the flag
    private HashMap<String, Integer> playerFlagTime;

    // Reference for the time game time
    private long minutes;
    private long seconds;

    // Duration of the game in minutes
    private int gameDuration = 0;

    /**
     * Constructor for the GameTime
     * Assign the position of the timer on the screen
     *
     * @param totalPlayer Total player in the game
     */
    public GameTime(int totalPlayer) {
        colPos = Board.getWidth() / 2 - ("##:##".length() / 2);
        rowPos = 1;

        playerFlagTime = new HashMap<>();

        createPlayersRegistry(totalPlayer);
    }

    /**
     * Get the time to be printed
     * Converts the elapsed time into minutes and seconds
     *
     * @return Time formatted (Time: MM:SS)
     */
    public String getGameTime() {
        // Updates the elapsed time
        elapsedTime = System.nanoTime() - startTime;

        // Convert the nanoTime to minutes and seconds
        seconds = (elapsedTime / 1000000000) % 60;
        minutes =  TimeUnit.NANOSECONDS.toMinutes(elapsedTime);

        if (minutes < 1 && seconds < 11)
            return "!Last 10 seconds";

        else if (seconds >= 28 && seconds <= 32)
            return "!Remaining time " + minutes + ":" + seconds;

        else
            return "";
    }

    /**
     * Setter for start time of the game
     */
    public void setStartTime() {
        startTime = System.nanoTime();
    }

    /**
     * Updates the flag time of the player
     *
     * @param playerName Player that will be updated
     * @param flagTime Time that he had the flag
     */
    public void updatePlayerTime(String playerName, int flagTime) {
        int time = playerFlagTime.get(playerName);
        playerFlagTime.replace(playerName, time + flagTime);
    }

    /**
     * Creates the HashMap for the players time registry
     *
     * @param totalPlayer Total of player in the game
     */
    private void createPlayersRegistry(int totalPlayer) {
        for (int i = 1; i <= totalPlayer; i++) {
            playerFlagTime.put("Player" + i, 0);
        }
    }

    /**
     * Check if the game its over
     *
     * @return
     */
    public boolean isGameOver() {
        return (getGameDuration() - minutes) == 0 && (59 - seconds) == 0;
    }

    /**
     * Getter
     *
     * @return Row position
     */
    public int getRowPos() {
        return rowPos;
    }

    /**
     * Getter
     *
     * @return Col position
     */
    public int getColPos() {
        return colPos;
    }

    /**
     * Getter
     *
     * @return Time of each player has the flag
     */
    public HashMap<String, Integer> getPlayerFlagTime() {
        return playerFlagTime;
    }

    public int getGameDuration() {
        return gameDuration + 1;
    }
}
