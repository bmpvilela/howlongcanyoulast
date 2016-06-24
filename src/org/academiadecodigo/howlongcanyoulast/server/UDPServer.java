package org.academiadecodigo.howlongcanyoulast.server;

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
public class UDPServer implements Runnable{

    private static final int MAX_PLAYERS = 4;
    private String allPlayerPositions;

    private DatagramSocket serverSocket = null;
    private ExecutorService pool;
    private HashMap<InetAddress, ClientThread> clientList;

    @Override
    public void run() {

        serverSocket = null;
        pool = Executors.newFixedThreadPool(4);
        clientList = new HashMap<>();


        try {
            serverSocket = new DatagramSocket(8080);
            byte[] data = new byte[1];

            while(true) {

                DatagramPacket receivePacket = new DatagramPacket(data, data.length);
                System.out.println("waiting to receive");
                serverSocket.receive(receivePacket);
                System.out.println("received: " + new String(receivePacket.getData()));

                if (!clientList.containsKey(receivePacket.getAddress()) && clientList.size() < MAX_PLAYERS) {

                    ClientThread ct = new ClientThread(serverSocket, receivePacket);
                    clientList.put(receivePacket.getAddress(), ct);
                    pool.submit(ct);

                } else if (clientList.containsKey(receivePacket.getAddress()) &&
                        !clientList.get(receivePacket.getAddress()).isRunning()) {

                    pool.submit(clientList.get(receivePacket.getAddress()));

                }

            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*public void sendToAll() {
        for () {

        }
    }*/

//    public static void main(String[] args) {
//
//        UDPServer server = new UDPServer();
//        server.start();
//
//    }

    class ClientThread implements Runnable {

        //InetAddress addr;
        //int port;
        DatagramPacket packet;
        DatagramSocket socket;
        private boolean running = true;


        public ClientThread(DatagramSocket socket, DatagramPacket packet) {
            this.socket = socket;
            this.packet = packet;
        }

        @Override
        public void run() {

            try {
                running = true;

                // FAZER AS MERDAS TODAS
                packet = new DatagramPacket("answer\n".getBytes(), "answer\n".getBytes().length, packet.getAddress(), packet.getPort());
                this.socket.send(packet);

                System.out.println();

                running = false;

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public boolean isRunning() {
            return running;
        }
    }

    public void setAllPlayerPositions(String allPositions){
        this.allPlayerPositions = allPositions;
    }

}
