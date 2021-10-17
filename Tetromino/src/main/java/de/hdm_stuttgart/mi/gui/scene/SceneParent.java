package de.hdm_stuttgart.mi.gui.scene;

import javafx.scene.Parent;
import javafx.scene.Scene;

public class SceneParent<T extends Parent> extends Scene {

    protected T root;

    public SceneParent(T root, double width, double height) {
        super(root, width, height);
        this.root = root;
    }

    public Scene getScene() {
        return this;
    }

    protected void addStylesheets(String... stylesheets) {
        for (String stylesheet : stylesheets) {
            getStylesheets().add(getClass().getResource("/stylesheets/" + stylesheet).toExternalForm());
        }
    }

}
