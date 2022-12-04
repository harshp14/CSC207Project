package client;

import observer.TetrisPieceObserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

public class EliminatedState implements SuperState{
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
    public EliminatedState(BufferedWriter writer, TetrisPieceObserver observer, Socket socket, BufferedReader reader){
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
        if (message.substring(0, 5) == "Stats"){
            getStats(message);
        }
    }

    /**
     * Takes the stats from the message and displays them to the player
     *
     * @param message message sent from the server that contains the stats
     */
    public void getStats(String message){

    }

}
