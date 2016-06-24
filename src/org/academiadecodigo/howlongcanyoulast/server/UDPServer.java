package org.academiadecodigo.howlongcanyoulast.server;

import org.academiadecodigo.howlongcanyoulast.game.Game;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
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

    public UDPServer(Game game){
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

            while(true) {

                DatagramPacket receivePacket = new DatagramPacket(data, data.length);
                System.out.println("waiting to receive");
                serverSocket.receive(receivePacket);

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("received: " + new String(receivePacket.getData()));

                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (!clientList.containsKey(receivePacket.getAddress()) && clientList.size() < MAX_PLAYERS) {

                            ClientThread ct = new ClientThread(serverSocket, receivePacket);

                            synchronized (clientList) {
                                clientList.put(receivePacket.getAddress(), ct);
                            }

                            game.setName("" + receivePacket.getAddress());
                            pool.submit(ct);

                        } else if (clientList.containsKey(receivePacket.getAddress()) &&
                                !clientList.get(receivePacket.getAddress()).isRunning()) {

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

//    public static void main(String[] args) {
//
//        UDPServer server = new UDPServer();
//        server.run();
//
//    }

    class ClientThread implements Runnable {

        //InetAddress addr;
        private DatagramPacket packet;
        private DatagramSocket socket;
        private boolean running = true;

        public ClientThread(DatagramSocket socket, DatagramPacket packet) {
            this.socket = socket;
            this.packet = packet;
        }

        @Override
        public void run() {

            running = true;

            // FAZER AS MERDAS TODAS
            sendToAll();

            running = false;

        }

        public boolean isRunning() {
            return running;
        }

        private int getPort() {
            return packet.getPort();
        }

        private void sendToAll () {
            try {

                for (InetAddress address : clientList.keySet()) {

                    System.out.println(address + " port " + clientList.get(address).getPort());

                    packet = new DatagramPacket("answer\n".getBytes(), "answer\n".getBytes().length, address, clientList.get(address).getPort());
                    this.socket.send(packet);

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
