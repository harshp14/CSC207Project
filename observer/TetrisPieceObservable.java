package observer;
import java.util.ArrayList;
import tetris.*;

public class TetrisPieceObservable {
    ArrayList<TetrisPiece> pieces = new ArrayList<>();

    public void addPiece(TetrisPiece piece) {pieces.add(piece);}

    public void removePiece() {pieces.remove(0);}

    public ArrayList<TetrisPiece> getPieces() {return this.pieces;}
}
