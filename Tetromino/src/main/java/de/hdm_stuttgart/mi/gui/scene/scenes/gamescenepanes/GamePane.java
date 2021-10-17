package de.hdm_stuttgart.mi.gui.scene.scenes.gamescenepanes;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hdm_stuttgart.mi.game.field.Field;
import de.hdm_stuttgart.mi.game.field.PlayField;
import de.hdm_stuttgart.mi.gui.scene.scenes.GameScene;
import de.hdm_stuttgart.mi.game.Game;
import de.hdm_stuttgart.mi.game.tetromino.Tetromino;
import de.hdm_stuttgart.mi.game.tetromino.Vector2D;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class GamePane extends BorderPane {

    private static final Logger log = LogManager.getLogger(GamePane.class.getName());

    private final Game game;
    private AnimationTimer drawLoop;
    private double blockSize;

    public GamePane(Game game) {
        this.game = game;
        addStylesheets("game.css");
    }

    public void draw() {
        setId("root");
        getStyleClass().add("menu");

        VBox container = new VBox();
        HBox fields = new HBox();
        VBox holdFieldVBox = new VBox();
        VBox nextFieldVBox = new VBox();
        VBox scoreVBox = new VBox(); //-15.0

        Rectangle spacer = new Rectangle();
        spacer.setId("spacer");

        Pane holdFieldNode = new Pane();
        holdFieldNode.setId("holdfield");
        Pane nextFieldNode = new Pane();
        nextFieldNode.setId("nextfield");
        Pane playFieldNode = new Pane();
        playFieldNode.setId("playfield");

        Pane playFieldBlocksNode = new Pane();
        Pane tetrominoNode = new Pane();
        Pane shadowNode = new Pane();
        Pane holdTetrominoNode = new Pane();
        List<Pane> nextTetrominoNodes = new ArrayList<>();
        for (int i = 0; i < game.getNextField().getTetrominos().size(); i++) {
            nextTetrominoNodes.add(new Pane());
        }

        Label scoreLabel = new Label("SCORE:");
        scoreLabel.getStyleClass().add("score-label");
        Label scoreValueLabel = new Label();
        scoreValueLabel.getStyleClass().add("score-label");
        Label nextLabel = new Label("NEXT");
        nextLabel.getStyleClass().add("score-label");
        Label holdLabel = new Label("HOLD");
        holdLabel.getStyleClass().add("score-label");

        container.getChildren().addAll(spacer, fields);
        spacer.setViewOrder(0);
        fields.setViewOrder(1);
        fields.getChildren().addAll(holdFieldVBox, playFieldNode, nextFieldVBox);
        holdFieldVBox.getChildren().addAll(holdLabel, holdFieldNode, scoreVBox);
        nextFieldVBox.getChildren().addAll(nextLabel, nextFieldNode);
        scoreVBox.getChildren().addAll(scoreLabel, scoreValueLabel);

        holdFieldNode.getChildren().add(holdTetrominoNode);
        nextFieldNode.getChildren().addAll(nextTetrominoNodes);
        playFieldNode.getChildren().addAll(shadowNode, tetrominoNode, playFieldBlocksNode);

        container.setAlignment(Pos.TOP_CENTER);
        fields.setAlignment(Pos.TOP_CENTER);
        setCenter(container);

        drawLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                blockSize = getScene().getHeight() / 24;

                spacer.setHeight(blockSize * 2);
                spacer.setWidth(blockSize * 10);

                holdLabel.setStyle("-fx-font-size: " + blockSize * 2);
                nextLabel.setStyle("-fx-font-size: " + blockSize * 2);
                holdLabel.setPadding(new Insets(- blockSize / 4, 0, - blockSize / 4, 0));
                nextLabel.setPadding(new Insets(- blockSize / 4, 0, - blockSize / 4, 0));

                scoreLabel.setStyle("-fx-font-size: " + blockSize * 1.5);
                scoreValueLabel.setStyle("-fx-font-size: " + blockSize * 1.5);
                scoreLabel.setPadding(new Insets(- blockSize / 8, 0, - blockSize / 4, 0));
                scoreValueLabel.setPadding(new Insets(- blockSize / 3, 0, - blockSize / 4, 0));

                fields.setSpacing(blockSize / 4);

                drawField(game.getHoldField(), holdFieldNode);
                drawField(game.getPlayField(), playFieldNode);
                drawField(game.getNextField(), nextFieldNode);

                drawTetromino(game.getPlayField().getShadow(), shadowNode);
                drawTetromino(game.getPlayField().getTetromino(), tetrominoNode);

                for (int i = 0; i < game.getNextField().getTetrominos().size(); i++) {
                    drawTetromino(game.getNextField().getTetrominos().get(i), nextTetrominoNodes.get(i));
                }

                if (game.getHoldField().getTetromino() != null) {
                    drawTetromino(game.getHoldField().getTetromino(), holdTetrominoNode);
                }

                drawPlayFieldBlocks(game.getPlayField(), playFieldBlocksNode);

                scoreValueLabel.setText("" + game.getScore().getPoints());

                // When the game is over, add a GameOverRoot to the GameScene
                if (game.isGameOver()) {
                    ((GameScene) getScene()).addGameOverPane();
                }
            }
        };
        startDrawLoop();
    }

    private void addStylesheets(String... stylesheets) {
        for (String stylesheet : stylesheets) {
            getStylesheets().add(getClass().getResource("/stylesheets/" + stylesheet).toExternalForm());
        }
    }

    public void startDrawLoop() {
        drawLoop.start();
        log.debug("DrawLoop started");
    }

    public void stopDrawLoop() {
        drawLoop.stop();
        log.debug("DrawLoop stopped");
    }

    private void drawField(Field field, Pane pane) {
        pane.setMaxSize(field.getWidth() * blockSize, field.getHeight() * blockSize);
        pane.setMinSize(field.getWidth() * blockSize, field.getHeight() * blockSize);
    }

    private void drawPlayFieldBlocks(PlayField playField, Pane pane) {
        pane.getChildren().clear();
        double gap = blockSize / 12;

        for (int row = 0; row < playField.getHeight(); row++) {
            for (int col = 0; col < playField.getWidth(); col++) {
                if (playField.getCell(col, row) != null) {
                    Rectangle block = new Rectangle(col * blockSize + (gap / 2), row * blockSize + (gap / 2),
                            blockSize - gap, blockSize - gap);
                    block.getStyleClass().add(playField.getCell(col, row).toString().toLowerCase());
                    pane.getChildren().add(block);
                }
            }
        }
    }

    private void drawTetromino(Tetromino tetromino, Pane pane) {
        double gap = blockSize / 12;
        List<List<Integer>> shape = tetromino.getShapeForCurrentRotation();
        Vector2D position = tetromino.getTopLeftCornerPosition();

        pane.getChildren().clear();
        pane.relocate(position.getX() * blockSize, position.getY() * blockSize);

        for (int row = 0; row < shape.size(); row++) {
            for (int col = 0; col < shape.get(row).size(); col++) {
                if (shape.get(row).get(col) != 0) {
                    Rectangle block = new Rectangle(col * blockSize + (gap / 2), row * blockSize + (gap / 2),
                            blockSize - gap, blockSize - gap);
                    block.getStyleClass().add(tetromino.getColor().toString().toLowerCase());
                    pane.getChildren().add(block);
                }
            }
        }
    }

}
