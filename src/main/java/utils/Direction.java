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
}
