/*
 * Copyright 2024 ForgeRock AS. All Rights Reserved
 *
 * Use of this code requires a commercial software license with ForgeRock AS.
 * or with one of its affiliates. All use shall be exclusively subject
 * to such license between the licensee and ForgeRock AS.
 */
package year2024.day13;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import utils.ProblemBase;
import utils.BigVector2;
import utils.Vector2;

public class Day13 extends ProblemBase {

    Pattern regex = Pattern.compile(".*X.(\\d+), Y.(\\d+)");
    static final Long SLIGHT_ERROR = 10000000000000L;

    public Day13() {
        super("2024");
    }

    @Override
    public Long solvePart1(List<String> inputArray) {
        Vector2 a = null;
        Vector2 b = null;
        Vector2 prize = null;
        List<Puzzle> puzzles = new ArrayList<>();
        for (var line : inputArray) {
            var matcher = regex.matcher(line);
            if (!matcher.find()) {
                puzzles.add(new Puzzle(a, b, prize));
                continue;
            }
            var x = matcher.group(1);
            var y = matcher.group(2);
            if (line.startsWith("Button A")) {
                a = Vector2.of(x, y);
            } else if (line.startsWith("Button B")) {
                b = Vector2.of(x, y);
            } else if (line.startsWith("Prize")) {
                prize = Vector2.of(x, y);
            }
        }

        // Add up all the solutions
        var total = 0L;
        for (var puzzle : puzzles) {
            var solution = puzzle.findSolution();
            if (solution.isPresent()) {
                total += solution.get();
            }
        }
        return total;
    }

    @Override
    public Long solvePart2(List<String> inputArray) {
        BigVector2 a = null;
        BigVector2 b = null;
        BigVector2 prize = null;
        List<BigPuzzle> puzzles = new ArrayList<>();
        for (var line : inputArray) {
            var matcher = regex.matcher(line);
            if (!matcher.find()) {
                puzzles.add(new BigPuzzle(a, b, prize));
                continue;
            }
            var x = matcher.group(1);
            var y = matcher.group(2);
            if (line.startsWith("Button A")) {
                a = BigVector2.of(x, y);
            } else if (line.startsWith("Button B")) {
                b = BigVector2.of(x, y);
            } else if (line.startsWith("Prize")) {
                prize = BigVector2.of(Long.parseLong(x) + SLIGHT_ERROR, Long.parseLong(y) + SLIGHT_ERROR);
//                prize = BigVector2.of(Long.parseLong(x), Long.parseLong(y));
            }
        }

        var total = 0L;
        for (var puzzle : puzzles) {
            var solution = puzzle.findSolution();
            if (solution.isPresent()) {
                total += solution.get();
            }
        }

        return total;
    }

    record Puzzle(Vector2 a, Vector2 b, Vector2 prize) {
        Optional<Integer> findSolution() {
            List<Vector2> possibleSolutions = new ArrayList<>();
            for (int pressA = 0; pressA < 100; pressA++) {
                for (int pressB = 0; pressB < 100; pressB++) {
                    var test = (a.multiply(pressA).add(b.multiply(pressB)));
                    if (test.equals(prize)) {
                        possibleSolutions.add(new Vector2(pressA, pressB));
                    }
                }
            }
            return possibleSolutions.stream().map(this::priceOfSolution).min(Integer::compare);
        }

        int priceOfSolution(Vector2 vector) {
            return vector.x() * 3 + vector.y();
        }
    }

    record BigPuzzle(BigVector2 a, BigVector2 b, BigVector2 prize) {
        Optional<Long> findSolution() {
            var ax = (double) a.x();
            var ay = (double) a.y();
            var bx = (double) b.x();
            var by = (double) b.y();
            var prizeX = (double) prize.x();
            var prizeY = (double) prize.y();

            var bPresses = (prizeY - (ay * prizeX) / ax) / (by - ((ay * bx) / ax));
            var aPresses = (prizeX - (bPresses * bx)) / ax;

            long aPressesLong = Math.round(aPresses);
            long bPressesLong = Math.round(bPresses);

            var testx = aPressesLong * a.x() + bPressesLong * b.x();
            var testy = aPressesLong * a.y() + bPressesLong * b.y();

            if(testx == prize.x() && testy == prize.y()) {
                return Optional.of(aPressesLong * 3 + bPressesLong);
            }

            return Optional.empty();
        }
    }

    @Override
    public Stream<Arguments> getPart1Examples() {
        return Stream.of(
                Arguments.of("""
                        Button A: X+94, Y+34
                        Button B: X+22, Y+67
                        Prize: X=8400, Y=5400
                        
                        Button A: X+26, Y+66
                        Button B: X+67, Y+21
                        Prize: X=12748, Y=12176
                        
                        Button A: X+17, Y+86
                        Button B: X+84, Y+37
                        Prize: X=7870, Y=6450
                        
                        Button A: X+69, Y+23
                        Button B: X+27, Y+71
                        Prize: X=18641, Y=10279
                        blah
                        """, 480L)
        );
    }

    @Override
    public Stream<Arguments> getPart2Examples() {
        return Stream.of(
                Arguments.of("""
                        Button A: X+94, Y+34
                        Button B: X+22, Y+67
                        Prize: X=8400, Y=5400
                        
                        Button A: X+26, Y+66
                        Button B: X+67, Y+21
                        Prize: X=12748, Y=12176
                        
                        Button A: X+17, Y+86
                        Button B: X+84, Y+37
                        Prize: X=7870, Y=6450
                        
                        Button A: X+69, Y+23
                        Button B: X+27, Y+71
                        Prize: X=18641, Y=10279
                        blah
                        """, 480L)
        );
    }
}
