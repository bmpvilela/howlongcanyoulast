package org.academiadecodigo.howlongcanyoulast.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by codecadet on 20/06/16.
 */
public class Server {

    //TODO broadCast()
    //TODO receiveInput()

    private static final int PORT_NUMBER = 8080;
    private static final int MAX_NUM_PLAYERS = 4; //TODO a mudar se necessário

    List<MySpecialClientThread> clientList = Collections.synchronizedList(new ArrayList<>(MAX_NUM_PLAYERS));

    ExecutorService pool = null;

    public void start() {

        pool = Executors.newFixedThreadPool(MAX_NUM_PLAYERS);
        ServerSocket s = null;

        try {
            s = new ServerSocket(PORT_NUMBER);

            Socket clientSocket;
            int i = 1;

            while(true) {
                clientSocket = s.accept();
                System.out.println("Someone connected.");
                MySpecialClientThread clientThread = new MySpecialClientThread(clientSocket, this, "Player "+i);
                i++;
                pool.submit(new Thread(clientThread));
                clientList.add(clientThread);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
            try {
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    protected String[] getClientNames() {
        String[] names = new String[clientList.size()];
        Iterator it = clientList.iterator();
        int counter = 0;
        while (it.hasNext()){
            names[0]=((MySpecialClientThread)it.next()).getName();
            counter++;
        }
        return names;
    }



}
