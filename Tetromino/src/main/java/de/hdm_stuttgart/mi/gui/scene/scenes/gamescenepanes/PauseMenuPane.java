package de.hdm_stuttgart.mi.gui.scene.scenes.gamescenepanes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hdm_stuttgart.mi.game.Game;
import de.hdm_stuttgart.mi.gui.buttons.ActionButton;
import de.hdm_stuttgart.mi.gui.scene.SceneFactory;
import de.hdm_stuttgart.mi.gui.scene.SceneInterface;
import de.hdm_stuttgart.mi.gui.scene.SceneType;
import de.hdm_stuttgart.mi.gui.scene.scenes.GameScene;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PauseMenuPane extends BorderPane {

    private static final Logger log = LogManager.getLogger(PauseMenuPane.class.getName());

    private final Game game;
    private final GameScene gameScene;
    private final GamePane gamePane;

    public PauseMenuPane(Game game, GamePane gamePane, GameScene gameScene) {
        this.game = game;
        this.gamePane = gamePane;
        this.gameScene = gameScene;
        addStylesheets("menu.css", "overlay.css");
    }

    public void draw() {
        getStyleClass().add("overlay");

        Button resumeButton = new ActionButton("RESUME", () -> {
            log.debug("RESUME Button clicked");
            gameScene.removePauseMenuPane();
            game.startGameLoop(game.getPeriod(), game.getPeriod());
            log.info("Remove pausemenu and resume game");
        });

        Button quitButton = new ActionButton("QUIT", () -> {
            log.debug("QUIT Button clicked");
            gamePane.stopDrawLoop();
            SceneInterface nextScene = SceneFactory.create(SceneType.START_MENU, getScene().getWidth(),
                    getScene().getHeight());
            ((Stage) (getScene().getWindow())).setScene(nextScene.getScene());
            log.info("Quit game and " + SceneType.START_MENU + " set as Scene");
        });

        VBox buttons = new VBox(5.0);
        buttons.getChildren().addAll(resumeButton, quitButton);
        buttons.setAlignment(Pos.CENTER);
        setCenter(buttons);
    }

    private void addStylesheets(String... stylesheets) {
        for (String stylesheet : stylesheets) {
            getStylesheets().add(getClass().getResource("/stylesheets/" + stylesheet).toExternalForm());
        }
    }

}
