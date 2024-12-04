/*
 * Copyright 2024 ForgeRock AS. All Rights Reserved
 *
 * Use of this code requires a commercial software license with ForgeRock AS.
 * or with one of its affiliates. All use shall be exclusively subject
 * to such license between the licensee and ForgeRock AS.
 */
package utils;

import java.util.ArrayList;
import java.util.List;

public class WindowMask {
    private final int width;
    private final int height;

    public WindowMask(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public List<Grid> getAllSets(Grid grid) {
        var sets = new ArrayList<Grid>();
        for (var i = 0; i < grid.getColumns(); i++) {
            for (var j = 0; j < grid.getRows(); j++) {
                var set = new Grid(width, height);
                for (var x = 0; x < width; x++) {
                    for (var y = 0; y < height; y++) {
                        var xCoord = i + x;
                        var yCoord = j + y;
                        if (!grid.isOutOfBounds(xCoord, yCoord)) {
                            set.set(x, y, grid.get(xCoord, yCoord));
                        }
                    }
                }
                sets.add(set);
            }
        }
        return sets;

    }
}
