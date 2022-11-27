package client;
import javafx.application.Application;
import javafx.stage.Stage;
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

    TetrisModel model;
    TetrisView view;

    public Client(Socket socket, String name) {
        try {
            this.socket = socket;
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.name = name;
        }
        catch (IOException e) {closeEverything();}
    }

    public void listener() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String message;

                while (socket.isConnected())  {
                    try {
                        message = reader.readLine();
                        System.out.println(message); //Print message that is received from server (Do something with it later)
                    }
                    catch (IOException e) {closeEverything();}
                }
            }
        }).start();
    }

    public void closeEverything() {
        try {
            if (this.reader != null) {this.reader.close();}
            if (this.writer != null) {this.writer.close();}
            if (this.socket != null) {this.socket.close();}
        }
        catch (IOException e) {e.printStackTrace(); }
    }

    public static void main(String[] args) throws IOException {
          launch(args);

//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Please enter a username");
//        String username = scanner.nextLine();
//
//        Socket socket = new Socket("localhost", 4200);
//        Client client = new Client(socket, username);
//        scanner.close();
//        client.listener();
//        client.sendName();
//        client.getNextPiece();
    }

    @Override
    public void start(Stage primaryStage) {
        this.model = new TetrisModel(); // create a model
        this.view = new TetrisView(model, primaryStage); //tie the model to the view
        this.model.startGame(); //begin
    }

    public void sendName(){
        try {
            writer.write(this.name);
            writer.newLine();
            writer.flush();
        }
        catch (IOException e){closeEverything();}

    }


    public void update(String message){
        try {
            int temp = message.indexOf("|");
            writer.write(this.name + message.substring(temp + 1));
            writer.newLine();
            writer.flush();
        }
        catch (Exception e) {closeEverything();}
    }

    public void getsEliminated(){
        try {
            writer.write(this.name + "|eliminated");
            writer.newLine();
            writer.flush();
        }
        catch (Exception e) {closeEverything();}
    }



    public void getNextPiece() {
        this.index += 1;
        try {
            writer.write(this.name + "|GetNextPiece" + this.index);
            writer.newLine();
            writer.flush();
        }
        catch (Exception e) {closeEverything();}
    }






}