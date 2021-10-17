package de.hdm_stuttgart.mi.game.tetromino;

import java.util.EnumMap;
import java.util.List;
import java.util.Random;

@FunctionalInterface
interface EnumMapProducer<K extends Enum<K>, V> {

    EnumMap<K, V> getEnumMap();

}

/**
 * A set of values for describing the shape of a {@link Tetromino}.
 */
public enum Shape{

    /**
     * Represents the I shape.
     * <pre>
     * Shape.I
     *┌─┬─┬─┬─┐
     *└─┴─┴─┴─┘
     * UP                RIGHT             DOWN              LEFT
     *┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐
     *│ 0 │ 0 │ 0 │ 0 │ │ 0 │ 0 │ 1 │ 0 │ │ 0 │ 0 │ 0 │ 0 │ │ 0 │ 1 │ 0 │ 0 │
     *├───┼───┼───┼───┤ ├───┼───┼───┼───┤ ├───┼───┼───┼───┤ ├───┼───┼───┼───┤
     *│ 1 │ 1 │ 1 │ 1 │ │ 0 │ 0 │ 1 │ 0 │ │ 0 │ 0 │ 0 │ 0 │ │ 0 │ 1 │ 0 │ 0 │
     *├───┼───┼───┼───┤ ├───┼───┼───┼───┤ ├───┼───┼───┼───┤ ├───┼───┼───┼───┤
     *│ 0 │ 0 │ 0 │ 0 │ │ 0 │ 0 │ 1 │ 0 │ │ 1 │ 1 │ 1 │ 1 │ │ 0 │ 1 │ 0 │ 0 │
     *├───┼───┼───┼───┤ ├───┼───┼───┼───┤ ├───┼───┼───┼───┤ ├───┼───┼───┼───┤
     *│ 0 │ 0 │ 0 │ 0 │ │ 0 │ 0 │ 1 │ 0 │ │ 0 │ 0 │ 0 │ 0 │ │ 0 │ 1 │ 0 │ 0 │
     *└───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┴───┘
     * </pre>
     */
    I(() -> {
        EnumMap<Direction, List<List<Integer>>> shapeByDirection = new EnumMap<>(Direction.class);
        shapeByDirection.put(Direction.UP, List.of(
            List.of(0, 0, 0, 0),
            List.of(1, 1, 1, 1),
            List.of(0, 0, 0, 0),
            List.of(0, 0, 0, 0)
        ));
        shapeByDirection.put(Direction.RIGHT, List.of(
            List.of(0, 0, 1, 0),
            List.of(0, 0, 1, 0),
            List.of(0, 0, 1, 0),
            List.of(0, 0, 1, 0)
        ));
        shapeByDirection.put(Direction.DOWN, List.of(
            List.of(0, 0, 0, 0),
            List.of(0, 0, 0, 0),
            List.of(1, 1, 1, 1),
            List.of(0, 0, 0, 0)
        ));
        shapeByDirection.put(Direction.LEFT, List.of(
            List.of(0, 1, 0, 0),
            List.of(0, 1, 0, 0),
            List.of(0, 1, 0, 0),
            List.of(0, 1, 0, 0)
        ));
        return shapeByDirection;
    }),

