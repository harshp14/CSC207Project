package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;

public class ClientHandler implements Runnable {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String name;
    private Server server;
    private Boolean eliminated;

    public ClientHandler(Socket socket, Server server) {
        try {
            this.socket = socket;
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.name = reader.readLine();
            this.server = server;
            this.eliminated = false;
            sendMessageToServer("SERVER: " + this.name + " has joined");
        }
        //Disconnect client if there is an error connecting to the server
        catch (IOException e){closeEverything(socket, reader, writer);}
    }

    /**
     * Send messages between the client and server while the client's game is running.
     * If it isn't, close the connection.
     */
    @Override
    public void run() {
        String messageFromClient;

        while (socket.isConnected()) {
            try {
                messageFromClient = reader.readLine();
                sendMessageToServer(messageFromClient);
            }
            catch (IOException e) {
                //Disconnect client if there is an error connecting to the server
                closeEverything(socket, reader, writer);
                break;
            }
        }
    }

    /**
     * Close the connection between the client and the server.
     */
    public void closeEverything(Socket socket, BufferedReader reader, BufferedWriter writer) {
        removeClient();
        try {
            if (reader != null) {reader.close();}
            if (writer != null) {writer.close();}
            if (socket != null) {socket.close();}
        }
        //Error in closing the reader, writer and the socket
        catch (IOException e) {e.printStackTrace(); }
    }

    /**
     * Attaches the client's name to its message and sends the message to the server.
     */
    public void sendMessageToServer(String message) { this.server.readInput(this.name + ":" + message);}

    /**
     * Sends a message from the server to the client as-is.
     */
    public void sendMessageToClient(String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        }
        catch (IOException e) {closeEverything(this.socket, this.reader, this.writer);}
    }

    /**
     * Sends a message to the server that the client has left the game.
     */
    public void removeClient() {sendMessageToServer(name + "| has left the game");}

}
