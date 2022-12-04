package client;
import javafx.application.Application;
import javafx.stage.Stage;
import observer.TetrisPieceObserver;
import tetris.TetrisModel;
import tetris.TetrisView;



import java.util.*;
import java.net.*;
import java.io.*;




public class Client extends Application {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String name;
    private Integer index = 0;

    private TetrisModel model;
    private TetrisView view;

    public TetrisPieceObserver observer;

    public SuperState curr;

    /**
     * Constructor
     *
     * @param name
     * @param socket
     */
    public Client(Socket socket, String name) {

        try {
            this.socket = socket;
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.name = name;
            this.curr = new PlayingState(this.writer, this.observer, this.socket, this.reader);
        }
        catch (IOException e) {closeEverything();}
    }


    /**
     * Listens for a message from the server and sends the message to the current player state
     *
     */
    public void listener() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String message;

                while (socket.isConnected())  {
                    try {
                        message = reader.readLine();
                        curr.listening(message); //Print message that is received from server (Do something with it later)
                    }
                    catch (IOException e) {closeEverything();}
                }
            }
        }).start();
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


    /**
     * Starts the game
     */
    public static void run(String[] args){
        launch(args);
    }


    public static void main(String[] args) throws IOException {


        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a username");
        String username = scanner.nextLine();

        Socket socket = new Socket("localhost", 4200);
        Client client = new Client(socket, username);
        scanner.close();
        client.listener();
        client.sendName();

    }

    /**
     * Sends the name to the server
     */
    public void sendName(){
        try {
            writer.write(this.name);
            writer.newLine();
            writer.flush();
        }
        catch (IOException e){closeEverything();}

    }

    /**
     * Helper function for run when the game starts
     */
    @Override
    public void start(Stage primaryStage) {
        this.model = new TetrisModel(); // create a model
        this.view = new TetrisView(model, primaryStage); //tie the model to the view
        this.model.startGame(); //begin
    }


    /**
     * Player is eliminated and the player state should change to eliminated
     */
    public void elimnated(){
        this.curr = new EliminatedState(this.writer, this.observer, this.socket, this.reader);
    }









}