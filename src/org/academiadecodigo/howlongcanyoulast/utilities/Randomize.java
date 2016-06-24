package org.academiadecodigo.howlongcanyoulast.utilities;

/**
 * Created by codecadet on 20/06/16.
 */
public abstract class Randomize {


    public static int inclusive(int maxNumber){
        return (int) Math.floor(Math.random()* maxNumber);
    }

    public static int exclusive(int maxNumber){
        return (int) Math.floor(Math.random() * maxNumber +1);
    }

}
