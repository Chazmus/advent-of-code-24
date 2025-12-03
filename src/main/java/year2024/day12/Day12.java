/*
 * Copyright 2024 ForgeRock AS. All Rights Reserved
 *
 * Use of this code requires a commercial software license with ForgeRock AS.
 * or with one of its affiliates. All use shall be exclusively subject
 * to such license between the licensee and ForgeRock AS.
 */
package year2024.day12;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.provider.Arguments;

import utils.ProblemBase;
import utils.Direction;
import utils.Grid;
import utils.Vector2;

public class Day12 extends ProblemBase {

    Set<Grid.Cell> visited;
    Set<Plot> plots;

    public Day12() {
        super("2024");
    }

    @BeforeEach
    public void setup() {
        visited = new HashSet<>();
        plots = new HashSet<>();
    }

    @Override
    public Long solvePart1(List<String> inputArray) {
        calculatePlots(inputArray);
        return plots.stream().mapToLong(plot -> (long) plot.cells.size() * plot.perimeter).sum();
    }

    private void calculatePlots(List<String> inputArray) {
        var grid = new Grid(inputArray);
        grid.getStreamOfCells().forEach(cell -> {
            if (visited.contains(cell)) {
                return;
            }
            plots.add(new Plot(cell, grid, visited));
        });
    }

    @Override
    public Long solvePart2(List<String> inputArray) {
        calculatePlots(inputArray);
        plots.forEach(Plot::calculateStraightLinePerimeters);
        return plots.stream().mapToLong(plot -> (long) plot.cells.size() * plot.straightPerimeter).sum();
    }

    static class Plot {
        Character type;
        Set<Grid.Cell> cells = new HashSet<>();
        Set<Grid.Cell> allVisited;
        int perimeter;
        int straightPerimeter;
        Set<PerimeterEdge> perimeterEdges = new HashSet<>();

        Plot(Grid.Cell startingCell, Grid grid, Set<Grid.Cell> allVisited) {
            type = startingCell.value();
            cells.add(startingCell);
            this.allVisited = allVisited;
            this.allVisited.add(startingCell);
            walkPlot(startingCell, grid);
        }

        private void walkPlot(Grid.Cell startingCell, Grid grid) {
            startingCell.position().getCardinalNeighbours().forEach(neighbourVector -> {
                if (grid.isWithinBounds(neighbourVector)) {
                    var neighbourCell = grid.getCell(neighbourVector);
                    if (neighbourCell.value() == type) {
                        if (cells.contains(neighbourCell) || allVisited.contains(neighbourCell)) {
                            return;
                        }
                        cells.add(neighbourCell);
                        allVisited.add(neighbourCell);
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

        public void calculateStraightLinePerimeters() {
            cells.forEach(cell -> cell.position().getCardinalNeighbours().forEach(neighbourVector -> {
                if (cells.stream().map(Grid.Cell::position).anyMatch(v -> v.equals(neighbourVector))) {
                    return;
                }
                // At a perimeter
                perimeterEdges.add(new PerimeterEdge(cell.position(),
                        Direction.fromVector(neighbourVector.subtract(cell.position()))));
            }));
            Set<Set<PerimeterEdge>> allStraightEdges = new HashSet<>();
            var initializedStraightEdge = new HashSet<PerimeterEdge>();
            perimeterEdges.stream()
                    .map(singleEdge -> getStraightEdge(singleEdge, initializedStraightEdge,
                            perimeterEdges, allStraightEdges))
                    .filter(Objects::nonNull)
                    .forEach(allStraightEdges::add);
            straightPerimeter = allStraightEdges.size();
        }

        private Set<PerimeterEdge> getStraightEdge(PerimeterEdge singleEdge, HashSet<PerimeterEdge> straightEdge,
                Set<PerimeterEdge> perimeterEdges, Set<Set<PerimeterEdge>> allStraightEdges) {
            if (allStraightEdges.stream().anyMatch(set -> set.contains(singleEdge))) {
                return null;
            }
            straightEdge.add(singleEdge);
            switch (singleEdge.direction()) {
                case UP, DOWN -> {
                    var possibleNeighbour1 = new PerimeterEdge(singleEdge.location().add(Direction.LEFT.toVector()),
                            singleEdge.direction());
                    var possibleNeighbour2 = new PerimeterEdge(singleEdge.location().add(Direction.RIGHT.toVector()),
                            singleEdge.direction());
                    if (perimeterEdges.contains(possibleNeighbour1) && !straightEdge.contains(possibleNeighbour1)) {
                        getStraightEdge(possibleNeighbour1, straightEdge, perimeterEdges, allStraightEdges);
                    }
                    if (perimeterEdges.contains(possibleNeighbour2)) {
                        getStraightEdge(possibleNeighbour2, straightEdge, perimeterEdges, allStraightEdges);
                    }
                }
                case LEFT, RIGHT -> {
                    var possibleNeighbour1 = new PerimeterEdge(singleEdge.location().add(Direction.UP.toVector()),
                            singleEdge.direction());
                    var possibleNeighbour2 = new PerimeterEdge(singleEdge.location().add(Direction.DOWN.toVector()),
                            singleEdge.direction());
                    if (perimeterEdges.contains(possibleNeighbour1) && !straightEdge.contains(possibleNeighbour1)) {
                        getStraightEdge(possibleNeighbour1, straightEdge, perimeterEdges, allStraightEdges);
                    }
                    if (perimeterEdges.contains(possibleNeighbour2) && !straightEdge.contains(possibleNeighbour2)) {
                        getStraightEdge(possibleNeighbour2, straightEdge, perimeterEdges, allStraightEdges);
                    }
                }
            }
            return straightEdge;
        }


        record PerimeterEdge(Vector2 location, Direction direction) {
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
        return Stream.of(
                Arguments.of("""
                        AAAA
                        BBCD
                        BBCC
                        EEEC
                        """, 80L),
                Arguments.of("""
                        OOOOO
                        OXOXO
                        OOOOO
                        OXOXO
                        OOOOO
                        """, 436L),
                Arguments.of("""
                        EEEEE
                        EXXXX
                        EEEEE
                        EXXXX
                        EEEEE
                        """, 236L),
                Arguments.of("""
                        AAAAAA
                        AAABBA
                        AAABBA
                        ABBAAA
                        ABBAAA
                        AAAAAA
                        """, 368L),
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
                        """, 1206L)
        );
    }
}
