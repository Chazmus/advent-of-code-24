/*
 * Copyright 2024 ForgeRock AS. All Rights Reserved
 *
 * Use of this code requires a commercial software license with ForgeRock AS.
 * or with one of its affiliates. All use shall be exclusively subject
 * to such license between the licensee and ForgeRock AS.
 */
package utils;

import java.util.stream.Stream;

/**
 * Represents a 2D vector.
 */
public record BigVector2(long x, long y) {

    public static BigVector2 of(long x, long y) {
        return new BigVector2(x, y);
    }

    public static BigVector2 of(String x, String y) {
        return new BigVector2(Long.parseLong(x), Long.parseLong(y));
    }

    public BigVector2 setX(int x) {
        return new BigVector2(x, y);
    }

    public BigVector2 setY(int x) {
        return new BigVector2(x, y);
    }

    public BigVector2 add(BigVector2 other) {
        return new BigVector2(x + other.x, y + other.y);
    }

    public BigVector2 multiply(int scalar) {
        return new BigVector2(x * scalar, y * scalar);
    }

    public BigVector2 rotateRight() {
        return new BigVector2(y, -x);
    }

    public BigVector2 rotateLeft() {
        return new BigVector2(-y, x);
    }

    public BigVector2 rotate(int degrees) {
        var times = degrees / 90;
        var result = this;
        for (var i = 0; i < times; i++) {
            result = result.rotateRight();
        }
        return result;
    }

    public BigVector2 subtract(BigVector2 other) {
        return new BigVector2(x - other.x, y - other.y);
    }

    public Stream<BigVector2> getCardinalNeighbours() {
        return Stream.of(
                new BigVector2(x, y + 1),
                new BigVector2(x, y - 1),
                new BigVector2(x + 1, y),
                new BigVector2(x - 1, y)
        );
    }
}
