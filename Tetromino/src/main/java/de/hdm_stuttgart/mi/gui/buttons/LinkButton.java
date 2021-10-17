package de.hdm_stuttgart.mi.gui.buttons;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hdm_stuttgart.mi.gui.scene.SceneFactory;
import de.hdm_stuttgart.mi.gui.scene.SceneInterface;
import de.hdm_stuttgart.mi.gui.scene.SceneType;
import javafx.stage.Stage;

public class LinkButton extends ActionButton {

    private static final Logger log = LogManager.getLogger(LinkButton.class.getName());

    public LinkButton(String label, SceneType sceneType) {
        super(label);
        setOnAction(__ -> {
            log.debug(label + " Button clicked");
            SceneInterface nextScene = SceneFactory.create(sceneType, getScene().getWidth(), getScene().getHeight());
            ((Stage) (getScene().getWindow())).setScene(nextScene.getScene());
            log.info(sceneType + " set as Scene");
        });
    }

}
