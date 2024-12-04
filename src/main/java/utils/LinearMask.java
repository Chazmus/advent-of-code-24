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

public class LinearMask {

    private List<Vector2> mask;

    public LinearMask(List<Vector2> mask) {
        this.mask = mask;
    }

    public List<List<Character>> getAllSets(Grid grid) {
        var sets = new ArrayList<List<Character>>();
        for (var i = 0; i < grid.getColumns(); i++) {
            for (var j = 0; j < grid.getRows(); j++) {
                var set = new ArrayList<Character>();
                for (var maskCoordinate : mask) {
                    var x = i + maskCoordinate.x();
                    var y = j + maskCoordinate.y();
                    if (!grid.isOutOfBounds(x, y)) {
                        set.add(grid.get(x, y));
                    }
                }
                sets.add(set);
            }
        }
        return sets;
    }

    public LinearMask rotateRight() {
        var newMask = new ArrayList<Vector2>();
        for (var vector : mask) {
            newMask.add(Vector2.of(vector.y(), -vector.x()));
        }
        return new LinearMask(newMask);
    }
}
