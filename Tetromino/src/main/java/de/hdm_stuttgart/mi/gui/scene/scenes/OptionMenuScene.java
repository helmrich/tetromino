package de.hdm_stuttgart.mi.gui.scene.scenes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hdm_stuttgart.mi.datamanager.DataManagerFactory;
import de.hdm_stuttgart.mi.datamanager.GameSpeedManager;
import de.hdm_stuttgart.mi.datamanager.HighscoreListManager;
import de.hdm_stuttgart.mi.game.score.HighscoreList;
import de.hdm_stuttgart.mi.gui.buttons.ActionButton;
import de.hdm_stuttgart.mi.gui.buttons.BackButton;
import de.hdm_stuttgart.mi.gui.scene.SceneInterface;
import de.hdm_stuttgart.mi.gui.scene.SceneParent;
import de.hdm_stuttgart.mi.gui.scene.SceneType;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OptionMenuScene extends SceneParent<BorderPane> implements SceneInterface {

    private static final Logger log = LogManager.getLogger(OptionMenuScene.class.getName());

    public OptionMenuScene(double width, double height) {
        super(new BorderPane(), width, height);
        addStylesheets("menu.css");
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.OPTION_MENU;
    }

    @Override
    public void draw() {
        root.getStyleClass().add("menu");

        root.setTop(new BackButton(SceneType.START_MENU));

        VBox containerVBox = new VBox(10.0);

        Label titleLabel = new Label("Options");
        titleLabel.getStyleClass().add("menu-heading");

        VBox settings = new VBox(20.0);

        HBox gameSpeedContainer = new HBox(20.0);
        Label gameSpeedLabel = new Label("Game Speed");
        gameSpeedLabel.setTooltip(new Tooltip("Change the game speed from slow (left) to fast (right)"));
        gameSpeedLabel.setPrefWidth(200.0);

        GameSpeedManager gameSpeedManager = DataManagerFactory
                .createGameSpeedManager("GameSpeedUserPreferencesManager");

        int currentGameSpeedPreference = 1200 - gameSpeedManager.loadData();

        log.debug("Current game speed: " + currentGameSpeedPreference);

        HBox gameSpeedSliderContainer = new HBox(10.0);
        gameSpeedSliderContainer.setAlignment(Pos.CENTER);
        gameSpeedSliderContainer.setPrefWidth(250.0);
        Slider gameSpeedSlider = new Slider(100.0, 1100.0, currentGameSpeedPreference);
        gameSpeedSlider.setPrefWidth(130.0);
        gameSpeedSlider.setMajorTickUnit(250);
        gameSpeedSlider.setBlockIncrement(250);
        gameSpeedSlider.setMinorTickCount(0);
        gameSpeedSlider.setShowTickMarks(true);
        gameSpeedSlider.setSnapToTicks(true);

        Label gameSpeedIndicatorLabel = new Label();
        gameSpeedIndicatorLabel.getStyleClass().add("game-speed-indicator-label");

        if (gameSpeedManager.loadData() == 1100) {
            gameSpeedIndicatorLabel.setText("Sane");
        } else if (gameSpeedManager.loadData() == 850) {
            gameSpeedIndicatorLabel.setText("Low");
        } else if (gameSpeedManager.loadData() == 600){
            gameSpeedIndicatorLabel.setText("Medium");
        }  else if (gameSpeedManager.loadData() == 350) {
            gameSpeedIndicatorLabel.setText("High");
        } else {
            gameSpeedIndicatorLabel.setText("Insane");
        }

        gameSpeedSlider.valueProperty().addListener(__ -> {

            int newGameSpeed = 1200 - (int) gameSpeedSlider.getValue();

            log.debug("Changing gameSpeed setting to " + newGameSpeed);

            gameSpeedManager.saveData(newGameSpeed);

            if (gameSpeedManager.loadData() >= 975) {
                gameSpeedIndicatorLabel.setText("Sane");
            } else if (gameSpeedManager.loadData() >= 725) {
                gameSpeedIndicatorLabel.setText("Low");
            } else if (gameSpeedManager.loadData() >= 475){
                gameSpeedIndicatorLabel.setText("Medium");
            }  else if (gameSpeedManager.loadData() >= 225) {
                gameSpeedIndicatorLabel.setText("High");
            } else {
                gameSpeedIndicatorLabel.setText("Insane");
            }
        });

        gameSpeedSliderContainer.getChildren().addAll(gameSpeedSlider, gameSpeedIndicatorLabel);

        gameSpeedContainer.getChildren().addAll(gameSpeedLabel, gameSpeedSliderContainer);
        gameSpeedContainer.setAlignment(Pos.CENTER);

        HBox resetHighscoresContainer = new HBox(20.0);
        Label resetHighscoreLabel = new Label("Reset Highscores");
        resetHighscoreLabel.setTooltip(new Tooltip("Reset the current highscore list"));
        resetHighscoreLabel.setPrefWidth(200.0);

        ActionButton resetHighscoresButton = new ActionButton("Reset", () -> {
            HighscoreListManager highscoreListManager = DataManagerFactory
                    .createHighscoreListManager("HighscoreListUserPreferencesManager");
            highscoreListManager.saveData(new HighscoreList());
        });
        resetHighscoresButton.getStyleClass().addAll("menu-button", "reset-button");

        resetHighscoresContainer.getChildren().addAll(resetHighscoreLabel, resetHighscoresButton);
        resetHighscoresContainer.setAlignment(Pos.CENTER);

        settings.getChildren().addAll(gameSpeedContainer, resetHighscoresContainer);
        settings.setAlignment(Pos.CENTER);

        containerVBox.getChildren().addAll(titleLabel, settings);
        containerVBox.setAlignment(Pos.CENTER);
        root.setCenter(containerVBox);
    }

    @Override
    public void addInput() {
    }

}
