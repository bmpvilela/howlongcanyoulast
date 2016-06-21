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

        int stopAnimationAt = GameTextType.getText(GameTextType.TIMEOUT)[0].length();

        Field.animation(GameTextType.getText(GameTextType.READY), -stopAnimationAt);
        Field.animation(GameTextType.getText(GameTextType.GO), -stopAnimationAt);

        gameTime.setStartTime();

        while (!gameTime.isGameOver()) {
            Field.draw(gameTime, scores);
        }

        stopAnimationAt = (Field.getWidth() / 2) - (GameTextType.getText(GameTextType.TIMEOUT)[0].length() / 2);
        Field.animation(GameTextType.getText(GameTextType.TIMEOUT), stopAnimationAt);
    }

    public void start(){

    }

}
