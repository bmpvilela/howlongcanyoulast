package org.academiadecodigo.howlongcanyoulast;

/**
 * Created by brunovilela on 19/06/16.
 */
public class Game {

    private int cols;
    private int rows;
    private Field field;
    private GameTime gameTime;
    private Scores scores;

    public Game (int cols, int rows){

        this.cols = cols;
        this.rows = rows;
    }

    public void init(int totalPlayers){

        Field.init(cols,rows);
        gameTime = new GameTime(totalPlayers);
        scores = new Scores(totalPlayers);

        gameTime.setStartTime();

        while (true) {
            Field.draw(gameTime, scores);
        }

    }

    public void start(){

    }

}
