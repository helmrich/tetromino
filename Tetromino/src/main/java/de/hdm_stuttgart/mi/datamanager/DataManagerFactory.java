package de.hdm_stuttgart.mi.datamanager;

import de.hdm_stuttgart.mi.datamanager.datamanagers.GameSpeedUserPreferencesManager;
import de.hdm_stuttgart.mi.datamanager.datamanagers.HighscoreListUserPreferencesManager;

public class DataManagerFactory {

    public static HighscoreListManager createHighscoreListManager(String type) {
        switch (type) {
            case "HighscoreListUserPreferencesManager":
                return new HighscoreListUserPreferencesManager();
            default:
                return null;
        }
    }

    public static GameSpeedManager createGameSpeedManager(String type) {
        switch (type) {
            case "GameSpeedUserPreferencesManager":
                return new GameSpeedUserPreferencesManager();
            default:
                return null;
        }
    }

}
