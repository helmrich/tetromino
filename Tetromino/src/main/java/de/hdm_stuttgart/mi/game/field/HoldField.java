package de.hdm_stuttgart.mi.game.field;

import java.util.List;

import de.hdm_stuttgart.mi.game.tetromino.Color;
import de.hdm_stuttgart.mi.game.tetromino.Shape;
import de.hdm_stuttgart.mi.game.tetromino.Tetromino;
import de.hdm_stuttgart.mi.game.tetromino.Vector2D;

public class HoldField extends Field {

    private Tetromino tetromino;
    private boolean swappable = true;

    public HoldField() {
        super(5, 4);
    }

    protected HoldField(int width, int height, List<List<Color>> field, Tetromino tetromino, boolean swappable) {
        super(width, height, field);
        if (tetromino != null) {
            this.tetromino = tetromino.copy();
        } else {
            this.tetromino = null;
        }
        this.swappable = swappable;
    }

    @Override
    public HoldField copy() {
        return new HoldField(width, height, field, tetromino, swappable);
    }

    public Tetromino getTetromino() {
        if (tetromino != null) {
            return tetromino.copy();
        } else {
            return null;
        }
    }

    public boolean isSwappable() {
        return swappable;
    }

    public void setSwappable(boolean swappable) {
        this.swappable = swappable;
        if (tetromino != null) {
            if (swappable) {
                tetromino.setDefaultColor();
            } else {
                tetromino.setColor(Color.SHADOW);
            }
        }
    }

    public Tetromino swapTetromino(Tetromino givenTetromino) {
        Tetromino takenTetromino = null;

        if (tetromino != null) {
            takenTetromino = tetromino.copy();
            takenTetromino.setDefault();
        }
        tetromino = givenTetromino.copy();
        tetromino.setDefault();
        if (tetromino.getShape() == Shape.O) {
            tetromino.setPosition(new Vector2D(2, 1.5));
        } else {
            tetromino.setPosition(new Vector2D(2, 2));
        }
        return takenTetromino;
    }

}
