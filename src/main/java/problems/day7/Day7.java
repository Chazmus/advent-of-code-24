package problems.day7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import problems.ProblemBase;

public class Day7 extends ProblemBase {

    enum Part1Operators {

        ADD(Long::sum),
        MULTIPLY((a, b) -> a * b),
        ;

        public BiFunction<Long, Long, Long> function;

        Part1Operators(BiFunction<Long, Long, Long> function) {
            this.function = function;
        }
    }

    enum Part2Operators {

        ADD(Long::sum),
        MULTIPLY((a, b) -> a * b),
        CONCAT((a, b) -> Long.valueOf(a + String.valueOf( b))),
        ;

        public BiFunction<Long, Long, Long> function;

        Part2Operators(BiFunction<Long, Long, Long> function) {
            this.function = function;
        }
    }

    @Override
    public Long solvePart1(List<String> inputArray) {
        var total = 0L;
        for (var line : inputArray) {
            var parts = line.split(": ");
            var testValue = Long.valueOf(parts[0]);
            var equationNumbers = Arrays.stream(parts[1].split(" ")).map(Long::valueOf).toList();
            var testCases = generateCombinations(equationNumbers.size() - 1);
            Long result = getAnyResult(testValue, equationNumbers, testCases);
            if (result != 0) {
                total += result;
            }
        }

        return total;
    }

    private Long getAnyResult(Long testValue, List<Long> equationNumbers, List<List<Part1Operators>> testCases) {
        for (var testCase : testCases) {
            Long result = equationNumbers.get(0);
            for (int i = 0; i < testCase.size(); i++) {
                result = testCase.get(i).function.apply(result, equationNumbers.get(i + 1));
            }

            if (result.equals(testValue)) {
                return result;
            }
        }
        return 0L;
    }
    private Long getAnyResultPart2(Long testValue, List<Long> equationNumbers, List<List<Part2Operators>> testCases) {
        for (var testCase : testCases) {
            Long result = equationNumbers.get(0);
            for (int i = 0; i < testCase.size(); i++) {
                result = testCase.get(i).function.apply(result, equationNumbers.get(i + 1));
            }

            if (result.equals(testValue)) {
                return result;
            }
        }
        return 0L;
    }


    public static List<List<Part1Operators>> generateCombinations(int length) {
        List<List<Part1Operators>> result = new ArrayList<>();
        Part1Operators[] values = Part1Operators.values();
        int totalCombinations = (int) Math.pow(values.length, length);
        for (int i = 0; i < totalCombinations; i++) {
            List<Part1Operators> combination = new ArrayList<>();
            int temp = i;
            for (int j = 0; j < length; j++) {
                combination.add(values[temp % values.length]);
                temp /= values.length;
            }
            result.add(combination);
        }
        return result;
    }

    public static List<List<Part2Operators>> generatePart2Combinations(int length) {
        List<List<Part2Operators>> result = new ArrayList<>();
        Part2Operators[] values = Part2Operators.values();
        int totalCombinations = (int) Math.pow(values.length, length);
        for (int i = 0; i < totalCombinations; i++) {
            List<Part2Operators> combination = new ArrayList<>();
            int temp = i;
            for (int j = 0; j < length; j++) {
                combination.add(values[temp % values.length]);
                temp /= values.length;
            }
            result.add(combination);
        }
        return result;
    }


    @Override
    public Long solvePart2(List<String> inputArray) {
        var total = 0L;
        for (var line : inputArray) {
            var parts = line.split(": ");
            var testValue = Long.valueOf(parts[0]);
            var equationNumbers = Arrays.stream(parts[1].split(" ")).map(Long::valueOf).toList();
            var testCases = generatePart2Combinations(equationNumbers.size() - 1);
            Long result = getAnyResultPart2(testValue, equationNumbers, testCases);
            if (result != 0) {
                total += result;
            }
        }

        return total;
    }

    @Override
    public Stream<Arguments> getPart1Examples() {
        return Stream.of(Arguments.of("""
                190: 10 19
                3267: 81 40 27
                83: 17 5
                156: 15 6
                7290: 6 8 6 15
                161011: 16 10 13
                192: 17 8 14
                21037: 9 7 18 13
                292: 11 6 16 20""", 3749L));
    }

    @Override
    public Stream<Arguments> getPart2Examples() {
        return Stream.of(Arguments.of("""
                190: 10 19
                3267: 81 40 27
                83: 17 5
                156: 15 6
                7290: 6 8 6 15
                161011: 16 10 13
                192: 17 8 14
                21037: 9 7 18 13
                292: 11 6 16 20""", 11387L));
    }
}
