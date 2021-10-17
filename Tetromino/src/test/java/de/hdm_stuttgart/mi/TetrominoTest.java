package de.hdm_stuttgart.mi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.hdm_stuttgart.mi.game.tetromino.Direction;
import de.hdm_stuttgart.mi.game.tetromino.Rotation;
import de.hdm_stuttgart.mi.game.tetromino.Shape;
import de.hdm_stuttgart.mi.game.tetromino.Tetromino;
import de.hdm_stuttgart.mi.game.tetromino.Vector2D;

/**
 * Unit test for {@code Tetromino}.
 */
public class TetrominoTest {

    @Test
    public void testRotate() {
        Tetromino tetromino = new Tetromino(Shape.random());

        tetromino.setDirection(Direction.UP);
        tetromino.rotate(Rotation.CLOCKWISE);
        assertEquals(Direction.RIGHT, tetromino.getDirection());

        tetromino.setDirection(Direction.UP);
        tetromino.rotate(Rotation.COUNTERCLOCKWISE);
        assertEquals(Direction.LEFT, tetromino.getDirection());
    }

    @Test
    public void testTranslate() {
        Tetromino tetromino = new Tetromino(Shape.random());

        tetromino.setPosition(new Vector2D(0, 0));
        tetromino.translate(Direction.UP);
        assertEquals(new Vector2D(0, -1), tetromino.getPosition());

        tetromino.setPosition(new Vector2D(0, 0));
        tetromino.translate(Direction.RIGHT);
        assertEquals(new Vector2D(1, 0), tetromino.getPosition());

        tetromino.setPosition(new Vector2D(0, 0));
        tetromino.translate(Direction.DOWN);
        assertEquals(new Vector2D(0, 1), tetromino.getPosition());

        tetromino.setPosition(new Vector2D(0, 0));
        tetromino.translate(Direction.LEFT);
        assertEquals(new Vector2D(-1, 0), tetromino.getPosition());
    }

}
