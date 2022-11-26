package client;
import java.util.*;
import java.net.*;
import java.io.*;

public class Client {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String name;
    private Integer index = 0;

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
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a username");
        String username = scanner.nextLine();

        Socket socket = new Socket("localhost", 4200);
        Client client = new Client(socket, username);
        scanner.close();
        client.listener();
        client.sendName();
        client.getNextPiece();
    }

    public void sendName(){
        try {
            writer.write(this.name);
            writer.newLine();
            writer.flush();
        }
        catch (IOException e){closeEverything();}

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