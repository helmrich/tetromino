package de.hdm_stuttgart.mi.game.field;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hdm_stuttgart.mi.game.tetromino.Color;
import de.hdm_stuttgart.mi.game.tetromino.Direction;
import de.hdm_stuttgart.mi.game.tetromino.Rotation;
import de.hdm_stuttgart.mi.game.tetromino.Shape;
import de.hdm_stuttgart.mi.game.tetromino.Tetromino;
import de.hdm_stuttgart.mi.game.tetromino.Vector2D;

public class PlayField extends Field {

    private static final Logger log = LogManager.getLogger(PlayField.class.getName());

    private Tetromino tetromino;
    private Tetromino shadow;

    public PlayField() {
        super(10, 20);
        spawnTetromino(new Tetromino(Shape.random()));
    }

    protected PlayField(int width, int height, List<List<Color>> field, Tetromino tetromino, Tetromino shadow) {
        super(width, height, field);
        this.tetromino = tetromino.copy();
        this.shadow = shadow.copy();
    }

    @Override
    public PlayField copy() {
        return new PlayField(width, height, field, tetromino, shadow);
    }

    public Tetromino getTetromino() {
        return tetromino.copy();
    }

    public void spawnTetromino(Tetromino tetromino) {
        Vector2D spawnPosition;
        switch (tetromino.getShape()) {
            case I:
                spawnPosition = new Vector2D(getWidth() / 2 - 0.5, -0.5);
                break;
            case O:
                spawnPosition = new Vector2D(getWidth() / 2 - 0.5, -1.5);
                break;
            case J:
            case L:
            case S:
            case T:
            case Z:
                spawnPosition = new Vector2D(getWidth() / 2 - 1, -1);
                break;
            default:
                log.warn("Invalid shape: " + tetromino.getShape());
                spawnPosition = new Vector2D(0, 0);
                break;
        }
        tetromino.setPosition(spawnPosition);
        this.tetromino = tetromino.copy();
        setShadow();
    }

    public Tetromino getShadow() {
        return shadow.copy();
    }

    private void setShadow() {
        Tetromino nextShadow = tetromino.copy();
        nextShadow.setColor(Color.SHADOW);
        while (isTetrominoValid(nextShadow)) {
            nextShadow.translate(Direction.DOWN);
        }
        nextShadow.translate(Direction.UP);
        shadow = nextShadow;
    }

    public boolean translateTetromino(Direction direction) {
        Tetromino nextTetromino = tetromino.copy();
        nextTetromino.translate(direction);
        if (isTetrominoValid(nextTetromino)) {
            tetromino.translate(direction);
            setShadow();
            return true;
        } else {
            return false;
        }
    }

    public boolean rotateTetromino(Rotation rotation) {
        Tetromino nextTetromino = tetromino.copy();
        nextTetromino.rotate(rotation);
        if (isTetrominoValid(nextTetromino)) {
            tetromino.rotate(rotation);
            setShadow();
            return true;
        } else {
            return wallkick(rotation);
        }
    }

    private boolean wallkick(Rotation rotation) {
        Direction currentDirection = tetromino.getDirection();
        Tetromino nextTetromino = tetromino.copy();
        nextTetromino.rotate(rotation);
        nextTetromino.translate(currentDirection);
        if (isTetrominoValid(nextTetromino)) {
            tetromino = nextTetromino;
            return true;
        }
        if (tetromino.getShape() == Shape.I) {
            nextTetromino.translate(currentDirection);
            if (isTetrominoValid(nextTetromino)) {
                tetromino = nextTetromino;
                return true;
            }
            nextTetromino.setPosition(tetromino.getPosition());
            nextTetromino.translate(currentDirection.next().next());
            if (isTetrominoValid(nextTetromino)) {
                tetromino = nextTetromino;
                return true;
            }
        }
        return false;
    }

    private boolean isTetrominoValid(Tetromino tetromino) {
        List<List<Integer>> shape = tetromino.getShapeForCurrentRotation();
        Vector2D position = tetromino.getTopLeftCornerPosition();

        for (int tetrominoRow = 0; tetrominoRow < shape.size(); tetrominoRow++) {
            for (int tetrominoCol = 0; tetrominoCol < shape.get(tetrominoRow).size(); tetrominoCol++) {

                int playFieldRow = (int) position.getY() + tetrominoRow;
                int playFieldCol = (int) position.getX() + tetrominoCol;
                // check if tetromino cell is not empty
                if (shape.get(tetrominoRow).get(tetrominoCol) != 0) {
                    // check if cell is out of top border
                    if (playFieldRow < 0) {
                        // check if cell is out of left or right border
                        if (playFieldCol < 0 || playFieldCol >= getWidth()) {
                            log.debug("out of Field");
                            return false;
                        }
                    }
                    // check if cell is out of any other border
                    else if (playFieldRow >= getHeight() || playFieldCol < 0 || playFieldCol >= getWidth()) {
                        log.debug("out of Field");
                        return false;
                    }
                    // check if playfield cell is not empty
                    else if (getCell(playFieldCol, playFieldRow) != null) {
                        log.debug("cell not empty");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean transferTetromino() {
        List<List<Integer>> shape = tetromino.getShapeForCurrentRotation();
        Vector2D position = tetromino.getTopLeftCornerPosition();
        Color color = tetromino.getColor();

        for (int tetrominoRow = 0; tetrominoRow < shape.size(); tetrominoRow++) {
            for (int tetrominoCol = 0; tetrominoCol < shape.get(tetrominoRow).size(); tetrominoCol++) {

                if (shape.get(tetrominoRow).get(tetrominoCol) != 0) {
                    int playFieldRow = (int) position.getY() + tetrominoRow;
                    int playFieldCol = (int) position.getX() + tetrominoCol;
                    /// check if cell is out of field
                    if (playFieldRow < 0 || playFieldRow >= getHeight() || playFieldCol < 0
                            || playFieldCol >= getWidth()) {
                        return false;
                    } else {
                        setCell(playFieldCol, playFieldRow, color);
                    }
                }
            }
        }
        return true;
    }

    public int clearFullLines() {
        int numberOfClearedLines = 0;
        boolean lineFull;
        // check every row from bottom to top
        for (int row = getHeight() - 1; row >= 0; row--) {
            lineFull = true;
            // check if row is not full
            for (int col = 0; col < getWidth(); col++) {
                if (getCell(col, row) == null) {
                    lineFull = false;
                    break;
                }
            }
            if (lineFull) {
                // move all lines above the full line one row downwards
                for (int i = row; i > 0; i--) {
                    setRow(i, getRow(i - 1));
                }
                // empty the top row
                setRow(0, null);
                row++;
                numberOfClearedLines++;
            }
        }
        return numberOfClearedLines;
    }

}
