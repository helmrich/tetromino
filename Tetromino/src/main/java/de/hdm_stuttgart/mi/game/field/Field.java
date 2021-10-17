package de.hdm_stuttgart.mi.game.field;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hdm_stuttgart.mi.game.tetromino.Color;

public class Field {

    private static final Logger log = LogManager.getLogger(Field.class.getName());

    protected final List<List<Color>> field;
    protected final int width;
    protected final int height;

    public Field(int width, int height) {
        field = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            field.add(new ArrayList<>(width));
            for (int col = 0; col < width; col++) {
                field.get(row).add(null);
            }
        }
        this.width = width;
        this.height = height;
    }

    protected Field(int width, int height, List<List<Color>> field) {
        this.width = width;
        this.height = height;
        this.field = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            this.field.add(new ArrayList<>(width));
            for (int col = 0; col < width; col++) {
                this.field.get(row).add(field.get(row).get(col));
            }
        }
    }

    public Field copy() {
        return new Field(width, height, field);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Color getCell(int col, int row) {
        return field.get(row).get(col);
    }

    public void setCell(int col, int row, Color color) {
        field.get(row).set(col, color);
    }

    public List<Color> getRow(int row) {
        return new ArrayList<>(field.get(row));
    }

    public void setRow(int row, List<Color> colorRow) {
        List<Color> rowReplacement = new ArrayList<>(width);
        if (colorRow == null || (colorRow.size() == 0)) {
            for (int i = 0; i < width; i++) {
                rowReplacement.add(null);
            }
            log.debug("Set empty row.");
        } else {
            if ((width - colorRow.size()) < 0) {
                log.warn("Row to set is longer then the field. The row will be trimmed to size.");
                colorRow = colorRow.subList(0, width);
            }
            for (int i = 0; i < colorRow.size(); i++) {
                rowReplacement.add(colorRow.get(i));
            }
            for (int i = 0; i < width - colorRow.size(); i++) {
                log.trace("Add null to row.");
                rowReplacement.add(null);
            }
            log.debug("Set row.");
        }
        field.set(row, rowReplacement);
    }

}
