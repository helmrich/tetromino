package de.hdm_stuttgart.mi.game.score;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hdm_stuttgart.mi.exceptions.InvalidNumberOfClearedLinesException;

/**
 * The {@code Score} class stores the associated {@code name} and {@code points}
 * a player scores when playing the game
 */
public class Score {

    private static final Logger log = LogManager.getLogger(Score.class.getName());

    private String name;
    private long points;

    /**
     * Creates an empty {@code Score} object with no name and 0 points
     */
    public Score() {
        points = 0;
    }

    /**
     * Creates a {@code Score} object with a specified name and an amount of points
     * 
     * @param name   The player's name.
     * @param points The points the player scored.
     */
    public Score(String name, long points) {
        this.name = name;
        this.points = points;
    }

    /**
     * Creates a copy of the {@code Score} object
     * 
     * @return A copy of the {@code Score} object
     */
    public Score copy() {
        return new Score(name, points);
    }

    /**
     * Gets the {@code name} of the player
     * 
     * @return {@code name}
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the {@code name} of the player
     * 
     * @param name Name of the player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the {@code points} of the player
     * 
     * @return {@code points}
     */
    public long getPoints() {
        return points;
    }

    public void setPoints(long score) {
        this.points = score;
    }

    public void increase(ScoreType scoreType) {
        log.trace("Increasing score by " + scoreType.getPoints());
        points += scoreType.getPoints();
        log.trace("New total score: " + points);
    }

    public void increase(ScoreType scoreType, double multiplier) {
        log.trace("Increasing score by " + scoreType.getPoints() * multiplier);
        points += scoreType.getPoints() * multiplier;
        log.trace("New total score: " + points);
    }

    public void lineIncrease(int numberOfClearedLines) throws InvalidNumberOfClearedLinesException {
        switch (numberOfClearedLines) {
            case 0:
                break;
            case 1:
                increase(ScoreType.SINGLE);
                break;
            case 2:
                increase(ScoreType.DOUBLE);
                break;
            case 3:
                increase(ScoreType.TRIPLE);
                break;
            case 4:
                increase(ScoreType.TETRIS);
                break;
            default:
                throw new InvalidNumberOfClearedLinesException(numberOfClearedLines);
        }
    }

    @Override
    public String toString() {
        return name + ": " + points;
    }

}
