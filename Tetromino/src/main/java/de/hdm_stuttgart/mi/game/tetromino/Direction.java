package de.hdm_stuttgart.mi.game.tetromino;

/**
 * A set of values for describing a direction.
 */
public enum Direction {

    /**
     * Represents the up direction.
     */
    UP(new Vector2D(0, -1)),

    /**
     * Represents the rigth direction.
     */
    RIGHT(new Vector2D(1, 0)),

    /**
     * Represents the down direction.
     */
    DOWN(new Vector2D(0, 1)),

    /**
     * Represents the left direction.
     */
    LEFT(new Vector2D(-1, 0));

    private final Vector2D directionVector;
    private static final Direction[] directions = values();
    private static final int length = directions.length;

    private Direction(Vector2D direction) {
        this.directionVector = direction;
    }

    /**
     * Gets the normalized {@link Vector2D} of the direction.
     */
    public Vector2D getDirectionVector() {
        return directionVector.copy();
    }

    /**
     * Gets the next {@code Direction} (clockwise).
     * <p>
     * Example:
     * </p>
     * <pre>
     * Direction.UP -> Direction.RIGHT -> Direction.DOWN -> Direction.LEFT -> Direction.UP
     * </pre>
     */
    public Direction next() {
        return directions[(this.ordinal() + 1) % length];
    }

    /**
     * Gets the previous {@code Direction} (counterclockwise).
     * <p>
     * Example:
     * </p>
     * <pre>
     * Direction.UP -> Direction.LEFT -> Direction.DOWN -> Direction.RIGHT -> Direction.UP
     * </pre>
     */
    public Direction previous() {
        return directions[(this.ordinal() - 1 + length) % length];
    }

}
