package org.academiadecodigo.howlongcanyoulast.server;

import org.academiadecodigo.howlongcanyoulast.client.Board;
import org.academiadecodigo.howlongcanyoulast.game.Game;
import org.academiadecodigo.howlongcanyoulast.game.GameTextType;
import org.academiadecodigo.howlongcanyoulast.utilities.Direction;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by codecadet on 22/06/16.
 */
public class UDPServer implements Runnable {

    private int maxPlayers;
    private DatagramSocket serverSocket;

    private Game game;
    private HashMap<InetAddress, ClientThread> clientList;

    public UDPServer(Game game, int maxPlayers) {
        this.maxPlayers = maxPlayers;
        this.game = game;
        clientList = new HashMap<>();

    }


    @Override
    public void run() {

        serverSocket = null;

        ExecutorService pool = Executors.newFixedThreadPool(maxPlayers);

        try {
            serverSocket = new DatagramSocket(8080);
            byte[] data = new byte[5];

            while (true) {

                DatagramPacket receivePacket = new DatagramPacket(data, data.length);
                System.out.println("waiting to receive");
                serverSocket.receive(receivePacket);
                System.out.println("Received");

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        if (!clientList.containsKey(receivePacket.getAddress()) && clientList.size() < maxPlayers) {

                            ClientThread ct = new ClientThread(receivePacket);

                            synchronized (clientList) {
                                clientList.put(receivePacket.getAddress(), ct);
                                clientList.notifyAll();

                            }

                            pool.submit(ct);

                        } else if (clientList.containsKey(receivePacket.getAddress())) {

                            clientList.get(receivePacket.getAddress()).setPacket(receivePacket);
                            pool.submit(clientList.get(receivePacket.getAddress()));

                        }
                    }

                });

                t.start();

                if (clientList.size() == maxPlayers && game.isGameOver()) {
                    sendToAll("gameOver");
                    break;
                }

            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendToAll(String toSend) { // argumento array de pos?

        for (ClientThread ct : clientList.values()){
            ct.send(toSend);
        }

    }

    public HashMap<InetAddress,ClientThread> getClientList() {
        return clientList;
    }


    class ClientThread implements Runnable {

        private DatagramPacket packet;
        private boolean running;
        private String name;
        private DatagramSocket socket;


        public ClientThread(DatagramPacket packet) {
            try {
                socket = new DatagramSocket();
            } catch (SocketException e) {
                e.printStackTrace();
            }
            this.packet = packet;
            name = "" + packet.getAddress();
            game.putPlayer(name);
            send(game.generateFirstString());
        }

        @Override
        public void run() {

            running = true;

            String received = "";
            byte[] bytes = new byte[packet.getLength()];

            for (int i = 0; i < bytes.length; i++) {

                received += Byte.toString(packet.getData()[i]);

            }

            game.movePlayer(name, Direction.getDir((char)Integer.parseInt(received)));

            sendToAll(game.assemblePlayersInfo());

            //sendToAll(game.getTime()); // TODO Time

            //Send the input to the player
            //System.out.println(received);
            running = false;

        }


        public boolean isRunning() {
            return running;
        }


        public void setPacket(DatagramPacket packet){
            this.packet = packet;

        }

        public void send(String s) {

            byte[] sendBuffer = s.getBytes();


            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, packet.getAddress(), packet.getPort());

            try {
                socket.send(sendPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public int getPlayerAmount(){
        return clientList.size();
    }

    public int getMaxPlayers(){
        return maxPlayers;
    }
}


