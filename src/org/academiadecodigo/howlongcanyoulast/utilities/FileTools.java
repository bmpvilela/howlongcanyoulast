package org.academiadecodigo.howlongcanyoulast.utilities;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by codecadet on 13/06/16.
 */
public class FileTools {

    /**
     * READ A FILE
     */
    public static String[] fileRead(String fileName) {

        BufferedReader br = null;
        String line = null;
        List<String> lines = null;

        try {

            File theFile = new File(fileName);
            if (!theFile.exists()) {
                theFile.createNewFile();
            }

            FileReader fileReader = new FileReader(theFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            lines = new ArrayList<>();

            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            bufferedReader.close();


        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return lines.toArray(new String[lines.size()]);
    }

    /**
     * CHECK THE LENGHT OF A SPECIFIC FILE
     */
    public static long fileSize(String fileName) {

        long lenght = 0;

        try {

            File fis = new File(fileName);
            if (fis.exists()) lenght = fis.length();

        } catch (Exception e) {
            System.out.println("ERROR reading file: " + e.getMessage());

        } finally {
            return lenght;
        }
    }

    /**
     * CHECK IF A FILE EXISTS AND CAN BE READ
     */
    public static boolean fileExists(String fileName) { //TODO Could be canRead
        File fis = new File(fileName);
        return fis.exists();
    }


    public static void fileWrite(String fileName, String[] toWrite) {

        BufferedWriter bw = null;

        try {

            File theFile = new File(fileName);
            if (!theFile.exists()) {
                theFile.createNewFile();
            }

            FileWriter fw = new FileWriter(theFile);
            bw = new BufferedWriter(fw);



            for (int i = 0; i < toWrite.length ; i++) {
                bw.write(toWrite[i]);
                bw.newLine();
            }

            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();

            } catch (Exception e) {
                System.out.println("Error closing the BufferedWriter: " + e.getMessage());
            }
        }
    }


}



