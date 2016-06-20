package org.academiadecodigo.howlongcanyoulast.server;

import java.io.*;
import java.net.Socket;

/**
 * Each server has a number of threads, each dedicated to one client. They receive all the output
 * from the client and ask the server to transmit the data to all clients.
 *
 * Created by codecadet on 20/06/16.
 */
public class MySpecialClientThread implements Runnable {

    private Socket socket;
    private Server server;
    private String name;

    public MySpecialClientThread(Socket socket, Server server, String name) {

        this.socket = socket;
        this.server = server;
        this.name = name;

    }

    public String getName() {
        return name;
    }

    // Declaring input and output streams
    BufferedInputStream inputStream;
    BufferedOutputStream outputStream;
    byte[] bytes = new byte[1]; //TODO a verificar tamanho do array

    /**
     * Assigns input and output stream that receives data from the specified socket, commanding
     * the server to send it to all clients connected to it.
     */
    @Override
    public void run() {

        try {
            inputStream = new BufferedInputStream(socket.getInputStream());
            outputStream = new BufferedOutputStream(socket.getOutputStream());

            while(true) {
                // Waits for input and stores it in an array of bytes
                int i = inputStream.read(bytes);    //TODO atençao ao null
                //String str = new String(bytes);
                //System.out.println(str);
                if (i == -1) {
                    server.clientList.remove(this);
                    break;
                }
                // Commands server to send the byte array to all clients
                server.getClientsName(bytes, name);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Commands the current thread to outstream an array of bytes
     * @param bytes
     */
    public void write(byte[] bytes) {
        try {
            outputStream.write(bytes);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
