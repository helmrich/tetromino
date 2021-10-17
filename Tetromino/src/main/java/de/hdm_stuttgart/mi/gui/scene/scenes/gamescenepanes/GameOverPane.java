package de.hdm_stuttgart.mi.gui.scene.scenes.gamescenepanes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hdm_stuttgart.mi.datamanager.DataManagerFactory;
import de.hdm_stuttgart.mi.datamanager.HighscoreListManager;
import de.hdm_stuttgart.mi.game.Game;
import de.hdm_stuttgart.mi.game.score.HighscoreList;
import de.hdm_stuttgart.mi.game.score.Score;
import de.hdm_stuttgart.mi.gui.buttons.ActionButton;
import de.hdm_stuttgart.mi.gui.buttons.LinkButton;
import de.hdm_stuttgart.mi.gui.scene.SceneFactory;
import de.hdm_stuttgart.mi.gui.scene.SceneInterface;
import de.hdm_stuttgart.mi.gui.scene.SceneType;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameOverPane extends BorderPane {

    private static final Logger log = LogManager.getLogger(GameOverPane.class.getName());

    private final Game game;
    private final GamePane gamePane;

    public GameOverPane(Game game, GamePane gamePane) {
        this.game = game;
        this.gamePane = gamePane;
        addStylesheets("menu.css", "gameOver.css", "overlay.css");
    }

    public void draw() {
        getStyleClass().add("overlay");

        VBox containerVBox = new VBox(10.0);
        containerVBox.getStyleClass().add("container");

        Label gameOverLabel = new Label("GAME OVER!");
        gameOverLabel.getStyleClass().add("game-over-heading");

        HBox scoreContainerHBox = new HBox(0.0);
        Label scoreLabel = new Label("Your Score:");
        scoreLabel.getStyleClass().add("game-over-label");
        scoreLabel.setPrefWidth(200.0);
        Label scoreValueLabel = new Label("" + game.getScore().getPoints());
        scoreValueLabel.getStyleClass().add("game-over-value-label");
        scoreContainerHBox.getChildren().addAll(scoreLabel, scoreValueLabel);
        scoreContainerHBox.setAlignment(Pos.CENTER);

        HBox nameContainerHBox = new HBox(0.0);
        Label nameLabel = new Label("Your Name:");
        nameLabel.getStyleClass().add("game-over-label");
        TextField nameTextField = new TextField();
        nameTextField.getStyleClass().add("name-input");
        nameContainerHBox.getChildren().addAll(nameLabel, nameTextField);
        nameContainerHBox.setAlignment(Pos.CENTER);

        HBox buttonContainer = new HBox(20.0);

        Button confirmButton = new ActionButton("Confirm", () -> {
            if (nameTextField.getText().isEmpty()) {
                nameTextField.setPromptText("Please enter a name!");
                nameTextField.requestFocus();
                return;
            }

            if (nameTextField.getText().length() > 20) {
                nameTextField.setText("");
                nameTextField.setPromptText("Enter a name with max. 20 characters!");
                nameTextField.requestFocus();
                return;
            }

            gamePane.stopDrawLoop();
            Score scoreToSubmit = new Score(nameTextField.getText(), game.getScore().getPoints());

            HighscoreListManager highscoreListManager = DataManagerFactory
                    .createHighscoreListManager("HighscoreListUserPreferencesManager");
            HighscoreList highscoreList = highscoreListManager.loadData();
            highscoreList.addScore(scoreToSubmit);
            highscoreListManager.saveData(highscoreList);

            SceneInterface nextScene = SceneFactory.create(SceneType.HIGHSCORE_LIST, getScene().getWidth(),
                    getScene().getHeight());
            ((Stage) (getScene().getWindow())).setScene(nextScene.getScene());
            log.info(SceneType.HIGHSCORE_LIST + " set as Scene");
        });
        confirmButton.getStyleClass().add("game-over-confirm-button");

        Button startMenuButton = new LinkButton("Start Menu", SceneType.START_MENU);
        startMenuButton.getStyleClass().add("game-over-button");

        buttonContainer.getChildren().addAll(confirmButton, startMenuButton);
        buttonContainer.setAlignment(Pos.CENTER);

        containerVBox.getChildren().addAll(gameOverLabel, scoreContainerHBox, nameContainerHBox, buttonContainer);
        containerVBox.setAlignment(Pos.CENTER);

        setCenter(containerVBox);
    }

    private void addStylesheets(String... stylesheets) {
        for (String stylesheet : stylesheets) {
            getStylesheets().add(getClass().getResource("/stylesheets/" + stylesheet).toExternalForm());
        }
    }
}
