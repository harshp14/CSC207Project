package observer;
import tetris.*;
import java.util.ArrayList;

public class TetrisPieceObserver {
    private ArrayList<TetrisPiece> pieces = new ArrayList<>();

    public void update(String[] pieces) {
        for(String piece : pieces) {
            this.pieces.add(new TetrisPiece(piece));
        }
    }

    public ArrayList<TetrisPiece> getPieces() {
        return this.pieces;
    }
}
