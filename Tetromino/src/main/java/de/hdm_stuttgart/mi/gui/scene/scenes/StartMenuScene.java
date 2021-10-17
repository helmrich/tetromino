package de.hdm_stuttgart.mi.gui.scene.scenes;

import de.hdm_stuttgart.mi.gui.buttons.ExitButton;
import de.hdm_stuttgart.mi.gui.buttons.LinkButton;
import de.hdm_stuttgart.mi.gui.scene.SceneInterface;
import de.hdm_stuttgart.mi.gui.scene.SceneParent;
import de.hdm_stuttgart.mi.gui.scene.SceneType;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class StartMenuScene extends SceneParent<BorderPane> implements SceneInterface {

    public StartMenuScene(double width, double height) {
        super(new BorderPane(), width, height);
        addStylesheets("menu.css");
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.START_MENU;
    }

    @Override
    public void draw() {
        root.getStyleClass().add("menu");

        VBox containerVBox = new VBox(20.0);

        VBox gameTitleVBox = new VBox(-20.0);
        Label titleLabel = new Label("Tetromino");
        Label subtitleLabel = new Label(
                "Magical Attack of the Doom Kingdom\nand the Final Battle of the Child Emperor\nThe Dark Awakening of the Dragonslayer\nTerror Instinct\n- Michelangelo Edition -");
        titleLabel.getStyleClass().addAll("menu-heading", "game-title-heading");
        subtitleLabel.getStyleClass().addAll("menu-heading", "subtitle");
        gameTitleVBox.getChildren().addAll(titleLabel, subtitleLabel);
        gameTitleVBox.setAlignment(Pos.CENTER);

        VBox buttons = new VBox(20.0);
        buttons.getChildren().addAll(
                new LinkButton("Start", SceneType.GAME),
                new LinkButton("Options", SceneType.OPTION_MENU),
                new LinkButton("Highscores", SceneType.HIGHSCORE_LIST), new ExitButton());
        buttons.setAlignment(Pos.CENTER);

        containerVBox.getChildren().addAll(gameTitleVBox, buttons);
        containerVBox.setAlignment(Pos.CENTER);
        root.setCenter(containerVBox);
    }

    @Override
    public void addInput() {
    }

}
