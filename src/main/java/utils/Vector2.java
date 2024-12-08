/*
 * Copyright 2024 ForgeRock AS. All Rights Reserved
 *
 * Use of this code requires a commercial software license with ForgeRock AS.
 * or with one of its affiliates. All use shall be exclusively subject
 * to such license between the licensee and ForgeRock AS.
 */
package utils;

/**
 * Represents a 2D vector.
 */
public record Vector2(int x, int y) {

    public static Vector2 of(int x, int y) {
        return new Vector2(x, y);
    }

    public Vector2 setX(int x) {
        return new Vector2(x, y);
    }

    public Vector2 setY(int x) {
        return new Vector2(x, y);
    }

    public Vector2 add(Vector2 other) {
        return new Vector2(x + other.x, y + other.y);
    }

    public Vector2 multiply(int scalar) {
        return new Vector2(x * scalar, y * scalar);
    }

    public Vector2 rotateRight() {
        return new Vector2(y, -x);
    }

    public Vector2 rotateLeft() {
        return new Vector2(-y, x);
    }

    public Vector2 rotate(int degrees) {
        var times = degrees / 90;
        var result = this;
        for (var i = 0; i < times; i++) {
            result = result.rotateRight();
        }
        return result;
    }

    public int manhattanDistance() {
        return Math.abs(x) + Math.abs(y);
    }

    public Vector2 subtract(Vector2 other) {
        return new Vector2(x - other.x, y - other.y);
    }
}
