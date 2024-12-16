package utils;

import java.util.stream.Stream;

public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT,
    UP_LEFT,
    UP_RIGHT,
    DOWN_LEFT,
    DOWN_RIGHT;

    public static Stream<Direction> getCardinalDirections() {
        return Stream.of(UP, DOWN, LEFT, RIGHT);
    }

    public static Direction fromVector(Vector2 vector) {
        if (vector.equals(Vector2.of(0, -1))) {
            return UP;
        } else if (vector.equals(Vector2.of(0, 1))) {
            return DOWN;
        } else if (vector.equals(Vector2.of(-1, 0))) {
            return LEFT;
        } else if (vector.equals(Vector2.of(1, 0))) {
            return RIGHT;
        } else if (vector.equals(Vector2.of(-1, -1))) {
            return UP_LEFT;
        } else if (vector.equals(Vector2.of(1, -1))) {
            return UP_RIGHT;
        } else if (vector.equals(Vector2.of(-1, 1))) {
            return DOWN_LEFT;
        } else if (vector.equals(Vector2.of(1, 1))) {
            return DOWN_RIGHT;
        } else {
            throw new IllegalArgumentException("Invalid vector");
        }
    }

    public static Direction from(char instruction) {
        switch (instruction) {
            case '^' -> {
                return UP;
            }
            case 'v' -> {
                return DOWN;
            }
            case '<' -> {
                return LEFT;
            }
            case '>' -> {
                return RIGHT;
            }
        }
        throw new IllegalArgumentException("Invalid instruction");
    }

    public Vector2 toVector() {
        return switch (this) {
            case UP -> Vector2.of(0, -1);
            case DOWN -> Vector2.of(0, 1);
            case LEFT -> Vector2.of(-1, 0);
            case RIGHT -> Vector2.of(1, 0);
            case UP_LEFT -> Vector2.of(-1, -1);
            case UP_RIGHT -> Vector2.of(1, -1);
            case DOWN_LEFT -> Vector2.of(-1, 1);
            case DOWN_RIGHT -> Vector2.of(1, 1);
        };
    }

    public Direction turnRight() {
        return switch (this) {
            case UP -> RIGHT;
            case DOWN -> LEFT;
            case LEFT -> UP;
            case RIGHT -> DOWN;
            case UP_LEFT -> UP_RIGHT;
            case UP_RIGHT -> DOWN_RIGHT;
            case DOWN_LEFT -> UP_LEFT;
            case DOWN_RIGHT -> DOWN_LEFT;
        };
    }

    public Direction turnLeft() {
        return switch (this) {
            case UP -> LEFT;
            case DOWN -> RIGHT;
            case LEFT -> DOWN;
            case RIGHT -> UP;
            case UP_LEFT -> DOWN_LEFT;
            case UP_RIGHT -> UP_LEFT;
            case DOWN_LEFT -> DOWN_RIGHT;
            case DOWN_RIGHT -> UP_RIGHT;
        };
    }

    public Direction getOpposite() {
        return switch (this) {
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
            case UP_LEFT -> DOWN_RIGHT;
            case UP_RIGHT -> DOWN_LEFT;
            case DOWN_LEFT -> UP_RIGHT;
            case DOWN_RIGHT -> UP_LEFT;
        };
    }
}
