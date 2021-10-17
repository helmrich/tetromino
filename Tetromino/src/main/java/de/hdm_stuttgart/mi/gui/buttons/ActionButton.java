package de.hdm_stuttgart.mi.gui.buttons;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.scene.control.Button;

public class ActionButton extends Button {

    private static final Logger log = LogManager.getLogger(ActionButton.class.getName());

    public ActionButton(String label, Runnable onAction) {
        setText(label);
        getStyleClass().addAll("button", "menu-button");
        setOnAction(__ -> {
            log.debug(label + " Button clicked");
            onAction.run();
        });
    }

    protected ActionButton(String label) {
        setText(label);
        getStyleClass().addAll("button", "menu-button");
    }

}