    /**
     * Represents the J shape.
     * <pre>
     * Shape.J
     *┌─┐
     *├─┼─┬─┐
     *└─┴─┴─┘
     * UP            RIGHT         DOWN          LEFT
     *┌───┬───┬───┐ ┌───┬───┬───┐ ┌───┬───┬───┐ ┌───┬───┬───┐
     *│ 1 │ 0 │ 0 │ │ 0 │ 1 │ 1 │ │ 0 │ 0 │ 0 │ │ 0 │ 1 │ 0 │
     *├───┼───┼───┤ ├───┼───┼───┤ ├───┼───┼───┤ ├───┼───┼───┤
     *│ 1 │ 1 │ 1 │ │ 0 │ 1 │ 0 │ │ 1 │ 1 │ 1 │ │ 0 │ 1 │ 0 │
     *├───┼───┼───┤ ├───┼───┼───┤ ├───┼───┼───┤ ├───┼───┼───┤
     *│ 0 │ 0 │ 0 │ │ 0 │ 1 │ 0 │ │ 0 │ 0 │ 1 │ │ 1 │ 1 │ 0 │
     *└───┴───┴───┘ └───┴───┴───┘ └───┴───┴───┘ └───┴───┴───┘
     * </pre>
     */
    J(() -> {
        EnumMap<Direction, List<List<Integer>>> shapeByDirection = new EnumMap<>(Direction.class);
        shapeByDirection.put(Direction.UP, List.of(
            List.of(1, 0, 0),
            List.of(1, 1, 1),
            List.of(0, 0, 0)
        ));
        shapeByDirection.put(Direction.RIGHT, List.of(
            List.of(0, 1, 1),
            List.of(0, 1, 0),
            List.of(0, 1, 0)
        ));
        shapeByDirection.put(Direction.DOWN, List.of(
            List.of(0, 0, 0),
            List.of(1, 1, 1),
            List.of(0, 0, 1)
        ));
        shapeByDirection.put(Direction.LEFT, List.of(
            List.of(0, 1, 0),
            List.of(0, 1, 0),
            List.of(1, 1, 0)
        ));
        return shapeByDirection;
    }),

    /**
     * Represents the L shape.
     * <pre>
     * Shape.L
     *    ┌─┐
     *┌─┬─┼─┤
     *└─┴─┴─┘
     * UP            RIGHT         DOWN          LEFT
     *┌───┬───┬───┐ ┌───┬───┬───┐ ┌───┬───┬───┐ ┌───┬───┬───┐
     *│ 0 │ 0 │ 1 │ │ 0 │ 1 │ 0 │ │ 0 │ 0 │ 0 │ │ 1 │ 1 │ 0 │
     *├───┼───┼───┤ ├───┼───┼───┤ ├───┼───┼───┤ ├───┼───┼───┤
     *│ 1 │ 1 │ 1 │ │ 0 │ 1 │ 0 │ │ 1 │ 1 │ 1 │ │ 0 │ 1 │ 0 │
     *├───┼───┼───┤ ├───┼───┼───┤ ├───┼───┼───┤ ├───┼───┼───┤
     *│ 0 │ 0 │ 0 │ │ 0 │ 1 │ 1 │ │ 1 │ 0 │ 0 │ │ 0 │ 1 │ 0 │
     *└───┴───┴───┘ └───┴───┴───┘ └───┴───┴───┘ └───┴───┴───┘
     * </pre>
     */
    L(() -> {
        EnumMap<Direction, List<List<Integer>>> shapeByDirection = new EnumMap<>(Direction.class);
        shapeByDirection.put(Direction.UP, List.of(
            List.of(0, 0, 1),
            List.of(1, 1, 1),
            List.of(0, 0, 0)
        ));
        shapeByDirection.put(Direction.RIGHT, List.of(
            List.of(0, 1, 0),
            List.of(0, 1, 0),
            List.of(0, 1, 1)
        ));
        shapeByDirection.put(Direction.DOWN, List.of(
            List.of(0, 0, 0),
            List.of(1, 1, 1),
            List.of(1, 0, 0)
        ));
        shapeByDirection.put(Direction.LEFT, List.of(
            List.of(1, 1, 0),
            List.of(0, 1, 0),
            List.of(0, 1, 0)
        ));
        return shapeByDirection;
    }),

    /**
     * Represents the O shape.
     * <pre>
     * Shape.O
     *┌─┬─┐
     *├─┼─┤
     *└─┴─┘
     * UP        RIGHT     DOWN      LEFT
     *┌───┬───┐ ┌───┬───┐ ┌───┬───┐ ┌───┬───┐
     *│ 1 │ 1 │ │ 1 │ 1 │ │ 1 │ 1 │ │ 1 │ 1 │
     *├───┼───┤ ├───┼───┤ ├───┼───┤ ├───┼───┤
     *│ 1 │ 1 │ │ 1 │ 1 │ │ 1 │ 1 │ │ 1 │ 1 │
     *└───┴───┘ └───┴───┘ └───┴───┘ └───┴───┘
     * </pre>
     */
    O(() -> {
        EnumMap<Direction, List<List<Integer>>> shapeByDirection = new EnumMap<>(Direction.class);
        shapeByDirection.put(Direction.UP, List.of(
            List.of(1,1),
            List.of(1,1)
        ));
        shapeByDirection.put(Direction.RIGHT, List.of(
            List.of(1,1),
            List.of(1,1)
        ));
        shapeByDirection.put(Direction.DOWN, List.of(
            List.of(1,1),
            List.of(1,1)
        ));
        shapeByDirection.put(Direction.LEFT, List.of(
            List.of(1,1),
            List.of(1,1)
        ));
        return shapeByDirection;
    }),

