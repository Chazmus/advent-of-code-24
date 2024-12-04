package utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a window mask that can be applied to a grid.
 */
public class WindowMask {
    private final int width;
    private final int height;

    /**
     * Create a new window mask with the specified width and height.
     *
     * @param width  The width of the mask.
     * @param height The height of the mask.
     */
    public WindowMask(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Get all sub-grids that can be formed by applying the mask to the grid.
     *
     * @param grid The grid to apply the mask to.
     * @return A list of sub-grids that can be formed by applying the mask to the grid.
     */
    public List<Grid> getAllSubGrids(Grid grid) {
        var subGrids = new ArrayList<Grid>();
        for (var i = 0; i < grid.getColumns(); i++) {
            for (var j = 0; j < grid.getRows(); j++) {
                var subGrid = new Grid(width, height);
                for (var x = 0; x < width; x++) {
                    for (var y = 0; y < height; y++) {
                        var xCoord = i + x;
                        var yCoord = j + y;
                        if (grid.isWithinBounds(xCoord, yCoord)) {
                            subGrid.set(x, y, grid.get(xCoord, yCoord));
                        }
                    }
                }
                subGrids.add(subGrid);
            }
        }
        return subGrids;
    }
}
