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

    public ClientHandler(Socket socket, Server server) {
        try {
            this.socket = socket;
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.name = reader.readLine();
            this.server = server;
            sendMessageToServer("SERVER: " + this.name + " has joined");
        }
        //Disconnect client if there is an error connecting to the server
        catch (IOException e){closeEverything(socket, reader, writer);}

    }

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

    public void sendMessageToServer(String message) {this.server.readInput(message);}

    public void removeClient() {sendMessageToServer(name + "| has left the game");}

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
}
