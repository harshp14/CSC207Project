package client;

import decorator.*;
import tetris.Colour;
import tetris.TetrisModel.MoveType;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * Tetris View
 *
 * Based on the Tetris assignment in the Nifty Assignments Database, authored by Nick Parlante
 */
public class SpectatorView {

    Stage stage;
    Colour colour = new Colour();
    gameDecorator decorator = new classicDecorator(colour);
    Label scoreLabel = new Label("");
    Label gameModeLabel = new Label("");

    BorderPane borderPane;
    Canvas canvas;
    GraphicsContext gc; //the graphics context will be linked to the canvas

    Boolean paused;
    Timeline timeline;

    int pieceWidth = 20; //width of block on display
    private double width; //height and width of canvas
    private double height;

    /**
     * Constructor
     *
     * @param stage application stage
     */

    public SpectatorView(Stage stage) {
        this.stage = stage;
        initUI();
    }

    /**
     * Initialize interface
     */
    private void initUI() {
        this.paused = false;
        this.stage.setTitle("CSC207 Tetris");
        this.width = 100*pieceWidth + 2;
        this.height = 100*pieceWidth + 2;

        borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #121212;");

        //add canvas
        canvas = new Canvas(this.width, this.height);
        canvas.setId("Canvas");
        gc = canvas.getGraphicsContext2D();

        //labels
        gameModeLabel.setId("GameModeLabel");

        gameModeLabel.setText("You Died");
        gameModeLabel.setMinWidth(250);
        gameModeLabel.setFont(new Font(20));
        gameModeLabel.setStyle("-fx-text-fill: #e8e6e3");

        borderPane.setCenter(canvas);

        var scene = new Scene(borderPane, 800, 800);
        this.stage.setScene(scene);
        this.stage.show();
    }


    /**
     * Draw the board
     */
    public void paintBoard(gameDecorator decorator) {
        // Draw a rectangle around the whole screen

        gc.setStroke(decorator.getColourBackground());
        gc.setFill(decorator.getColourBackground());
        gc.fillRect(0, 0, this.width-1, this.height-1);

    }


}