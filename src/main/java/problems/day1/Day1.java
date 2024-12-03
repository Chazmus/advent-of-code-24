package problems.day1;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import problems.ProblemBase;

public class Day1 extends ProblemBase {
    @Override
    public Long solvePart1(List<String> inputArray) {
        var leftNumbers = new ArrayList<Integer>();
        var rightNumbers = new ArrayList<Integer>();
        for (var line : inputArray) {
            var numbers = line.split(" ");
            leftNumbers.add(Integer.parseInt(numbers[0]));
            rightNumbers.add(Integer.parseInt(numbers[numbers.length - 1]));
        }

        var sortedLeft = leftNumbers.stream().sorted().toList();
        var sortedRight = rightNumbers.stream().sorted().toList();

        var sum = 0L;
        for (var i = 0; i < sortedLeft.size(); i++) {
            var left = sortedLeft.get(i);
            var right = sortedRight.get(i);
            var diff = Math.abs(left - right);
            sum += diff;
        }

        return sum;
    }

    @Override
    public Long solvePart2(List<String> inputArray) {
        var leftNumbers = new ArrayList<Integer>();
        var rightMapOfFrequency = new HashMap<Integer, Integer>();
        for (var line : inputArray) {
            var numbers = line.split(" ");
            leftNumbers.add(Integer.parseInt(numbers[0]));
            rightMapOfFrequency.put(Integer.parseInt(numbers[numbers.length - 1]),
                    rightMapOfFrequency.getOrDefault(Integer.parseInt(numbers[numbers.length - 1]), 0) + 1);
        }

        var sum = 0L;
        for (var i = 0; i < leftNumbers.size(); i++) {
            var left = leftNumbers.get(i);
            var right = rightMapOfFrequency.getOrDefault(left, 0);
            if (right == 0) {
                continue;
            }
            var diff = Math.abs(left * right);
            sum += diff;
        }

        return sum;
    }

    @Override
    public Stream<Map.Entry<String, Long>> getPart1Examples() {
        var input = """
                3   4
                4   3
                2   5
                1   3
                3   9
                3   3
                """;
        var expected = 11L;
        return Stream.of(Map.entry(input, expected));
    }

    @Override
    public Stream<Map.Entry<String, Long>> getPart2Examples() {
        String input = """
                3   4
                4   3
                2   5
                1   3
                3   9
                3   3
                """;
        var expected = 31L;
        return Stream.of(Map.entry(input, expected));
    }
}
