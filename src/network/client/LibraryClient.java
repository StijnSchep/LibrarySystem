package network.client;

import config.Communication;
import config.Connection;

import java.io.*;
import java.net.*;

/**
 * Creates a connection with the server with the selected hostname and port number
 */
public class LibraryClient {

    public static void main(String[] args) {
        String hostName = Connection.HOSTNAME;
        int portNumber = Connection.PORTNUMBER;

        try (
                Socket serverSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(serverSocket.getInputStream()));
        ) {
            BufferedReader stdIn =
                    new BufferedReader(new InputStreamReader(System.in));

            LibraryClientThread inputListener = new LibraryClientThread(in);
            inputListener.start();

            String fromUser;
            while (inputListener.isOpen()) {
                if((fromUser = stdIn.readLine()) == null) { continue; }

                if(fromUser.equals(Communication.ABORT)) {
                    inputListener.close();
                }

                out.println(fromUser);
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }
}