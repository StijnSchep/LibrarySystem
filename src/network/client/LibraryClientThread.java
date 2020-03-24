package network.client;

import config.Communication;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Provides continues printing of server input as it becomes available
 */
public class LibraryClientThread extends Thread {
    private BufferedReader inputReader;
    private boolean isOpen = true;

    public LibraryClientThread(BufferedReader inputReader) {
        this.inputReader = inputReader;
    }

    public void run() {
        String fromServer;

        try {
            while ((fromServer = inputReader.readLine()) != null) {
                if (fromServer.equals(Communication.ABORT)) {
                    isOpen = false;
                    break;
                }

                System.out.println("server: " + fromServer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return whether the connection is still open
     */
    public boolean isOpen() {
        return isOpen;
    }

    /**
     * Set isOpen to false
     * Allows the main thread to recognize when the connection is closed
     */
    public void close() {
        isOpen = false;
    }
}
