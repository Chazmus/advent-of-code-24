/*
 * Copyright 2024 ForgeRock AS. All Rights Reserved
 *
 * Use of this code requires a commercial software license with ForgeRock AS.
 * or with one of its affiliates. All use shall be exclusively subject
 * to such license between the licensee and ForgeRock AS.
 */
package problems.day15;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.provider.Arguments;

import problems.ProblemBase;
import problems.day8.Day8;
import utils.Direction;
import utils.Grid;
import utils.Pair;
import utils.Vector2;

public class Day15 extends ProblemBase {

    Set<Vector2> walls;
    Set<Vector2> boxes;
    Set<BigBox> bigBoxes;
    Vector2 robot;

    @BeforeEach
    public void setup() {
        walls = new HashSet<>();
        boxes = new HashSet<>();
        robot = new Vector2(0, 0);
        bigBoxes = new HashSet<>();
    }

    @Override
    public Long solvePart1(List<String> inputArray) {
        Input input = getResult(inputArray);
        var map = input.map();
        var instructions = input.instructionSet();

        map.getStreamOfCells().forEach(cell -> {
            if (cell.value().equals('#')) {
                walls.add(cell.position());
            } else if (cell.value().equals('@')) {
                robot = cell.position();
            } else if (cell.value().equals('O')) {
                boxes.add(cell.position());
            }
        });

        for (var instruction : instructions.toCharArray()) {
            var direction = Direction.from(instruction);
            moveRobot(direction);
        }

        return calculateScore();
    }

    @Override
    public Long solvePart2(List<String> inputArray) {
        Input input = getResult(inputArray);
        var map = input.map();
        var instructions = input.instructionSet();
        processDoubleBig(map);
//        printMap(new Vector2(map.getRows() * 2, map.getColumns()));

        for (var instruction : instructions.toCharArray()) {
            var direction = Direction.from(instruction);
            moveRobot(direction);
        }
        return 0L;
    }

    public void printMap(Vector2 size) {
        var newMap = new Grid(size.x(), size.y(), '.');
        walls.forEach(position -> newMap.set(position, '#'));
        bigBoxes.forEach(bigBox -> {
            newMap.set(bigBox.position.first(), '[');
            newMap.set(bigBox.position.second(), ']');
        });
        newMap.print();
    }

    public void processDoubleBig(Grid map) {
        map.getStreamOfCells().forEach(cell -> {
            var expandedPosition = new Vector2(cell.position().x() * 2, cell.position().y());
            var expandedPosition2 = expandedPosition.add(new Vector2(1, 0));

            if (cell.value().equals('#')) {
                walls.add(expandedPosition);
                walls.add(expandedPosition2);
            }

            if (cell.value().equals('@')) {
                robot = expandedPosition;
            }

            if (cell.value().equals('O')) {
                bigBoxes.add(new BigBox(expandedPosition, expandedPosition2));
            }
        });
    }


    private Long calculateScore() {
        return boxes.stream()
                .mapToLong(box -> box.x() + (100L * box.y()))
                .sum();
    }

    private void moveRobot(Direction direction) {
        var newPosition = robot.add(direction.toVector());
        if (walls.contains(newPosition)) {
            return;
        }

        var boxesToMove = new ArrayList<Vector2>();
        while (boxes.contains(newPosition)) {
            boxesToMove.add(newPosition);
            newPosition = newPosition.add(direction.toVector());
        }
        if (walls.contains(newPosition)) {
            return;
        }

        if (!boxesToMove.isEmpty()) {
            boxes.remove(boxesToMove.getFirst());
            boxes.add(boxesToMove.getLast().add(direction.toVector()));
        }

        robot = robot.add(direction.toVector());
    }

    private static Input getResult(List<String> inputArray) {
        var mapList = new ArrayList<String>();
        var mapDone = false;
        var instructionSet = new StringBuilder();
        for (var line : inputArray) {
            if ("".equals(line)) {
                mapDone = true;
                continue;
            }
            if (!mapDone) {
                mapList.add(line);
            } else {
                instructionSet.append(line);
            }
        }
        return new Input(new Grid(mapList), instructionSet.toString());
    }


    @Override
    public Stream<Arguments> getPart1Examples() {
        return getArgumentsStream();
    }

    private static Stream<Arguments> getArgumentsStream() {
        return Stream.of(
                Arguments.of("""
                        ########
                        #..O.O.#
                        ##@.O..#
                        #...O..#
                        #.#.O..#
                        #...O..#
                        #......#
                        ########
                        
                        <^^>>>vv<v>>v<<""", 2028L),
                Arguments.of("""
                        ##########
                        #..O..O.O#
                        #......O.#
                        #.OO..O.O#
                        #..O@..O.#
                        #O#..O...#
                        #O..O..O.#
                        #.OO.O.OO#
                        #....O...#
                        ##########
                        
                        <vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^
                        vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v
                        ><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<
                        <<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^
                        ^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><
                        ^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^
                        >^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^
                        <><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>
                        ^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>
                        v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^""", 10092L)
        );
    }

    @Override
    public Stream<Arguments> getPart2Examples() {
        return getArgumentsStream();
    }

    private record Input(Grid map, String instructionSet) {
    }

    private class BigBox {
        private Pair<Vector2> position;

        public BigBox(Vector2 left, Vector2 right) {
            this.position = Pair.of(left, right);
        }
    }
}
