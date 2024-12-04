/*
 * Copyright 2024 ForgeRock AS. All Rights Reserved
 *
 * Use of this code requires a commercial software license with ForgeRock AS.
 * or with one of its affiliates. All use shall be exclusively subject
 * to such license between the licensee and ForgeRock AS.
 */
package utils;

import java.util.List;

public class Grid {

    Character[][] grid;

    public Grid(Vector2 size) {
        this(size.x(), size.y());
    }

    public Grid(int rows, int columns) {
        grid = new Character[rows][columns];
    }

    public Grid(List<String> contents) {
        grid = new Character[contents.size()][contents.get(0).length()];
        for (int i = 0; i < contents.size(); i++) {
            for (int j = 0; j < contents.get(i).length(); j++) {
                grid[i][j] = contents.get(i).charAt(j);
            }
        }
    }

    public Character get(int x, int y) {
        return grid[x][y];
    }

    public Character get(Vector2 coordinate) {
        return get(coordinate.x(), coordinate.y());
    }

    public void set(int x, int y, Character value) {
        grid[x][y] = value;
    }

    public void set(Vector2 coordinate, Character value) {
        set(coordinate.x(), coordinate.y(), value);
    }

    public int getRows() {
        return grid.length;
    }

    public int getColumns() {
        return grid[0].length;
    }

    public boolean isOutOfBounds(int x, int y) {
        return x < 0 || x >= getRows() || y < 0 || y >= getColumns();
    }

    public boolean isOutOfBounds(Vector2 coordinate) {
        return isOutOfBounds(coordinate.x(), coordinate.y());
    }
}
