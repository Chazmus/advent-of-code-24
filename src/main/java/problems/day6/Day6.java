/*
 * Copyright 2024 ForgeRock AS. All Rights Reserved
 *
 * Use of this code requires a commercial software license with ForgeRock AS.
 * or with one of its affiliates. All use shall be exclusively subject
 * to such license between the licensee and ForgeRock AS.
 */
package problems.day6;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import problems.ProblemBase;
import utils.Direction;
import utils.Grid;
import utils.Vector2;

public class Day6 extends ProblemBase {
    class Guard {

        Vector2 position;
        Direction direction;
        Set<Vector2> visited;
        Grid map;

        public Guard(Grid map) {
            visited = new HashSet<>();
            map.find('^').ifPresent(position -> {
                this.position = position;
                this.direction = Direction.UP;
                this.visited.add(position);
            });
            this.map = map;
        }

        public void walk() {
            var target = position.add(direction.toVector());
            if (map.isOutOfBounds(target)) {
                // Check for the guard being out of bounds in the loop
                position = target;
                return;
            }
            while (map.get(target) == '#') {
                direction = direction.turnRight();
                target = position.add(direction.toVector());
            }

            position = target;
            visited.add(position);
        }

    }

    @Override
    public Long solvePart1(List<String> inputArray) {
        Grid map = new Grid(inputArray);
        Guard guard = new Guard(map);
        while (map.isWithinBounds(guard.position)) {
            guard.walk();
        }
        return (long) guard.visited.size();
    }

    @Override
    public Long solvePart2(List<String> inputArray) {
        return 0L;
    }

    @Override
    public Stream<Arguments> getPart1Examples() {
        return Stream.of(Arguments.of("""
                ....#.....
                .........#
                ..........
                ..#.......
                .......#..
                ..........
                .#..^.....
                ........#.
                #.........
                ......#...
                """, 41L));
    }

    @Override
    public Stream<Arguments> getPart2Examples() {
        return Stream.empty();
    }
}
