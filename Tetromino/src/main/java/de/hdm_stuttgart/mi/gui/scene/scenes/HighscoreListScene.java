package de.hdm_stuttgart.mi.gui.scene.scenes;

import java.util.ArrayList;
import java.util.List;

import de.hdm_stuttgart.mi.datamanager.DataManagerFactory;
import de.hdm_stuttgart.mi.datamanager.HighscoreListManager;
import de.hdm_stuttgart.mi.game.score.HighscoreList;
import de.hdm_stuttgart.mi.gui.buttons.BackButton;
import de.hdm_stuttgart.mi.gui.scene.SceneInterface;
import de.hdm_stuttgart.mi.gui.scene.SceneParent;
import de.hdm_stuttgart.mi.gui.scene.SceneType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HighscoreListScene extends SceneParent<BorderPane> implements SceneInterface {

    private HighscoreList highscoreList;

    public HighscoreListScene(double width, double height) {
        super(new BorderPane(), width, height);
        addStylesheets("menu.css", "highscoreList.css");

        HighscoreListManager highscoreListManager = DataManagerFactory
                .createHighscoreListManager("HighscoreListUserPreferencesManager");
        highscoreList = highscoreListManager.loadData();
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.HIGHSCORE_LIST;
    }

    @Override
    public void draw() {
        root.getStyleClass().add("menu");

        BackButton backButton = new BackButton(SceneType.START_MENU);
        root.setTop(backButton);
        BorderPane.setAlignment(backButton, Pos.TOP_LEFT);

        VBox containerVBox = new VBox(-10.0);

        Label titleLabel = new Label("Highscores");
        titleLabel.getStyleClass().add("menu-heading");

        VBox highscoresVBox = new VBox(0.0);
        highscoresVBox.getStyleClass().add("highscore-list");

        List<HBox> highscoreHBoxes = new ArrayList<>();

        for (int i = 0; i < 11; i++) {
            HBox highscoreHBox = new HBox(0.0);
            highscoreHBox.getStyleClass().add("highscore-box");
            highscoreHBox.setAlignment(Pos.CENTER);

            if (i % 2 == 0) {
                highscoreHBox.getStyleClass().add("light-background");
            } else {
                highscoreHBox.getStyleClass().add("dark-background");
            }

            highscoreHBoxes.add(highscoreHBox);
            highscoresVBox.getChildren().add(highscoreHBox);
        }

        Label nameListHeading = new Label("Name");
        nameListHeading.getStyleClass().add("heading-label");
        nameListHeading.setAlignment(Pos.CENTER);
        Label scoreListHeading = new Label("Score");
        scoreListHeading.getStyleClass().add("heading-label");
        scoreListHeading.setAlignment(Pos.CENTER);
        highscoreHBoxes.get(0).getStyleClass().add("list-heading");
        highscoreHBoxes.get(0).getChildren().addAll(nameListHeading, scoreListHeading);

        for (int i = 0; i < highscoreList.getHighscores().size(); i++) {
            Label positionLabel = new Label(String.valueOf(i + 1) + ".");
            positionLabel.getStyleClass().add("position-label");
            positionLabel.setAlignment(Pos.CENTER_LEFT);

            Label nameLabel = new Label(highscoreList.getHighscores().get(i).getName());
            nameLabel.getStyleClass().add("name-label");
            nameLabel.setAlignment(Pos.CENTER);
            nameLabel.setPadding(new Insets(0, 0, 0, -20));

            Label scoreLabel = new Label(Long.toString(highscoreList.getHighscores().get(i).getPoints()));
            scoreLabel.getStyleClass().add("score-label");
            scoreLabel.setAlignment(Pos.CENTER);
            scoreLabel.setPadding(new Insets(0, 0, 0, -20));

            highscoreHBoxes.get(i + 1).getChildren().addAll(positionLabel, nameLabel, scoreLabel);
        }

        containerVBox.getChildren().addAll(titleLabel, highscoresVBox);
        containerVBox.setAlignment(Pos.CENTER);
        root.setCenter(containerVBox);

    }

    @Override
    public void addInput() {
    }

}
