package server;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

import observer.TetrisPieceObservable;
import tetris.TetrisPiece;


public class Server {
    //Networking Variables
    private ServerSocket serverSocket;
    private ArrayList<ClientHandler> clients = new ArrayList<>();
    private TetrisPieceObservable observable = new TetrisPieceObservable();

    //Game Variables
    private Integer bufferSize = 5; //Number of extra pieces to preload

    //Server Management
    public Server (ServerSocket socket) {serverSocket = socket;}

    public void startServer() {
        int numPlayers = 0;
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("New connection");
                this.clients.add(new ClientHandler(socket, this));
                numPlayers += 1;
                Thread thread = new Thread(this.clients.get(numPlayers - 1));
                thread.start();
            }
        }
        //Error handling next steps: Should server close if there is an error when someone is trying to join?
        catch (IOException e) {}
    }

    public void closeServer() {
        //Close server socket if it isn't null
        try {if (serverSocket != null) {serverSocket.close();}}
        catch (IOException e){e.printStackTrace();}
    }

    public static void main (String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4200); //<-- Whatever this is needs to match the port number on the client
        Server server = new Server(serverSocket);
        server.startServer();
    }

    public void readInput(String input) {
        //Do something with this input
        System.out.println(input);
    }

    //Game Management
    public void computePiece() {
        //Create new tetris piece
        TetrisPiece piece = new TetrisPiece();

        //Add it to observable list
        this.observable.addPiece(piece);
    }

    public void startGame() {
        for (int i = 0; i < this.bufferSize; i++) {
            TetrisPiece piece = new TetrisPiece(); //Create new tetris piece
            this.observable.addPiece(piece); //Add it to observable list
        }
    }
}
