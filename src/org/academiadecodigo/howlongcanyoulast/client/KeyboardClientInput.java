package org.academiadecodigo.howlongcanyoulast.client;


import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenWriter;
import com.sun.java.swing.plaf.gtk.GTKConstants;
import com.sun.org.apache.xpath.internal.operations.String;
import org.academiadecodigo.howlongcanyoulast.utilities.Field;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;


/**
 * Created by codecadet on 20/06/16.
 */
public class KeyboardClientInput implements Runnable  {
    //TODO input()
    //TODO sendToServer()
    BufferedOutputStream out = null;
    Socket clientSocket = null;



    public KeyboardClientInput(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

        try {
           out = new BufferedOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true) {
            Key value = Field.getScreen().readInput();
            if(value != null){
                System.out.println(value.getCharacter());
                try {
                    out.write((int)value.getCharacter());
                   out.flush();

              } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    public byte getInput(){
//       Key value = Field.getScreen().readInput();
//
//
//   }





}
