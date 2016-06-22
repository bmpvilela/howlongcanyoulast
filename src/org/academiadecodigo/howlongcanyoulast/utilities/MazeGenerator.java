package org.academiadecodigo.howlongcanyoulast.utilities;



/**
 * Created by codecadet on 20/06/16.
 */
public class MazeGenerator {


    private String[] map;
    private int[][] cells;
    private int difficulty;


    /**
     * Constructor with a file template
     * @param path to the file
     * @param difficulty how hard it is max number 4
     */

    public MazeGenerator(String path, DificultyType difficulty){

        map = FileTools.fileRead(path);
        setDifficulty(difficulty.ordinal());
        cells = new int[map.length][map[0].length()];

    }

    /**
     * Constructor with specified cols and rows
     * @param cols
     * @param rows
     * @param difficulty how hard it is max number 4
     */
    public MazeGenerator(int cols, int rows, DificultyType difficulty){

        setDifficulty(difficulty.ordinal());

        if(cols <= 100 && rows <= 30){
            cells = new int[rows][cols];
            map = new String[rows];
        } else{
            cells = new int[100][30];
            map = new String[30];
        }



    }


    /**
     * Generate the map, will have a 2 tile border up top, 1 everywhere else, 4 player capacity,
     */
    public void GenerateMap(){


        for (int i = 0; i < cells.length; i++) {

            for (int j = 0; j < cells[i].length ; j++) {

                //Generate Borders
                if(i < 2 || j == 0 || i > cells.length-2 || j == cells[i].length-1){
                    cells[i][j] += EnumColors.BLUE.ordinal(); //Color blue for the border

                //Set the players positions switch the 0 for player cursor
                } else if((i < 3 && j < 3) || (i> cells.length-3 && j < 3) || (i<3 && j>cells[i].length-4) || (i>cells.length-3 && j>cells[i].length-4) ){
                    cells[i][j] += EnumColors.GREEN.ordinal(); // Empty square change for the player icon
                //Set the outside path
                } else if(i<3 || j<3 || i>cells.length-3 || j>cells[i].length-4){
                    cells[i][j] += EnumColors.BLACK.ordinal();

                //Set the center
                } else if(i> cells.length/2-4 && j > cells[i].length/2-4 && i < cells.length/2+4 && j <cells[i].length/2+4 ){
                    cells[i][j] += EnumColors.MAGENTA.ordinal();
                }


                else {
                    cells[i][j] += generateMaze(i,j);

                }
            }
        }

        saveFile();


    }

    private int generateMaze(int col, int row) {

        if(cells[col][row-1] == EnumColors.WHITE.ordinal() && cells[col][row-2] == EnumColors.BLACK.ordinal() ){
            return EnumColors.WHITE.ordinal();
        }
        if(cells[col][row-1] == EnumColors.BLACK.ordinal() && cells[col][row-2] == EnumColors.WHITE.ordinal()){
            return EnumColors.BLACK.ordinal();
        }


        switch (howManyWalls(col,row)){

            case 0:
                return Randomize.inclusive(30) < difficulty+1 ? EnumColors.WHITE.ordinal() : EnumColors.BLACK.ordinal(); // half a chance to generate a wall
            case 1:
                return Randomize.exclusive(30) < 3 * difficulty+1 ? EnumColors.WHITE.ordinal() : EnumColors.BLACK.ordinal();
            case 2:
                return Randomize.exclusive(30) < 2 * difficulty+1 ? EnumColors.WHITE.ordinal(): EnumColors.BLACK.ordinal();

            default:
                return EnumColors.BLACK.ordinal();

        }



    }

    private int howManyWalls(int col, int row) {

        int howMany = 0;

        for (int i = col-1; i <=col+1 ; i++) {
            for (int j = row-1; j <=row+1 ; j++) {

                if(i == col && j == row || i == col-1 && j == row-1 || i == col+1 && j == row-1 || i == col-1 && j == row+1){ //Don't want to compare to myself or to the corners

                } else if(cells[i][j] == EnumColors.WHITE.ordinal()){
                    howMany++;
                }

            }
        }
        return howMany;
    }

    private void saveFile(){

        String[] toSave = new String[cells.length];

        for (int i = 0; i < cells.length ; i++) {
            toSave[i] = new String("");
            for (int j = 0; j <cells[0].length ; j++) {
                toSave[i] += cells[i][j];
            }
        }

        FileTools.fileWrite("map2.txt" ,toSave);


    }

    private void setDifficulty(int difficulty){
            this.difficulty = difficulty;
    }


}
