package de.hdm_stuttgart.mi.game.field;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.hdm_stuttgart.mi.game.tetromino.Color;
import de.hdm_stuttgart.mi.game.tetromino.Shape;
import de.hdm_stuttgart.mi.game.tetromino.Tetromino;
import de.hdm_stuttgart.mi.game.tetromino.Vector2D;

public class NextField extends Field {

    private final List<Tetromino> tetrominos;

    public NextField() {
        super(5, 16);
        tetrominos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            pushTetromino();
        }
        setTetrominosPosition();
    }

    protected NextField(int width, int height, List<List<Color>> field, List<Tetromino> tetrominos) {
        super(width, height, field);
        this.tetrominos = new ArrayList<>();
        for (int i = 0; i < tetrominos.size(); i++) {
            this.tetrominos.add(tetrominos.get(i).copy());
        }
    }

    @Override
    public NextField copy() {
        return new NextField(width, height, field, tetrominos);
    }

    public List<Tetromino> getTetrominos() {
        return tetrominos.stream().map(tetromino -> tetromino.copy()).collect(Collectors.toList());
    }

    public Tetromino getNextTetromino() {
        Tetromino nextTetromino = tetrominos.get(0);
        nextTetromino.setDefault();
        shiftTetromino();
        pushTetromino();
        setTetrominosPosition();
        return nextTetromino.copy();
    }

    // push() adds one element to the end of an array
    private void pushTetromino() {
        tetrominos.add(new Tetromino(Shape.random()));
    }

    // shift() removes the first element from an array
    private void shiftTetromino() {
        tetrominos.remove(0);
    }

    private void setTetrominosPosition() {
        for (int i = 0; i < tetrominos.size(); i++) {
            if (tetrominos.get(i).getShape() == Shape.O) {
                tetrominos.get(i).setPosition(new Vector2D(2, 1.5 + 3 * i));
            } else {
                tetrominos.get(i).setPosition(new Vector2D(2, 2 + 3 * i));
            }
        }
    }

}
