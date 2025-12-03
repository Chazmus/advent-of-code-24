package year2025.day1;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import utils.ProblemBase;

public class Day1 extends ProblemBase {
    public Day1() {
        super("2025");
    }

    @Override
    public Long solvePart1(List<String> inputArray) {
        var dialNum = 50;
        var totalZeros = 0L;
        for (var line : inputArray) {
            var turnAmount = Integer.valueOf(line.substring(1));
            if (line.startsWith("R")) {
                dialNum += turnAmount;
            } else {
                dialNum -= turnAmount;
            }
            dialNum = Math.floorMod(dialNum, 100);
            if (dialNum == 0) {
                totalZeros++;
            }
        }
        return totalZeros;
    }

    @Override
    public Long solvePart2(List<String> inputArray) {
        var dialNum = 50;
        var totalZeros = 0L;
        for (var line : inputArray) {
            var turnAmount = Integer.valueOf(line.substring(1));
            var startedAtZero = dialNum == 0;
            if (line.startsWith("R")) {
                dialNum += turnAmount;
            } else {
                dialNum -= turnAmount;
            }
            int numTurns = dialNum / 100;
            if (dialNum < 0 && !startedAtZero) {
                numTurns = Math.abs(numTurns) + 1;
            }
            if (dialNum != 100) {
                totalZeros += numTurns;
            }
            dialNum = Math.floorMod(dialNum, 100);
            if (dialNum == 0) {
                totalZeros++;
            }
        }
        return totalZeros;
    }

    @Override
    public Stream<Arguments> getPart1Examples() {
        var input = """
                L68
                L30
                R48
                L5
                R60
                L55
                L1
                L99
                R14
                L82
                """;
        var expected = 3L;
        return Stream.of(Arguments.of(input, expected));
    }

    @Override
    public Stream<Arguments> getPart2Examples() {
        var input = """
                L68
                L30
                R48
                L5
                R60
                L55
                L1
                L99
                R14
                L82
                """;
        var expected = 6L;
        return Stream.of(Arguments.of(input, expected));
    }
}
