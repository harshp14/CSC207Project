package server;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import observer.TetrisPieceObservable;
import tetris.TetrisPiece;


public class Server {
    //Networking Variables
    private ServerSocket serverSocket;
    private ArrayList<ClientHandler> clients = new ArrayList<>();
    private TetrisPieceObservable observable = new TetrisPieceObservable();

    private HashMap<String, Integer> playerIndicies = new HashMap<>();

    /**
     * Starts the game and lets each client member know that the game has begun.
     */
    public void startGame() {
        this.observable.addPiece();
        this.observable.addPiece();
        this.observable.addPiece();
        for (int i = 0; i < this.playerIndicies.size(); i++) {
            clients.get(i).sendMessageToClient("startGame|");
        }
    }

    /**
     * Check if any new pieces need to be added or removed from the observable
     */
    public void placedPiece(){
        this.observable.checkSize();

    }

    /**
     * Eliminates client when they lose, takes <username>:eliminated|
     */
    public void eliminateClient(){

    }

    /**
     * Sends the stats of the client to the client, sends <username>:sendStats|
     */
    public void sendStats(){

    }

    //Server Management
    public Server (ServerSocket socket) {serverSocket = socket;}


    /**
     * When starting a new server we add the new client connection to an arraylist of clients and start a thread for each
     * client.
     */
    public void startServer() {
        try {
            Scanner scan = new Scanner(System.in);
            System.out.println("How many players will there be for this round?");
            int num = scan.nextInt();
            for(int i = 0; i < num; i++){
                Socket socket = serverSocket.accept();
                System.out.println("New connection");
                this.clients.add(new ClientHandler(socket, this));
                Thread thread = new Thread(this.clients.get(clients.size()-1));
                thread.start();
            }
            startGame();
        }
        //Error handling next steps: Should server close if there is an error when someone is trying to join?
        catch (IOException e) {}
    }

    /**
     * Closes the server if it is open.
     */
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

    /**
     * Process the input string as required
     * @param input
     */
    public void readInput(String input) {
        //Do something with this input
        System.out.println(input);
    }

}
