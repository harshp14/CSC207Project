package client;


import observer.TetrisPieceObserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

public class PlayingState implements SuperState{
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    public TetrisPieceObserver observer;

    /**
     * Constructor
     *
     * @param writer communicates with the server
     * @param observer observer
     * @param socket socket for the server
     * @param reader contains message from the server
     */
    public PlayingState(BufferedWriter writer, TetrisPieceObserver observer, Socket socket, BufferedReader reader){
        this.writer = writer;
        this.observer = observer;
        this.socket = socket;
        this.reader = reader;
    }

    /**
     * Determines what the message represents and runs a specific method depending on the message
     *
     * @param message message sent from the server
     */
    public void listening(String message){
        if (message.substring(0, 6) == "Update"){
            update(message);
        }
        else if  (message.substring(0, 5) == "Piece"){
             placedPiece();
        }

    }

    /**
     * Updates the observer
     *
     * @param message message sent from the server
     */
    public void update(String message){
        try {
            int temp = message.indexOf("|");
            observer.update(message.substring(temp + 1).split(","));
        }
        catch (Exception e) {closeEverything();}
    }

    /**
     * Updates server on placed piece and score
     */
    public void placedPiece() {
//        this.index += 1;
//        try {
//            writer.write("placedPiece|" + this.index);
//            writer.newLine();
//            writer.flush();
//        }
//        catch (Exception e) {closeEverything();}
    }

    /**
     * Closes the reader, writer and socket
     */
    public void closeEverything() {
        try {
            if (this.reader != null) {this.reader.close();}
            if (this.writer != null) {this.writer.close();}
            if (this.socket != null) {this.socket.close();}
        }
        catch (IOException e) {e.printStackTrace(); }
    }



}
