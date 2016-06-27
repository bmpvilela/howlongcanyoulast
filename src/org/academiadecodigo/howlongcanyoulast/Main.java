package org.academiadecodigo.howlongcanyoulast;

import org.academiadecodigo.howlongcanyoulast.client.Controller;
import org.academiadecodigo.howlongcanyoulast.game.Game;
import org.academiadecodigo.howlongcanyoulast.utilities.DificultyType;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by brunovilela on 19/06/16.
 *
 * Args must be greater than 1
 * Case Server: SERVER DIFICULTY NUMPLAYERS
 * DIFICULTY OPTIONS: BABY, EASY, NORMAL, IMPOSSIBRU;
 * NUMPLAYERS OPTIONS: 2-4
 * Example: SERVER EASY 3
 *
 * Case Client
 * Example CLIENT
 */

public class Main {

    public static void main(String[] args) throws UnknownHostException, SocketException {

        if (args.length > 1) {

            switch (args[0].toUpperCase()) {
                case "CLIENT":
                    if (isIpValid(args[1]))
                        asClient(args);
                    else errorMessage();
                    break;

                case "SERVER":
                    if (args.length > 2 && DificultyType.isMember(args[1]) && isNumPlayersValid(args[2]))
                        asServer(args);
                    else errorMessage();
                    break;

                default:
                errorMessage();
            }
        }else {
            errorMessage();
        }
    }

    private static void asClient(String[] args){

        try {
            Controller controller = new Controller(InetAddress.getByName(args[1]), 8080);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private static void asServer(String args[]){

        new Thread(new Runnable() {
            @Override
            public void run() {
                Game game = new Game(Integer.parseInt(args[2]));
                game.init(DificultyType.valueOf(args[1].toUpperCase()));
            }
        }).start();
    }

    /**
     * Check if a string is a valid IP
     * @param ip
     * @return
     */
    private static boolean isIpValid(String ip){

        return ip.matches("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
    }

    /**
     * Check is the number of players is valid
     *
     */
    private static boolean isNumPlayersValid(String nP){
        if (Integer.parseInt(nP) >= 2 &&  Integer.parseInt(nP) <=4 ) return true;
        return false;
    }


    /**
     * Error message with examples
     */
    private static void errorMessage(){

        System.out.println("[ERROR] Invalid argument(s)");
        System.out.println("---------------------------");
        System.out.println("For server use: SERVER DIFFICULTY NUMPLAYERS");
        System.out.println("DIFFICULTY OPTIONS: BABY, EASY, NORMAL, IMPOSSIBRU");
        System.out.println("NUMPLAYERS OPTIONS: 2-4");
        System.out.println("Example: SERVER EASY 2");
        System.out.println("---------------------------");
        System.out.println("For client use: CLIENT SERVER_IP");
        System.out.println("Example: CLIENT 192.168.1.10");

    }
}
