/*
 * Copyright 2024 ForgeRock AS. All Rights Reserved
 *
 * Use of this code requires a commercial software license with ForgeRock AS.
 * or with one of its affiliates. All use shall be exclusively subject
 * to such license between the licensee and ForgeRock AS.
 */
package problems.day12;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.provider.Arguments;

import problems.ProblemBase;
import utils.Grid;

public class Day12 extends ProblemBase {

    Set<Grid.Cell> visited;
    Set<Plot> plots;

    @BeforeEach
    public void setup() {
        visited = new HashSet<>();
        plots = new HashSet<>();
    }

    @Override
    public Long solvePart1(List<String> inputArray) {
        var grid = new Grid(inputArray);
        grid.getStreamOfCells().forEach(cell -> {
            if (visited.contains(cell)) {
                return;
            }
            plots.add(new Plot(cell, grid));
        });
        return 0L;
    }

    @Override
    public Long solvePart2(List<String> inputArray) {
        return 0L;
    }

    static class Plot {
        Set<Grid.Cell> cells = new HashSet<>();
        int perimeter;

        Plot(Grid.Cell startingCell, Grid grid) {
            cells.add(startingCell);
            walkPlot(startingCell, grid);
        }

        private void walkPlot(Grid.Cell startingCell, Grid grid) {
            startingCell.position().getCardinalNeighbours().forEach(neighbourVector -> {
                if (grid.isWithinBounds(neighbourVector)) {
                    var neighbourCell = grid.getCell(neighbourVector);
                    if (neighbourCell.value() == startingCell.value()) {
                        if (cells.contains(neighbourCell)) {
                            return;
                        }
                        cells.add(neighbourCell);
                        walkPlot(neighbourCell, grid);
                    } else {
                        // different plot
                        perimeter++;
                    }

                } else {
                    // at the edge of the grid
                    perimeter++;
                }
            });
        }

    }

    @Override
    public Stream<Arguments> getPart1Examples() {
        return Stream.of(
                Arguments.of("""
                        AAAA
                        BBCD
                        BBCC
                        EEEC
                        """, 140L),
                Arguments.of("""
                        OOOOO
                        OXOXO
                        OOOOO
                        OXOXO
                        OOOOO
                        """, 772L),
                Arguments.of("""
                        RRRRIICCFF
                        RRRRIICCCF
                        VVRRRCCFFF
                        VVRCCCJFFF
                        VVVVCJJCFE
                        VVIVCCJJEE
                        VVIIICJJEE
                        MIIIIIJJEE
                        MIIISIJEEE
                        MMMISSJEEE
                        """, 1930L)
        );
    }

    @Override
    public Stream<Arguments> getPart2Examples() {
        return Stream.empty();
    }
}
