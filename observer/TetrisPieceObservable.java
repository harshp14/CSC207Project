package observer;
import java.util.ArrayList;
import tetris.*;
import java.util.Random;

public class TetrisPieceObservable {
    ArrayList<String> pieces = new ArrayList<>();
    String[] possiblePieces = {TetrisPiece.STICK_STR, TetrisPiece.L1_STR, TetrisPiece.L2_STR, TetrisPiece.PYRAMID_STR, TetrisPiece.S1_STR, TetrisPiece.S2_STR, TetrisPiece.SQUARE_STR};

    //Whenever someone needs a new piece that hasn't already been computed, compute it for them
    public void addPiece() {
        this.pieces.add(possiblePieces[(new Random().nextInt(7))]);
    }

    //When everyones index is greater then 0, remove the first piece to save space
    public void removePiece() {pieces.remove(0);}

    //Get all computed pieces
    public String getPieces(int index) {
        if (pieces.size() > index) {addPiece();}
        return pieces.toString();
    }
}