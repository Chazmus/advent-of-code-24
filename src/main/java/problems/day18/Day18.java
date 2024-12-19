/*
 * Copyright 2024 ForgeRock AS. All Rights Reserved
 *
 * Use of this code requires a commercial software license with ForgeRock AS.
 * or with one of its affiliates. All use shall be exclusively subject
 * to such license between the licensee and ForgeRock AS.
 */
package problems.day18;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import problems.ProblemBase;
import utils.Grid;
import utils.Vector2;

public class Day18 extends ProblemBase {
    @Override
    public Long solvePart1(List<String> inputArray) {
        var input = inputArray.subList(0, 1023);
        var grid = new Grid(71, 71, '.');
        input.stream()
                .forEach(line -> {
                    var coordStr = line.split(",");
                    var x = Integer.parseInt(coordStr[0]);
                    var y = Integer.parseInt(coordStr[1]);
                    grid.set(x, y, '#');
                });
        var start = new Vector2(0, 0);
        var end = new Vector2(70, 70);
        return (long) grid.findShortestPath(start, end);
    }

    @Override
    public Long solvePart2(List<String> inputArray) {
        var grid = new Grid(71, 71, '.');
        AtomicInteger finalX = new AtomicInteger();
        AtomicInteger finalY = new AtomicInteger();
        AtomicBoolean found = new AtomicBoolean(false);
        inputArray.stream()
                .forEach(line -> {
                    if (found.get()) {
                        return;
                    }
                    var coordStr = line.split(",");
                    var x = Integer.parseInt(coordStr[0]);
                    var y = Integer.parseInt(coordStr[1]);
                    grid.set(x, y, '#');
                    var start = new Vector2(0, 0);
                    var end = new Vector2(70, 70);
                    if (grid.findShortestPath(start, end) == null) {
                        finalX.set(x);
                        finalY.set(y);
                        found.set(true);
                    }
                });
        var xStr = finalX.toString();
        var yStr = finalY.toString();
        return Long.parseLong(xStr + yStr);
    }

    @Override
    public Stream<Arguments> getPart1Examples() {
        return Stream.of(
                Arguments.of("""
                        5,4
                        4,2
                        4,5
                        3,0
                        2,1
                        6,3
                        2,4
                        1,5
                        0,6
                        3,3
                        2,6
                        5,1""", 22L)
        );
    }

    @Override
    public Stream<Arguments> getPart2Examples() {
        return Stream.empty();
    }
}
