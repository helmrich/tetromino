package de.hdm_stuttgart.mi.gui.buttons;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.stage.Stage;

public class ExitButton extends ActionButton {

    private static final Logger log = LogManager.getLogger(ExitButton.class.getName());

    public ExitButton() {
        super("Exit");
        setOnAction(__ -> {
            log.debug("Exit Button clicked");
            ((Stage) getScene().getWindow()).close();
            log.info("Exit Tetromino");
        });
    }

}
