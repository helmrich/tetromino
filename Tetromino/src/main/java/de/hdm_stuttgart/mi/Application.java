package de.hdm_stuttgart.mi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hdm_stuttgart.mi.gui.scene.SceneFactory;
import de.hdm_stuttgart.mi.gui.scene.SceneInterface;
import de.hdm_stuttgart.mi.gui.scene.SceneType;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Main app
 */
public class Application extends javafx.application.Application {

    private static final Logger log = LogManager.getLogger(Application.class.getName());

    /**
     * Run main app
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Show main app
     *
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {

        stage.getIcons().addAll(new Image(getClass().getResourceAsStream("/images/icon_128x128.png")),
                                new Image(getClass().getResourceAsStream("/images/icon_64x64.png")),
                                new Image(getClass().getResourceAsStream("/images/icon_32x32.png")));
        log.trace("All icons set");

        if (null != Font.loadFont(getClass().getResourceAsStream("/fonts/AsepriteFont.ttf"), 16)){
            log.trace("Font loaded");
        } else {
            log.warn("Font not found");
        }
        
        stage.setTitle("Tetromino");

        SceneInterface scene = SceneFactory.create(SceneType.START_MENU, 854, 480);
        stage.setScene(scene.getScene());
        log.info(SceneType.START_MENU + " set as Scene");
        stage.show();

        stage.setMinHeight(stage.getHeight());
        stage.setMinWidth(stage.getHeight());
        stage.heightProperty().addListener((observable, oldValue, newValue) -> {
            // set min aspect ratio of stage to 1:1
            stage.setMinWidth(newValue.doubleValue());
        });

        log.trace("Initial stage setup complete");
    }

}
