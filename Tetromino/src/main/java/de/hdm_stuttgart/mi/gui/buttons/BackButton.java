package de.hdm_stuttgart.mi.gui.buttons;

import de.hdm_stuttgart.mi.gui.scene.SceneType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;

public class BackButton extends LinkButton {

    public BackButton(SceneType sceneType) {
        super("< Back", sceneType);
        getStyleClass().add("back-button");
        BorderPane.setAlignment(this, Pos.TOP_LEFT);
        BorderPane.setMargin(this, new Insets(20.0, 0.0, 0.0, 20.0));
    }

}
