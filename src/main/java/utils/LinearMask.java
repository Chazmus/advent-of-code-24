package utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a linear mask that can be applied to a grid.
 */
public class LinearMask {

    private final List<Vector2> mask;

    /**
     * Create a new linear mask with the specified direction and length.
     *
     * @param direction The direction of the mask.
     * @param length    The length of the mask.
     */
    public LinearMask(Direction direction, int length) {
        var directionVector = direction.toVector();
        var mask = new ArrayList<Vector2>();
        for (var i = 0; i < length; i++) {
            mask.add(directionVector.multiply(i));
        }
        this.mask = mask;
    }

    private LinearMask(List<Vector2> mask) {
        this.mask = mask;
    }

    private List<List<Character>> getAllCharSets(Grid grid) {
        var sets = new ArrayList<List<Character>>();
        for (var i = 0; i < grid.getColumns(); i++) {
            for (var j = 0; j < grid.getRows(); j++) {
                var set = new ArrayList<Character>();
                for (var maskCoordinate : mask) {
                    var x = i + maskCoordinate.x();
                    var y = j + maskCoordinate.y();
                    if (grid.isWithinBounds(x, y)) {
                        set.add(grid.get(x, y));
                    }
                }
                sets.add(set);
            }
        }
        return sets;
    }

    /**
     * Get all strings that can be formed by applying the mask to the grid.
     *
     * @param grid           The grid to apply the mask to.
     * @param includeReverse Whether to include the reverse of each string.
     * @return A list of strings that can be formed by applying the mask to the grid.
     */
    public List<String> getAllStrings(Grid grid, boolean includeReverse) {
        var strings = new ArrayList<String>();
        for (var set : getAllCharSets(grid)) {
            var word = set.stream().map(String::valueOf).reduce("", String::concat);
            strings.add(word);
            if (includeReverse) {
                var reverse = new StringBuilder(word).reverse().toString();
                strings.add(reverse);
            }
        }
        return strings;
    }

    /**
     * Rotate the mask 90 degrees to the right.
     *
     * @return A new mask that is rotated 90 degrees to the right.
     */
    public LinearMask rotateRight() {
        var newMask = new ArrayList<Vector2>();
        for (var vector : mask) {
            newMask.add(Vector2.of(vector.y(), -vector.x()));
        }
        return normalized(newMask);
    }

    /**
     * Normalize the mask so that the top-left corner is at (0, 0).
     *
     * @param newMask The mask to normalize.
     * @return A new mask that is normalized.
     */
    private static LinearMask normalized(ArrayList<Vector2> newMask) {
        var minX = newMask.stream().map(Vector2::x).min(Integer::compareTo).get();
        var minY = newMask.stream().map(Vector2::y).min(Integer::compareTo).get();
        var normalizedMask = newMask.stream().map(v -> Vector2.of(v.x() - minX, v.y() - minY)).toList();
        return new LinearMask(normalizedMask);
    }
}
