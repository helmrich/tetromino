package de.hdm_stuttgart.mi.gui.scene;

import de.hdm_stuttgart.mi.gui.scene.scenes.GameScene;
import de.hdm_stuttgart.mi.gui.scene.scenes.HighscoreListScene;
import de.hdm_stuttgart.mi.gui.scene.scenes.OptionMenuScene;
import de.hdm_stuttgart.mi.gui.scene.scenes.StartMenuScene;

public enum SceneType {

    GAME {
        @Override
        public GameScene getInstance(double width, double height) {
            return new GameScene(width, height);
        }
    },
    HIGHSCORE_LIST {
        @Override
        public HighscoreListScene getInstance(double width, double height) {
            return new HighscoreListScene(width, height);
        }
    },
    OPTION_MENU {
        @Override
        public OptionMenuScene getInstance(double width, double height) {
            return new OptionMenuScene(width, height);
        }
    },
    START_MENU {
        @Override
        public StartMenuScene getInstance(double width, double height) {
            return new StartMenuScene(width, height);
        }
    };

    public abstract SceneInterface getInstance(double width, double height);
    
}
