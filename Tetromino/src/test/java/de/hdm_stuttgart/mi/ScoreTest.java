package de.hdm_stuttgart.mi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import de.hdm_stuttgart.mi.exceptions.InvalidNumberOfClearedLinesException;
import de.hdm_stuttgart.mi.game.score.Score;
import de.hdm_stuttgart.mi.game.score.ScoreType;

/**
 * Unit test for {@code Score}.
 */
public class ScoreTest {
    @Test
    public void testIncreaseWithMultiplier() {
        Score score = new Score();

        score.increase(ScoreType.SOFT_DROP, 10);
        assertEquals(10, score.getPoints());

        score.increase(ScoreType.HARD_DROP, 15);
        assertEquals(40, score.getPoints());
    }

    @Test
    public void testLineIncrease() {
        Score score = new Score();
        assertEquals(0, score.getPoints());

        try {
            score.lineIncrease(1);
            assertEquals(100, score.getPoints());

            score.lineIncrease(2);
            assertEquals(400, score.getPoints());

            score.lineIncrease(3);
            assertEquals(900, score.getPoints());

            score.lineIncrease(4);
            assertEquals(1700, score.getPoints());
        } catch (InvalidNumberOfClearedLinesException e) {
            return;
        }
    }

    @Test
    public void negativeTestLineIncreaseWithInvalidLineNumber() {
        Score score = new Score();

        try {
            score.lineIncrease(5);
        } catch (InvalidNumberOfClearedLinesException e) {
            assertEquals(0, score.getPoints());
        }
    }

    @Test
    public void testInvalidNumberOfClearedLinesException() {
        Score score = new Score();
        Throwable exception = assertThrows(InvalidNumberOfClearedLinesException.class, () -> score.lineIncrease(5));
        assertEquals("Invalid number of cleared lines: 5", exception.getMessage());
    }

}
