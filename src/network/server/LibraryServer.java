package network.server;

import config.Connection;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Opens the server to incoming connections
 */
public class LibraryServer {
    public static void main(String[] args) {
        int portNumber = Connection.PORTNUMBER;
        boolean listening = true;

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (listening) {
                new LibraryServerThread(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}
