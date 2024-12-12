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
import utils.Direction;
import utils.Grid;
import utils.Vector2;

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
            cells.forEach(cell -> {
                cell.position().getCardinalNeighbours().forEach(neighbourVector -> {
                    if (cells.stream().map(Grid.Cell::position).anyMatch(v -> v.equals(neighbourVector))) {
                        return;
                    }
                    // At a perimeter
                    perimeterEdges.add(new PerimeterEdge(cell.position(),
                            Direction.fromVector(neighbourVector.subtract(cell.position()))));
                });
            });
            // Combine edges in a line
            var singleEdges = new HashSet<Set<PerimeterEdge>>();
            for (var edge : perimeterEdges) {
                if (singleEdges.stream().anyMatch(set -> set.contains(edge))) {
                    continue;
                }
                var edgeSet = new HashSet<PerimeterEdge>();
                edgeSet.add(edge);
                var direction = edge.direction;
                var found = false;
                switch (direction) {
                    case UP, DOWN -> {
                        var possibleNeighbor1 = new PerimeterEdge(edge.location().add(Direction.LEFT.toVector()),
                                direction);
                        var possibleNeighbor2 = new PerimeterEdge(edge.location().add(Direction.RIGHT.toVector()),
                                direction);

                        for (var singleEdgeSet : singleEdges) {
                            if (singleEdgeSet.contains(possibleNeighbor1) || singleEdgeSet.contains(possibleNeighbor2)) {
                                singleEdgeSet.add(edge);
                                found = true;
                                break;
                            }
                        }
                        if (found) {
                            break;
                        }

                        if (perimeterEdges.contains(possibleNeighbor1)) {
                            edgeSet.add(possibleNeighbor1);
                        }
                        if (perimeterEdges.contains(possibleNeighbor2)) {
                            edgeSet.add(possibleNeighbor2);
                        }
                    }
                    case LEFT, RIGHT -> {
                        var possibleNeighbor1 = new PerimeterEdge(edge.location().add(Direction.UP.toVector()),
                                direction);
                        var possibleNeighbor2 = new PerimeterEdge(edge.location().add(Direction.DOWN.toVector()),
                                direction);
                        if (perimeterEdges.contains(possibleNeighbor1)) {
                            edgeSet.add(possibleNeighbor1);
                        }
                        if (perimeterEdges.contains(possibleNeighbor2)) {
                            edgeSet.add(possibleNeighbor2);
                        }
                    }
                }
                if (!found) {
                    singleEdges.add(edgeSet);
                }
            }
            straightPerimeter = singleEdges.size();
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
//                Arguments.of("""
//                        AAAA
//                        BBCD
//                        BBCC
//                        EEEC
//                        """, 80L),
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
                        """, 1930L)
        );
    }
}
