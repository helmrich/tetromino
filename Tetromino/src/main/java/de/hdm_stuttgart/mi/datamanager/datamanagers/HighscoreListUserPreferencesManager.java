package de.hdm_stuttgart.mi.datamanager.datamanagers;

import java.lang.reflect.Type;
import java.util.prefs.Preferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hdm_stuttgart.mi.datamanager.HighscoreListManager;
import de.hdm_stuttgart.mi.game.score.HighscoreList;

public class HighscoreListUserPreferencesManager implements HighscoreListManager {

	private static final Logger log = LogManager.getLogger(HighscoreListUserPreferencesManager.class.getName());

	@Override
	public void saveData(HighscoreList data) {
		Gson gson = new Gson();

		String newJson = gson.toJson(data);

		Preferences.userRoot().put("highscoreListJson", newJson);

		log.trace("Successfully saved highscore list in user preferences");
	}

	@Override
	public HighscoreList loadData() {
		Gson gson = new Gson();

		String jsonString = Preferences.userRoot().get("highscoreListJson", "NO JSON FOUND");

		if (jsonString == "NO JSON FOUND") {
			log.info("No highscore list JSON available, returning empty highscore list");
			return new HighscoreList();
		}

		Type type = new TypeToken<HighscoreList>() {
		}.getType();

		HighscoreList highscoreList = gson.fromJson(Preferences.userRoot().get("highscoreListJson", "NO JSON FOUND"),
				type);

		log.trace("Successfully loaded highscore list with " + highscoreList.getHighscores().size() + " scores");

		return highscoreList;
	}

}
