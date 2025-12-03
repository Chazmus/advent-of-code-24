/*
 * Copyright 2024 ForgeRock AS. All Rights Reserved
 *
 * Use of this code requires a commercial software license with ForgeRock AS.
 * or with one of its affiliates. All use shall be exclusively subject
 * to such license between the licensee and ForgeRock AS.
 */
package year2024.day8;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import utils.ProblemBase;
import utils.Grid;
import utils.Pair;
import utils.Vector2;

public class Day8 extends ProblemBase {
    public Day8() {
        super("2024");
    }

    @Override
    public Long solvePart1(List<String> inputArray) {
        var grid = new Grid(inputArray);
        var nonEmptyCells = grid.getStreamOfCells()
                .filter(cell -> cell.value() != '.').toList();

        Map<Character, List<Vector2>> cellsToListOfPositions = new HashMap<>();
        for (var cell : nonEmptyCells) {
            cellsToListOfPositions.putIfAbsent(cell.value(), List.of());
            cellsToListOfPositions.put(cell.value(), Stream.concat(cellsToListOfPositions.get(cell.value()).stream(),
                    Stream.of(cell.position())).toList());
        }

        var antinodesSet = new HashSet<Vector2>();
        for (var entry : cellsToListOfPositions.entrySet()) {
            entry.getValue().forEach(positionA -> {
                entry.getValue().forEach(positionB -> {
                    if (positionA.equals(positionB)) {
                        return;
                    }
                    var antinodes = calculateAntinodes(positionA, positionB);
                    if (grid.isWithinBounds(antinodes.first())) {
                        antinodesSet.add(antinodes.first());
                    }
                    if (grid.isWithinBounds(antinodes.second())) {
                        antinodesSet.add(antinodes.second());
                    }
                });
            });
        }

        return (long) antinodesSet.size();
    }

    public void calculateAntinodes(Vector2 a, Vector2 b, Grid grid, HashSet<Vector2> antinodesSet) {
        Vector2 directionBetween = b.subtract(a);
        Vector2 antinode1 = b.add(directionBetween);
        Vector2 antinode2 = a.subtract(directionBetween);
        while (grid.isWithinBounds(antinode1)) {
            antinodesSet.add(antinode1);
            antinode1 = antinode1.add(directionBetween);
        }
        while (grid.isWithinBounds(antinode2)) {
            antinodesSet.add(antinode2);
            antinode2 = antinode2.subtract(directionBetween);
        }
    }

    public Pair<Vector2> calculateAntinodes(Vector2 a, Vector2 b) {
        Vector2 directionBetween = b.subtract(a);
        Vector2 antinode1 = b.add(directionBetween);
        Vector2 antinode2 = a.subtract(directionBetween);
        return new Pair<>(antinode1, antinode2);
    }

    @Override
    public Long solvePart2(List<String> inputArray) {
        var grid = new Grid(inputArray);
        var nonEmptyCells = grid.getStreamOfCells()
                .filter(cell -> cell.value() != '.').toList();

        Map<Character, List<Vector2>> cellsToListOfPositions = new HashMap<>();
        for (var cell : nonEmptyCells) {
            cellsToListOfPositions.putIfAbsent(cell.value(), List.of());
            cellsToListOfPositions.put(cell.value(), Stream.concat(cellsToListOfPositions.get(cell.value()).stream(),
                    Stream.of(cell.position())).toList());
        }

        var antinodesSet = new HashSet<Vector2>();
        for (var entry : cellsToListOfPositions.entrySet()) {
            entry.getValue().forEach(positionA -> {
                entry.getValue().forEach(positionB -> {
                    if (positionA.equals(positionB)) {
                        antinodesSet.add(positionA);
                        return;
                    }
                    calculateAntinodes(positionA, positionB, grid, antinodesSet);
                });
            });
        }

        return (long) antinodesSet.size();
    }

    @Override
    public Stream<Arguments> getPart1Examples() {
        return Stream.of(Arguments.of("""
                ............
                ........0...
                .....0......
                .......0....
                ....0.......
                ......A.....
                ............
                ............
                ........A...
                .........A..
                ............
                ............
                """, 14L));
    }

    @Override
    public Stream<Arguments> getPart2Examples() {
        return Stream.of(Arguments.of("""
                ............
                ........0...
                .....0......
                .......0....
                ....0.......
                ......A.....
                ............
                ............
                ........A...
                .........A..
                ............
                ............
                """, 34L));
    }

}
