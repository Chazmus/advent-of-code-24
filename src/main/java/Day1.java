import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day1 {

    public static class Part1 extends Part {
        int solve(List<String> inputArray) {
            var leftNumbers = new ArrayList<Integer>();
            var rightNumbers = new ArrayList<Integer>();
            for (var line : inputArray) {
                if (line.isEmpty()) {
                    continue;
                }
                var numbers = line.split(" ");
                leftNumbers.add(Integer.parseInt(numbers[0]));
                rightNumbers.add(Integer.parseInt(numbers[numbers.length-1]));
            }

            var sortedLeft = leftNumbers.stream().sorted().toList();
            var sortedRight = rightNumbers.stream().sorted().toList();

            var sum = 0;
            for (var i = 0; i < sortedLeft.size(); i++) {

                var left = sortedLeft.get(i);
                var right = sortedRight.get(i);
                var diff = Math.abs(left - right);
                sum += diff;
            }

            return sum;
        }
    }

    public static class Part2 extends Part {
        int solve(List<String> inputArray) {
            var leftNumbers = new ArrayList<Integer>();
            var rightMapOfFrequency = new HashMap<Integer, Integer>();
            for (var line : inputArray) {
                if (line.isEmpty()) {
                    continue;
                }
                var numbers = line.split(" ");
                leftNumbers.add(Integer.parseInt(numbers[0]));
                rightMapOfFrequency.put(Integer.parseInt(numbers[numbers.length-1]),
                        rightMapOfFrequency.getOrDefault(Integer.parseInt(numbers[numbers.length-1]), 0) + 1);
            }

            var sum = 0;
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
    }
}