    /**
     * Represents the S shape.
     * <pre>
     * Shape.S
     *  ┌─┬─┐
     *┌─┼─┼─┘
     *└─┴─┘
     * UP            RIGHT         DOWN          LEFT
     *┌───┬───┬───┐ ┌───┬───┬───┐ ┌───┬───┬───┐ ┌───┬───┬───┐
     *│ 0 │ 1 │ 1 │ │ 0 │ 1 │ 0 │ │ 0 │ 0 │ 0 │ │ 1 │ 0 │ 0 │
     *├───┼───┼───┤ ├───┼───┼───┤ ├───┼───┼───┤ ├───┼───┼───┤
     *│ 1 │ 1 │ 0 │ │ 0 │ 1 │ 1 │ │ 0 │ 1 │ 1 │ │ 1 │ 1 │ 0 │
     *├───┼───┼───┤ ├───┼───┼───┤ ├───┼───┼───┤ ├───┼───┼───┤
     *│ 0 │ 0 │ 0 │ │ 0 │ 0 │ 1 │ │ 1 │ 1 │ 0 │ │ 0 │ 1 │ 0 │
     *└───┴───┴───┘ └───┴───┴───┘ └───┴───┴───┘ └───┴───┴───┘
     * </pre>
     */
    S(() -> {
        EnumMap<Direction, List<List<Integer>>> shapeByDirection = new EnumMap<>(Direction.class);
        shapeByDirection.put(Direction.UP, List.of(
            List.of(0, 1, 1),
            List.of(1, 1, 0),
            List.of(0, 0, 0)
        ));
        shapeByDirection.put(Direction.RIGHT, List.of(
            List.of(0, 1, 0),
            List.of(0, 1, 1),
            List.of(0, 0, 1)
        ));
        shapeByDirection.put(Direction.DOWN, List.of(
            List.of(0, 0, 0),
            List.of(0, 1, 1),
            List.of(1, 1, 0)
        ));
        shapeByDirection.put(Direction.LEFT, List.of(
            List.of(1, 0, 0),
            List.of(1, 1, 0),
            List.of(0, 1, 0)
        ));
        return shapeByDirection;
    }),

    /**
     * Represents the T shape.
     * <pre>
     * Shape.T
     *  ┌─┐
     *┌─┼─┼─┐
     *└─┴─┴─┘
     * UP            RIGHT         DOWN          LEFT
     *┌───┬───┬───┐ ┌───┬───┬───┐ ┌───┬───┬───┐ ┌───┬───┬───┐
     *│ 0 │ 1 │ 0 │ │ 0 │ 1 │ 0 │ │ 0 │ 0 │ 0 │ │ 0 │ 1 │ 0 │
     *├───┼───┼───┤ ├───┼───┼───┤ ├───┼───┼───┤ ├───┼───┼───┤
     *│ 1 │ 1 │ 1 │ │ 0 │ 1 │ 1 │ │ 1 │ 1 │ 1 │ │ 1 │ 1 │ 0 │
     *├───┼───┼───┤ ├───┼───┼───┤ ├───┼───┼───┤ ├───┼───┼───┤
     *│ 0 │ 0 │ 0 │ │ 0 │ 1 │ 0 │ │ 0 │ 1 │ 0 │ │ 0 │ 1 │ 0 │
     *└───┴───┴───┘ └───┴───┴───┘ └───┴───┴───┘ └───┴───┴───┘
     * </pre>
     */
    T(() -> {
        EnumMap<Direction, List<List<Integer>>> shapeByDirection = new EnumMap<>(Direction.class);
        shapeByDirection.put(Direction.UP, List.of(
            List.of(0, 1, 0),
            List.of(1, 1, 1),
            List.of(0, 0, 0)
        ));
        shapeByDirection.put(Direction.RIGHT, List.of(
            List.of(0, 1, 0),
            List.of(0, 1, 1),
            List.of(0, 1, 0)
        ));
        shapeByDirection.put(Direction.DOWN, List.of(
            List.of(0, 0, 0),
            List.of(1, 1, 1),
            List.of(0, 1, 0)
        ));
        shapeByDirection.put(Direction.LEFT, List.of(
            List.of(0, 1, 0),
            List.of(1, 1, 0),
            List.of(0, 1, 0)
        ));
        return shapeByDirection;
    }),

