package problems.day7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import problems.ProblemBase;

public class Day7 extends ProblemBase {

    enum Part1Operators implements BiFunction<Long, Long, Long> {

        ADD(Long::sum),
        MULTIPLY((a, b) -> a * b),
        ;

        private final BiFunction<Long, Long, Long> function;

        Part1Operators(BiFunction<Long, Long, Long> function) {
            this.function = function;
        }

        @Override
        public Long apply(Long aLong, Long aLong2) {
            return function.apply(aLong, aLong2);
        }
    }

    enum Part2Operators implements BiFunction<Long, Long, Long> {

        ADD(Long::sum),
        MULTIPLY((a, b) -> a * b),
        CONCAT((a, b) -> Long.valueOf(a + String.valueOf(b))),
        ;

        private final BiFunction<Long, Long, Long> function;

        Part2Operators(BiFunction<Long, Long, Long> function) {
            this.function = function;
        }

        @Override
        public Long apply(Long aLong, Long aLong2) {
            return function.apply(aLong, aLong2);
        }
    }

    @Override
    public Long solvePart1(List<String> inputArray) {
        var total = 0L;
        for (var line : inputArray) {
            var parts = line.split(": ");
            var testValue = Long.valueOf(parts[0]);
            var equationNumbers = Arrays.stream(parts[1].split(" ")).map(Long::valueOf).toList();
            var testCases = generateCombinations(equationNumbers.size() - 1, Part1Operators.class);
            long result = getAnyResult(testValue, equationNumbers, testCases);
            if (result != 0) {
                total += result;
            }
        }

        return total;
    }

    @Override
    public Long solvePart2(List<String> inputArray) {
        var total = 0L;
        for (var line : inputArray) {
            var parts = line.split(": ");
            var testValue = Long.valueOf(parts[0]);
            var equationNumbers = Arrays.stream(parts[1].split(" ")).map(Long::valueOf).toList();
            var testCases = generateCombinations(equationNumbers.size() - 1, Part2Operators.class);
            long result = getAnyResult(testValue, equationNumbers, testCases);
            if (result != 0) {
                total += result;
            }
        }

        return total;
    }

    private Long getAnyResult(Long testValue, List<Long> equationNumbers, List<List<BiFunction<Long, Long, Long>>> testCases) {
        for (var testCase : testCases) {
            Long result = equationNumbers.getFirst();
            for (int i = 0; i < testCase.size(); i++) {
                result = testCase.get(i).apply(result, equationNumbers.get(i + 1));
            }

            if (result.equals(testValue)) {
                return result;
            }
        }
        return 0L;
    }


    public static <E extends Enum<E> & BiFunction<Long, Long, Long>> List<List<BiFunction<Long, Long,
            Long>>> generateCombinations(int length,
            Class<E> operatorsEnum) {
        List<List<BiFunction<Long, Long, Long>>> result = new ArrayList<>();
        E[] values = operatorsEnum.getEnumConstants();
        int totalCombinations = (int) Math.pow(values.length, length);
        for (int i = 0; i < totalCombinations; i++) {
            List<BiFunction<Long, Long, Long>> combination = new ArrayList<>();
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
