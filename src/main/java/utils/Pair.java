/*
 * Copyright 2024 ForgeRock AS. All Rights Reserved
 *
 * Use of this code requires a commercial software license with ForgeRock AS.
 * or with one of its affiliates. All use shall be exclusively subject
 * to such license between the licensee and ForgeRock AS.
 */
package utils;

public record Pair<T>(T first, T second) {
    public static Pair<Vector2> of(Vector2 first, Vector2 second) {
        return new Pair<>(first, second);
    }
}
