package observer;
import tetris.*;
import java.util.ArrayList;

public class TetrisPieceObserver {
    private ArrayList<TetrisPiece> pieces = new ArrayList<>();

    public void update(ArrayList<TetrisPiece> pieces) {
        this.pieces = pieces;
    }

    public ArrayList<TetrisPiece> getPieces() {
        return this.pieces;
    }
}
