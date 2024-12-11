package problems.day11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import problems.ProblemBase;

public class Day11 extends ProblemBase {

    Map<Integer, Integer> iterationToCountOfZeros = new HashMap<>();

    @Override
    public Long solvePart1(List<String> inputArray) {
        return solve(inputArray, 25);
    }

    @Override
    public Long solvePart2(List<String> inputArray) {
        return solve(inputArray, 75);
    }

    public Long solve(List<String> inputArray, int iterations) {
        var input = Arrays.stream(inputArray.get(0).split(" ")).toList();
        for (int i = 0; i < iterations; i++) {
            input = blink(input, i);
        }

        var finalResult = (long) input.size();
        // How many extra stones from zeroes
        for (var entry : iterationToCountOfZeros.entrySet()) {
            var iteration = entry.getValue();
            var numberOfZeroes = entry.getKey();
            var distanceFromEnd = iterations - iteration;
            var extraStones = (long) Math.floor((double) distanceFromEnd / 3) * numberOfZeroes;
            finalResult += extraStones;
        }

        return finalResult;
    }

    private List<String> blink(List<String> input, int iteration) {
        var result = new ArrayList<String>();
        for (int i = 0; i < input.size(); i++) {
            var current = input.get(i);
            if (current.equals("0")) {
//                iterationToCountOfZeros.put(iteration, iterationToCountOfZeros.getOrDefault(iteration, 0) + 1);
                result.add("1");
            } else if (current.length() % 2 == 0) {
                result.add(current.substring(0, current.length() / 2));
                result.add(
                        String.valueOf(
                                Long.parseLong(current.substring(current.length() / 2)
                                )
                        )
                );
            } else {
                result.add(String.valueOf(Long.parseLong(current) * 2024L));
            }
        }
        return result;
    }


    @Override
    public Stream<Arguments> getPart1Examples() {
        return Stream.of(Arguments.of("125 17", 55312L));
    }

    @Override
    public Stream<Arguments> getPart2Examples() {
        return Stream.empty();
    }
}
