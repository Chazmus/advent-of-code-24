package problems.day7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import problems.ProblemBase;

public class Day7 extends ProblemBase {

    private enum Operator implements BiFunction<Long, Long, Long> {

        ADD(Long::sum),
        MULTIPLY((a, b) -> a * b),
        CONCAT((a, b) -> Long.valueOf("%d%d".formatted(a, b)));

        private final BiFunction<Long, Long, Long> function;

        Operator(BiFunction<Long, Long, Long> function) {
            this.function = function;
        }

        @Override
        public Long apply(Long aLong, Long aLong2) {
            return function.apply(aLong, aLong2);
        }
    }

    @Override
    public Long solvePart1(List<String> inputArray) {
        return solve(inputArray, List.of(Operator.ADD, Operator.MULTIPLY));
    }

    @Override
    public Long solvePart2(List<String> inputArray) {
        return solve(inputArray, List.of(Operator.ADD, Operator.MULTIPLY, Operator.CONCAT));
    }

    private Long solve(List<String> inputArray, List<Operator> operators) {
        var total = 0L;
        for (var line : inputArray) {
            var parts = line.split(": ");
            var testValue = Long.valueOf(parts[0]);
            var equationNumbers = Arrays.stream(parts[1].split(" ")).map(Long::valueOf).toList();
            var testCases = generateCombinations(equationNumbers.size() - 1, operators);
            long result = getAnyResult(testValue, equationNumbers, testCases);
            if (result != 0) {
                total += result;
            }
        }
        return total;
    }

    private Long getAnyResult(Long testValue, List<Long> equationNumbers,
            List<List<Operator>> testCases) {
        for (var testCase : testCases) {
            Long result = equationNumbers.getFirst();
            for (int i = 0; i < testCase.size(); i++) {
                result = testCase.get(i).apply(result, equationNumbers.get(i + 1));
                if (result > testValue) {
                    break;
                }
            }

            if (result.equals(testValue)) {
                return result;
            }
        }
        return 0L;
    }

    private static List<List<Operator>> generateCombinations(int length, List<Operator> operators) {
        int numCombinations = (int) Math.pow(operators.size(), length);
        List<List<Operator>> combinations = new ArrayList<>();
        for (int combinationIndex = 0; combinationIndex < numCombinations; combinationIndex++) {
            List<Operator> combination = new ArrayList<>();
            int remainingIndex = combinationIndex;
            for (int position = 0; position < length; position++) {
                Operator operator = operators.get(remainingIndex % operators.size());
                combination.add(operator);
                remainingIndex /= operators.size();
            }
            combinations.add(combination);
        }
        return combinations;
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
