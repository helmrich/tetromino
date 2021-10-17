package de.hdm_stuttgart.mi.game.tetromino;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The {@code Tetromino} class represents a
 * <a href="https://en.wikipedia.org/wiki/Tetromino">tetromino</a>, a geometric
 * shape composed of four squares, connected orthogonally.
 * <p>
 * The shape is represented by {@link Shape}. A tetromino has also a
 * {@link Color}, a {@link Direction} and a position as a {@link Vector2D}.
 * </p>
 */
public class Tetromino {

    private static final Logger log = LogManager.getLogger(Tetromino.class.getName());

    private Shape shape;
    private Color color;
    private Direction direction;
    private Vector2D position;

    public Tetromino(Shape shape) {
        this.shape = shape;
        color = getColorForShape(shape);
        direction = Direction.UP;
        position = new Vector2D(0, 0);
    }

    protected Tetromino(Shape shape, Color color, Direction direction, Vector2D position) {
        this.shape = shape;
        this.color = color;
        this.direction = direction;
        this.position = position.copy();
    }

    public Tetromino copy() {
        return new Tetromino(shape, color, direction, position);
    }

    public Shape getShape() {
        return shape;
    }

    public synchronized void setShape(Shape shape) {
        this.shape = shape;
    }

    public Color getColor() {
        return color;
    }

    public synchronized void setColor(Color color) {
        this.color = color;
    }

    public Direction getDirection() {
        return direction;
    }

    public synchronized void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Vector2D getPosition() {
        return position.copy();
    }

    public synchronized void setPosition(Vector2D position) {
        this.position = position.copy();
    }

    public synchronized void setDefault() {
        color = getColorForShape(shape);
        direction = Direction.UP;
        position = new Vector2D(0, 0);
    }

    public synchronized void setDefaultColor() {
        color = getColorForShape(shape);
    }

    public Vector2D getTopLeftCornerPosition() {
        return position.subtract(shape.getCenter());
    }

    public Color getColorForShape(Shape shape) {
        switch (shape) {
            case I:
                return Color.CYAN;
            case J:
                return Color.BLUE;
            case L:
                return Color.ORANGE;
            case O:
                return Color.YELLOW;
            case S:
                return Color.GREEN;
            case T:
                return Color.PURPLE;
            case Z:
                return Color.RED;
            default:
                log.warn("Invalid shape: " + shape);
                return Color.SHADOW;
        }
    }

    public List<List<Integer>> getShapeForCurrentRotation() {
        return shape.getShapeForDirection(direction);
    }

    public synchronized void translate(Direction direction) {
        position = position.add(direction.getDirectionVector());
        log.debug("Tetromino moved to " + position.toString() + " position");
    }

    public synchronized void rotate(Rotation rotation) {
        switch (rotation) {
            case CLOCKWISE:
                direction = direction.next();
                log.debug("Tetromino rotated to " + direction + " direction");
                break;
            case COUNTERCLOCKWISE:
                direction = direction.previous();
                log.debug("Tetromino rotated to " + direction + " direction");
                break;
            default:
                log.warn("Invalid rotation: " + rotation);
                break;
        }
    }

}
