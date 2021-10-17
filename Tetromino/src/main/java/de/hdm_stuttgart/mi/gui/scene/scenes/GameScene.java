package de.hdm_stuttgart.mi.gui.scene.scenes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hdm_stuttgart.mi.exceptions.UnassignedKeyException;
import de.hdm_stuttgart.mi.game.Game;
import de.hdm_stuttgart.mi.gui.scene.SceneInterface;
import de.hdm_stuttgart.mi.gui.scene.SceneParent;
import de.hdm_stuttgart.mi.gui.scene.SceneType;
import de.hdm_stuttgart.mi.gui.scene.scenes.gamescenepanes.GameOverPane;
import de.hdm_stuttgart.mi.gui.scene.scenes.gamescenepanes.GamePane;
import de.hdm_stuttgart.mi.gui.scene.scenes.gamescenepanes.PauseMenuPane;
import de.hdm_stuttgart.mi.input.KeyInput;
import de.hdm_stuttgart.mi.game.tetromino.Direction;
import de.hdm_stuttgart.mi.game.tetromino.Rotation;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;

public class GameScene extends SceneParent<StackPane> implements SceneInterface {

    private static final Logger log = LogManager.getLogger(GameScene.class.getName());

    private final Game game;
    private final GamePane gamePane;
    private final PauseMenuPane pauseMenuPane;
    private final GameOverPane gameOverPane;

    public GameScene(double width, double height) {
        super(new StackPane(), width, height);
        game = new Game();
        gamePane = new GamePane(game);
        pauseMenuPane = new PauseMenuPane(game, gamePane, this);
        gameOverPane = new GameOverPane(game, gamePane);
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.GAME;
    }

    @Override
    public void draw() {
        gamePane.draw();
        pauseMenuPane.draw();
        root.getChildren().add(gamePane);
    }

    @Override
    public void addInput() {
        KeyInput keyInput = new KeyInput(this);

        keyInput.addPressedEvent(KeyCode.UP, () -> {
            game.rotateTetrominoIfPossible(Rotation.CLOCKWISE);
        });
        keyInput.addPressedEvent(KeyCode.CONTROL, () -> {
            game.rotateTetrominoIfPossible(Rotation.COUNTERCLOCKWISE);
        });
        keyInput.addRepeatedEvent(KeyCode.RIGHT, () -> {
            try {
                if (!keyInput.isKeyPressed(KeyCode.LEFT)) {
                    game.translateTetrominoIfPossible(Direction.RIGHT);
                }
            } catch (UnassignedKeyException e) {
                log.warn(e);
            }
        }, 250, 50);
        keyInput.addRepeatedEvent(KeyCode.LEFT, () -> {
            try {
                if (!keyInput.isKeyPressed(KeyCode.RIGHT)) {
                    game.translateTetrominoIfPossible(Direction.LEFT);
                }
            } catch (UnassignedKeyException e) {
                log.warn(e);
            }
        }, 250, 50);
        keyInput.addPressedEvent(KeyCode.SHIFT, () -> {
            game.swapTetrominoIfPossible();
        });
        keyInput.addPressedAndReleasedEvent(KeyCode.DOWN, () -> {
            game.startSoftDrop();
        }, () -> {
            game.stopSoftDrop();
        });
        keyInput.addPressedEvent(KeyCode.ENTER, () -> {
            game.hardDrop();
        });
        keyInput.addPressedEvent(KeyCode.ESCAPE, () -> {
            if (game.isGameLoopRunning()) {
                game.stopGameLoop();
            }
            if (!game.isGameOver()) {
                addPauseMenuPane();
            }
        });
    }

    public void addPauseMenuPane() {
        if (!root.getChildren().contains(pauseMenuPane)) {
            root.getChildren().add(pauseMenuPane);
        }
    }

    public void removePauseMenuPane() {
        root.getChildren().removeIf(child -> child.equals(pauseMenuPane));
    }

    public void addGameOverPane() {
        if (!root.getChildren().contains(gameOverPane)) {
            gameOverPane.draw();
            root.getChildren().add(gameOverPane);
        }
    }

}
