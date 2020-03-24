package network.server;

import business.ISBNTrimmer;
import config.Communication;
import data.BookCaller;
import domain.JsonResult;

import java.net.*;
import java.io.*;

/**
 *  Represents a single connection between a client and the server
 */
public class LibraryServerThread extends Thread {
    private Socket socket;
    private PrintWriter out;

    LibraryServerThread(Socket socket) {
        super("LibraryServerThread");
        this.socket = socket;
    }

    public void run() {

        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String inputLine;

            printOptionsMessage();

            while ((inputLine = in.readLine()) != null) {
                System.out.println("Client: " + inputLine);

                if (inputLine.startsWith(Communication.ABORT)) {
                    out.println(Communication.ABORT);
                    break;
                }

                if(inputLine.startsWith(Communication.OPTIONS)) {
                    printOptionsMessage();
                    continue;
                }

                if(inputLine.startsWith(Communication.SEARCH)) {
                    try {
                        String ISBN = ISBNTrimmer.retrieveISBN(inputLine);
                        out.println("Searching for book with ISBN " + ISBN + "...");

                        printResultMessage(BookCaller.getBookByISBN(ISBN));
                    } catch(IllegalArgumentException e) {
                        out.println(e.getMessage());
                    }

                    continue;
                }

                out.println("Unknown command");
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printOptionsMessage() {
        out.println("The server supports the following commands:");
        out.println("search <isbn> - searches the web for information on the book with the given ISBN");
        out.println("options - shows this menu");
        out.println("abort - aborts the connection with the server");
    }

    private void printResultMessage(JsonResult result) {
        try {
            out.println("ISBN search returned the following:");
            out.println("Book title: " + result.getBookDetail().getTitle());
            out.println("Book subtitle: " + result.getBookDetail().getSubTitle());
            out.println("Book authors: " + result.getBookDetail().getAuthors());

        } catch(NullPointerException e) {
            out.println("No book was found for this ISBN");
        }
    }
}