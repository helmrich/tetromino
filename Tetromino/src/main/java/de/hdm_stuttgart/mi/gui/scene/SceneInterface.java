package de.hdm_stuttgart.mi.gui.scene;

import javafx.scene.Scene;

public interface SceneInterface {

    Scene getScene();

    SceneType getSceneType();

    void draw();

    void addInput();

}
