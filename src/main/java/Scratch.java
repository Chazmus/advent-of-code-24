import java.util.ArrayList;
import java.util.List;

public class Scratch {

    static int sum(List<Integer> numbers) {
        var sum = 0;
        for (var number : numbers) {
            sum += number;
        }
        return sum;
    }

    public static class Part1 extends Part {
        int solve(List<String> inputArray) {
            var numbers = new ArrayList<Integer>();
            for (var line : inputArray) {
                Character firstDigit = null;
                Character lastDigit = null;
                for (var c : line.toCharArray()) {
                    if (Character.isDigit(c)) {
                        lastDigit = c;
                        if (firstDigit == null) {
                            firstDigit = c;
                        }
                    }
                }
                numbers.add(Integer.parseInt(firstDigit.toString() + lastDigit.toString()));
            }
            return sum(numbers);
        }
    }

    public static class Part2 extends Part {
        int solve(List<String> inputArray) {
            return 0;
        }
    }
}
