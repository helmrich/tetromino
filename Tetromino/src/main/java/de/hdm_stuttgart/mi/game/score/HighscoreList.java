package de.hdm_stuttgart.mi.game.score;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HighscoreList {

    private static final Logger log = LogManager.getLogger(HighscoreList.class.getName());

    private List<Score> highscores = new ArrayList<>();
    private final int size = 10;

    public List<Score> getHighscores() {
        return highscores.stream().map(score -> score.copy()).collect(Collectors.toList());
    }

    public void addScore(Score score) {
        // Get highest score that is smaller than the score to add
        Score insertScore = highscores.parallelStream()
                                      .filter(highscore -> highscore.getPoints() < score.getPoints())
                                      .max(Comparator.comparing(highscore -> highscore.getPoints()))
                                      .orElse(null);
        if (insertScore != null) {
            // insert score before the highest score that is smaller than the score to add
            highscores.add(highscores.indexOf(insertScore), score);
            log.trace("Score \"" + score + "\" added");
            // trim list to size
            if (highscores.size() > size) {
                log.trace("Last score \"" + highscores.get(size) + "\"  removed, only " + size + " scores are allowed in the highscore list");
                highscores.remove(size);
            }
        } else if (highscores.size() < size) {
            // insert score at the end of list
            highscores.add(score);
            log.trace("Score \"" + score + "\" added");
        } else {
            log.trace("Score \"" + score + "\" not added, because it's to low and only " + size + " scores are allowed in the highscore list");
        }
    }

}
