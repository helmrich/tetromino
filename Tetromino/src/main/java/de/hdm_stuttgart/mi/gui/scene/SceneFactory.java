package de.hdm_stuttgart.mi.gui.scene;

public class SceneFactory {

    public static SceneInterface create(SceneType sceneType, double width, double height) {
        SceneInterface scene = sceneType.getInstance(width, height);
        scene.draw();
        scene.addInput();
        return scene;
    }

}
