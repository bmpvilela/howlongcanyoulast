package org.academiadecodigo.howlongcanyoulast.server;

import org.academiadecodigo.howlongcanyoulast.game.Game;
import org.academiadecodigo.howlongcanyoulast.game.gameobjects.Player;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by codecadet on 22/06/16.
 */
public class UDPServer implements Runnable {

    private static final int MAX_PLAYERS = 4;
    private DatagramSocket serverSocket;

    private Game game;
    private ConcurrentHashMap<InetAddress, ClientThread> clientList;

    public UDPServer(Game game) {
        this.game = game;
    }


    @Override
    public void run() {

        serverSocket = null;

        ExecutorService pool = Executors.newFixedThreadPool(4);
        clientList = new ConcurrentHashMap<>();

        try {
            serverSocket = new DatagramSocket(8080);
            byte[] data = new byte[5];

            while (true) {

                DatagramPacket receivePacket = new DatagramPacket(data, data.length);
                System.out.println("waiting to receive");
                serverSocket.receive(receivePacket);

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        if (!clientList.containsKey(receivePacket.getAddress()) && clientList.size() < MAX_PLAYERS) {

                            ClientThread ct = new ClientThread(receivePacket);

                            synchronized (clientList) {
                                clientList.put(receivePacket.getAddress(), ct);
                            }

                            pool.submit(ct);

                        } else if (clientList.containsKey(receivePacket.getAddress()) &&
                                !clientList.get(receivePacket.getAddress()).isRunning()) {

                            clientList.get(receivePacket.getAddress()).setPacket(receivePacket);
                            pool.submit(clientList.get(receivePacket.getAddress()));

                        }
                    }
                });

                t.start();

            }


        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    class ClientThread implements Runnable {

        //int port;
        private DatagramPacket packet;
        private Player myPlayer;
        private boolean running = true;


        public ClientThread(DatagramPacket packet) {
            this.packet = packet;
            game.putPlayer("" + packet.getAddress());
        }

        @Override
        public void run() {


            String received = "";
            byte[] bytes = new byte[packet.getLength()];

            for (int i = 0; i < bytes.length; i++) {

                received += Byte.toString(packet.getData()[i]);

            }


            //Send the input to the player
            System.out.println(received);

        }


        public boolean isRunning() {
            return running;
        }


        public void setPacket(DatagramPacket packet){
            this.packet = packet;

        }
    }
}


