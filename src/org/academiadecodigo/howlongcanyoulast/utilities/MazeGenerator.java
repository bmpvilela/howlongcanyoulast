package org.academiadecodigo.howlongcanyoulast.utilities;


import org.academiadecodigo.howlongcanyoulast.FileTools;

/**
 * Created by codecadet on 20/06/16.
 */
public abstract class MazeGenerator {

    private static final int MAX_SIZE_ROW = 30;
    private static final int MAX_SIZE_COL = 100;

    public static void GenerateMap(){

        String[] toReturn = new String[MAX_SIZE_ROW];

        int pathFrom = Randomize.inclusive(MAX_SIZE_ROW);


        for (int i = 0; i < MAX_SIZE_ROW; i++) {

            toReturn[i] = new String("");

            for (int j = 0; j < MAX_SIZE_COL ; j++) {

                //Generate Borders
                if(i < 2 || j == 0 || i > MAX_SIZE_ROW-2 || j == MAX_SIZE_COL-1){
                    toReturn[i] += 1; //Color blue for the border

                //Set the players positions switch the 0 for player cursor
                } else if((i < 3 && j < 3) || (i> MAX_SIZE_ROW-3 && j < 3) || (i<3 && j>MAX_SIZE_COL-4) || (i>MAX_SIZE_ROW-3 && j>MAX_SIZE_COL-4) ){
                    toReturn[i] += 0; // Empty square change for the player icon
                //Set the outside path
                } else if(i<3 || j<3 || i>MAX_SIZE_ROW-3 || j>MAX_SIZE_COL-4){
                    toReturn[i] += 2;

                //Set the center
                } else if(i> MAX_SIZE_ROW/2-4 && j > MAX_SIZE_COL/2-4 && i < MAX_SIZE_ROW/2+4 && j <MAX_SIZE_COL/2+4 ){
                    toReturn[i] += 3;
                    System.out.println("col: " + i + " row: " +j);
                } else if(i > pathFrom-2 && i<pathFrom){
                    toReturn[i] += 5;
                }


                else {
                    toReturn[i] += 4;
                   // toReturn[i] += Randomize.inclusive(7);

                }
            }
        }

        FileTools.fileWrite("map2.txt", toReturn);


    }











}
