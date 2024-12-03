package problems.day2;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import problems.ProblemBase;

public class Day2 extends ProblemBase {
    @Override
    public Long solvePart1(List<String> inputArray) {
        var totalSafe = 0L;
        for (var line : inputArray) {
            if (isSafe(line)) {
                totalSafe++;
            }
        }
        return totalSafe;
    }

    private boolean isSafe(String line) {
        var numbers = line.split(" ");
        var window = new int[]{0, 1};
        var result = true;

        var first = numbers[window[0]];
        var second = numbers[window[1]];

        // Check if equal
        if (first.equals(second)) {
            return false;
        }

        var incOrDec = "inc";
        // Check if the first number is greater than the second number
        var firstIsGreaterThanSecond = Integer.parseInt(first) > Integer.parseInt(second);
        if (firstIsGreaterThanSecond) {
            incOrDec = "dec";
        }

        // Find absolute diff
        var absDiff = Math.abs(Integer.parseInt(first) - Integer.parseInt(second));
        if (absDiff > 3) {
            return false;
        }

        window[0] = window[0] + 1;
        window[1] = window[1] + 1;
        while (result && window[1] < numbers.length) {
            result = check(numbers, window, incOrDec);
            window[0] = window[0] + 1;
            window[1] = window[1] + 1;
        }

        return result;
    }

    private static boolean check(String[] numbers, int[] window, String expectedIncOrDec) {
        var first = numbers[window[0]];
        var second = numbers[window[1]];
        // Check if equal
        if (first.equals(second)) {
            return false;
        }

        var incOrDec = "inc";
        // Check if the first number is greater than the second number
        var firstIsGreaterThanSecond = Integer.parseInt(first) > Integer.parseInt(second);
        if (firstIsGreaterThanSecond) {
            incOrDec = "dec";
        }
        if (!incOrDec.equals(expectedIncOrDec)) {
            return false;
        }

        // Find absolute diff
        var absDiff = Math.abs(Integer.parseInt(first) - Integer.parseInt(second));
        return absDiff <= 3;
    }

    private boolean isSafe2(String line) {

        var numbers = Arrays.stream(line.split(" ")).map(Integer::parseInt).toList();
        var allPossibleLines = new ArrayList<List<Integer>>();

        allPossibleLines.add(numbers);
        for (var i = 0; i < numbers.size(); i++) {
            var newList = new ArrayList<Integer>();
            newList.addAll(numbers.subList(0, i));
            newList.addAll(numbers.subList(i + 1, numbers.size()));
            allPossibleLines.add(newList);
        }

        var result = true;
        for (var lineOfNums : allPossibleLines) {
            result = true;
            var window = new int[]{0, 1};
            var first = lineOfNums.get(window[0]);
            var second = lineOfNums.get(window[1]);

            // Check if equal
            if (first.equals(second)) {
                result = false;
                continue;
            }

            var incOrDec = "inc";
            // Check if the first number is greater than the second number
            var firstIsGreaterThanSecond = first > second;
            if (firstIsGreaterThanSecond) {
                incOrDec = "dec";
            }

            // Find absolute diff
            var absDiff = Math.abs(first - second);
            if (absDiff > 3) {
                result = false;
                continue;
            }

            window[0] = window[0] + 1;
            window[1] = window[1] + 1;
            while (window[1] < lineOfNums.size()) {
                result = check2(lineOfNums, window, incOrDec);
                if (!result) {
                    break;
                }
                window[0] = window[0] + 1;
                window[1] = window[1] + 1;
            }
            if (result) {
                break;
            }
        }
        return result;
    }

    private static boolean check2(List<Integer> numbers, int[] window, String expectedIncOrDec) {
        var first = numbers.get(window[0]);
        var second = numbers.get(window[1]);
        // Check if equal
        if (first.equals(second)) {
            return false;
        }

        var incOrDec = "inc";
        // Check if the first number is greater than the second number
        var firstIsGreaterThanSecond = first > second;
        if (firstIsGreaterThanSecond) {
            incOrDec = "dec";
        }
        if (!incOrDec.equals(expectedIncOrDec)) {
            return false;
        }

        // Find absolute diff
        var absDiff = Math.abs(first - second);
        return absDiff <= 3;
    }


    @Override
    public Long solvePart2(List<String> inputArray) {
        var totalSafe = 0L;
        for (var line : inputArray) {
            if (isSafe2(line)) {
                totalSafe++;
            }
        }
        return totalSafe;
    }

    @Override
    public Stream<Map.Entry<String, Long>> getPart1Examples() {
        return Stream.of(Map.entry("""
                7 6 4 2 1
                1 2 7 8 9
                9 7 6 2 1
                1 3 2 4 5
                8 6 4 4 1
                1 3 6 7 9""", 2L));
    }

    @Override
    public Stream<Map.Entry<String, Long>> getPart2Examples() {
        return Stream.of(Map.entry("""
                7 6 4 2 1
                1 2 7 8 9
                9 7 6 2 1
                1 3 2 4 5
                8 6 4 4 1
                1 3 6 7 9""", 4L));
    }
}
