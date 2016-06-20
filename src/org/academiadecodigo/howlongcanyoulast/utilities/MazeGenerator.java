package org.academiadecodigo.howlongcanyoulast.utilities;


import com.apple.eio.FileManager;
import org.academiadecodigo.howlongcanyoulast.FileTools;

/**
 * Created by codecadet on 20/06/16.
 */
public abstract class MazeGenerator {

    private static final int MAX_SIZE = 50;

    public static void GenerateMaze(){

        System.out.println("Generate maze");

        String[] toReturn = new String[MAX_SIZE];


        for (int i = 0; i < MAX_SIZE; i++) {

            toReturn[i] = new String("");

            for (int j = 0; j <MAX_SIZE ; j++) {

                toReturn[i] += Randomize.inclusive(7);
            }
        }

        System.out.println("Sent to write");
        FileTools.fileWrite("map2.txt", toReturn);


    }









}
