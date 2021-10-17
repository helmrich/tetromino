package de.hdm_stuttgart.mi.game.tetromino;

/**
 * The {@code Vector2D} class stores {@code x} and {@code y} coordinates.
 * <p>
 * It represents a simple two-dimensional vector. The coordinates describe the
 * vector from its origin to its end.
 * </p>
 */
public class Vector2D {

    private double x;
    private double y;

    /**
     * Creates a {@code Vector2D} with specified {@code x} and {@code y} coordinates
     * 
     * @param x The x-coordinate of the vector.
     * @param y The y-coordinate of the vector.
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a copy of {@code Vector2D} object.
     */
    public Vector2D copy() {
        return new Vector2D(x, y);
    }

    /**
     * Gets the x-coordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the x-coordinate.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Gets the y-coordinate.
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the y-coordinate.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Gets the vector coordinates as a string.
     * <p>
     * Example:
     * </p>
     * 
     * <pre>
     * new Vector2D(5, 8).toString() == "(5, 8)";
     * </pre>
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj instanceof Vector2D)) {
            Vector2D vector2D = (Vector2D) obj;
            return ((vector2D.x == x) && (vector2D.y == y));
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return (int) x + (int) y;
    }

    /**
     * Creates a new {@code Vector2D} by adding a specified {@code Vector2D}.
     * 
     * @param vector2D The vector to add.
     * @return The sum of the vectors as a {@code Vector2D}.
     */
    public Vector2D add(Vector2D vector2D) {
        return new Vector2D(x + vector2D.x, y + vector2D.y);
    }

    /**
     * Creates a new {@code Vector2D} by subtracting a specified {@code Vector2D}.
     * 
     * @param vector2D The vector to subtract.
     * @return The difference of the vectors as a {@code Vector2D}.
     */
    public Vector2D subtract(Vector2D vector2D) {
        return new Vector2D(x - vector2D.x, y - vector2D.y);
    }

}
