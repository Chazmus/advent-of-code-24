package problems.day11;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.provider.Arguments;

import problems.ProblemBase;

public class Day11 extends ProblemBase {

    Map<Long, Long> stoneToCount;
    Map<Long, Long> nextStoneToCount;
    Map<Long, Long[]> cache;

    @BeforeEach
    public void setup() {
        stoneToCount = new HashMap<>();
        nextStoneToCount = new HashMap<>();
       cache = new HashMap<>();
    }

    @Override
    public Long solvePart1(List<String> inputArray) {
        return solve(inputArray, 25);
    }

    @Override
    public Long solvePart2(List<String> inputArray) {
        return solve(inputArray, 75);
    }

    public Long solve(List<String> inputArray, int iterations) {
        var input = Arrays.stream(inputArray.get(0).split(" "))
                .map(Long::parseLong).toList();

        for (var stone : input) {
            stoneToCount.put(stone, stoneToCount.getOrDefault(stone, 0L) + 1);
        }

        for (int j = 0; j < iterations; j++) {
            nextStoneToCount.clear();

            for (long stone : stoneToCount.keySet()) {
                if (stone == 0) {
                    nextStoneToCount.put(1L, nextStoneToCount.getOrDefault(1L, 0L) + stoneToCount.get(stone));
                } else if (cache.containsKey(stone)) {
                    for (Long l : cache.get(stone)) {
                        nextStoneToCount.put(l, nextStoneToCount.getOrDefault(l, 0L) + stoneToCount.get(stone));
                    }
                } else {
                    var digits = String.valueOf(stone);
                    if (digits.length() % 2 == 0) {
                        long first = Long.parseLong(digits.substring(0, digits.length() / 2));
                        long second = Long.parseLong(digits.substring(digits.length() / 2));
                        nextStoneToCount.put(first, nextStoneToCount.getOrDefault(first, 0L) + stoneToCount.get(stone));
                        nextStoneToCount.put(second,
                                nextStoneToCount.getOrDefault(second, 0L) + stoneToCount.get(stone));
                        cache.put(stone, new Long[]{first, second});
                    } else {
                        long num = 2024 * stone;
                        nextStoneToCount.put(num, nextStoneToCount.getOrDefault(num, 0L) + stoneToCount.get(stone));
                        cache.put(stone, new Long[]{num});
                    }
                }
            }
            stoneToCount = Map.copyOf(nextStoneToCount);
        }

        var result = 0L;
        for (var stone : stoneToCount.keySet()) {
            result += stoneToCount.get(stone);
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
