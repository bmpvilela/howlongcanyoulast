package org.academiadecodigo.howlongcanyoulast;

import org.academiadecodigo.howlongcanyoulast.client.Board;

/**
 * Created by andre on 21/06/2016.
 *
 * Class that will dynamically position the scores on the screen
 * using the total of player in the game
 */
public class Scores {

    // Bi-dimensional array that will keep the position of the player score on the screen
    private int[][] playerScorePos;

    /**
     * Constructor of the class scores and will calculate the score
     * position on the screen
     *
     * @param totalPlayers Number of players
     */
    public Scores(int totalPlayers) {
        positioningScores(totalPlayers);
    }

    /**
     * Calculates the percentage of the game screen
     * for each player score
     *
     * @param totalPlayer
     */
    private void positioningScores(int totalPlayer)  {
        if (totalPlayer == 2) {
            playerScorePos = new int[][]{{(int) (Board.getWidth() * 0.25), 0},
                                        {(int) (Board.getWidth() * 0.75), 0}};
        } else if (totalPlayer == 3) {
            playerScorePos = new int[][]{{(int) (Board.getWidth() * 0.20), 0},
                                        {(int) (Board.getWidth() * 0.45), 0},
                                        {(int) (Board.getWidth() * 0.70), 0}};
        } else if (totalPlayer == 4) {
            playerScorePos = new int[][]{{(int) (Board.getWidth() * 0.15), 0},
                                        {(int) (Board.getWidth() * 0.35), 0},
                                        {(int) (Board.getWidth() * 0.55), 0},
                                        {(int) (Board.getWidth() * 0.75), 0}};
        }
    }

    /**
     * Getter
     *
     * @return Score position on the screen for the player
     */
    public int[][] getScores() {
        return playerScorePos;
    }
}