    /**
     * Represents the Z shape.
     * <pre>
     * Shape.Z
     *┌─┬─┐
     *└─┼─┼─┐
     *  └─┴─┘
     * UP            RIGHT         DOWN          LEFT
     *┌───┬───┬───┐ ┌───┬───┬───┐ ┌───┬───┬───┐ ┌───┬───┬───┐
     *│ 1 │ 1 │ 0 │ │ 0 │ 0 │ 1 │ │ 0 │ 0 │ 0 │ │ 0 │ 1 │ 0 │
     *├───┼───┼───┤ ├───┼───┼───┤ ├───┼───┼───┤ ├───┼───┼───┤
     *│ 0 │ 1 │ 1 │ │ 0 │ 1 │ 1 │ │ 1 │ 1 │ 0 │ │ 1 │ 1 │ 0 │
     *├───┼───┼───┤ ├───┼───┼───┤ ├───┼───┼───┤ ├───┼───┼───┤
     *│ 0 │ 0 │ 0 │ │ 0 │ 1 │ 0 │ │ 0 │ 1 │ 1 │ │ 1 │ 0 │ 0 │
     *└───┴───┴───┘ └───┴───┴───┘ └───┴───┴───┘ └───┴───┴───┘
     * </pre>
     */
    Z(() -> {
        EnumMap<Direction, List<List<Integer>>> shapeByDirection = new EnumMap<>(Direction.class);
        shapeByDirection.put(Direction.UP, List.of(
            List.of(1, 1, 0),
            List.of(0, 1, 1),
            List.of(0, 0, 0)
        ));
        shapeByDirection.put(Direction.RIGHT, List.of(
            List.of(0, 0, 1),
            List.of(0, 1, 1),
            List.of(0, 1, 0)
        ));
        shapeByDirection.put(Direction.DOWN, List.of(
            List.of(0, 0, 0),
            List.of(1, 1, 0),
            List.of(0, 1, 1)
        ));
        shapeByDirection.put(Direction.LEFT, List.of(
            List.of(0, 1, 0),
            List.of(1, 1, 0),
            List.of(1, 0, 0)
        ));
        return shapeByDirection;
    });

    private final EnumMap<Direction, List<List<Integer>>> shapeByDirection;
    private final Vector2D center;
    private static final Shape[] shapes = values();
    private static final int length = shapes.length;
    private static final Random random = new Random();
    private static Shape lastRandom;

    private Shape(EnumMapProducer<Direction, List<List<Integer>>> enumMapProducer) {
        shapeByDirection = enumMapProducer.getEnumMap();
        double length = shapeByDirection.get(Direction.UP).size();
        center = new Vector2D(length / 2 - 0.5, length / 2 - 0.5);
    }

    /**
     * Gets a random shape value.
     */
    public static Shape random() {
        Shape shape = shapes[random.nextInt(length)];
        if (shape == lastRandom) {
            shape = shapes[random.nextInt(length)];
        }
        lastRandom = shape;
        return shape;
    }

    /**
     * Gets the positioning of the center as a positive {@link Vector2D}.
     * <pre>
     *I Center = (1.5, 1.5)
     *J Center = (1.0, 1.0)
     *L Center = (1.0, 1.0)
     *O Center = (0.5, 0.5)
     *S Center = (1.0, 1.0)
     *T Center = (1.0, 1.0)
     *Z Center = (1.0, 1.0)
     * </pre>
     */
    public Vector2D getCenter() {
        return center.copy();
    }

    /**
     * Gets the shapes as a two-dimensional {@code List} for a specified {@link Direction}.
     * @param direction The specified direction.
     * @return The shape as a {@code List<List<Integer>>}.
     */
    public List<List<Integer>> getShapeForDirection(Direction direction) {
        return shapeByDirection.get(direction);
    }

}
