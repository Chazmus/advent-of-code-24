package utils;

public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT,
    UP_LEFT,
    UP_RIGHT,
    DOWN_LEFT,
    DOWN_RIGHT;

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
}
