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

    //communication methods
    private void sendMessageToServer(String message) {this.server.readInput(message);}

    private void sendMessageToClient(String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        }
        catch (IOException e) {closeEverything(this.socket, this.reader, this.writer);}
    }

    //called by Client
    public void statsToServer(String message) {
        sendMessageToServer(message);
    }

    public void checkForNewPiece() { //combine with statsToServer? they get called together anyway
        sendMessageToServer(name + "| piece placed");
    }

    public void eliminateClient() {
        sendMessageToServer(name + "| was eliminated");
        this.eliminated = true;
        this.spectate();
    }

    public void spectate() {
        while (!this.eliminated) {
            sendMessageToServer(name + "| stats request");
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void removeClient() {sendMessageToServer(name + "| has left the game");}

    //called by Server
    public void gameInfoToClient(String message) {
        sendMessageToClient(message);
    }

    public void pieceToClient(String message) {
        sendMessageToClient(message);
    }

    public void statsToClient(String message) {
        sendMessageToClient(message);
    }
}
