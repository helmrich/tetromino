package de.hdm_stuttgart.mi.datamanager.datamanagers;

import java.util.prefs.Preferences;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hdm_stuttgart.mi.datamanager.GameSpeedManager;

public class GameSpeedUserPreferencesManager implements GameSpeedManager {

    private static final Logger log = LogManager.getLogger(GameSpeedUserPreferencesManager.class.getName());

    @Override
    public void saveData(Integer data) {
        log.trace("Saving game speed " + data);
        Preferences.userRoot().putInt("gameSpeed", data);
    }

    @Override
    public Integer loadData() {
        log.trace("Loaded game speed " + Preferences.userRoot().getInt("gameSpeed", 600));
        return Preferences.userRoot().getInt("gameSpeed", 600);
    }

}
