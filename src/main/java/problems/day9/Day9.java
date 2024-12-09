package problems.day9;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import problems.ProblemBase;

public class Day9 extends ProblemBase {
    @Override
    public Long solvePart1(List<String> inputArray) {
        var uncompressed = getUncompressed(inputArray);
        var compacted = compact(uncompressed);

        return getTotal(compacted);
    }

    private Long getTotal(List<String> compacted) {
        var total = 0L;
        for (int i = 0; i < compacted.size(); i++) {
            var current = compacted.get(i);
            if (current.equals(".")) {
                continue;
            }
            total += i * Long.parseLong(current);
        }
        return total;
    }

    private List<String> compact(List<String> uncompressed) {
        var startPointer = 0;
        var endPointer = uncompressed.size() - 1;
        var currentStart = uncompressed.get(startPointer);
        var currentEnd = uncompressed.get(endPointer);
        var result = new LinkedList<>(uncompressed);

        while (startPointer < endPointer) {
            currentStart = uncompressed.get(startPointer);
            currentEnd = uncompressed.get(endPointer);
            if (currentStart.equals(".")) {
                while (currentEnd.equals(".")) {
                    currentEnd = uncompressed.get(--endPointer);
                }
                if (endPointer < startPointer) {
                    break;
                }
                result.set(startPointer, currentEnd);
                result.set(endPointer, currentStart);
                endPointer--;
            }
            startPointer++;
        }
        return result;
    }

    private List<String> getUncompressed(List<String> inputArray) {
        var compressed = Arrays.stream(inputArray.get(0).split("")).map(Short::valueOf).toList();
        var uncompressed = new ArrayList<String>();
        var fileId = 0;
        for (int index = 0; index < compressed.size(); index++) {
            var current = compressed.get(index);
            if (index % 2 == 0) {
                uncompressed.addAll(Collections.nCopies(current, String.valueOf(fileId)));
                fileId++;
            } else {
                uncompressed.addAll(Collections.nCopies(current, "."));
            }
        }
        return uncompressed;
    }

    @Override
    public Long solvePart2(List<String> inputArray) {
        return 0L;
    }

    @Override
    public Stream<Arguments> getPart1Examples() {
        var incorrectResult = "6370926321628";
        var anotherIncorrectResult = "6370402959503";
        return Stream.of(
//                Arguments.of("2333133121414131402", 1928L),
//                Arguments.of("12345", 60L),
//                Arguments.of("123456", 60L),
//                Arguments.of("19", 0L),
//                Arguments.of("123", 6L),
//                Arguments.of("2222", 5L),
                Arguments.of("22222", 19L)

        );

    }

    @Override
    public Stream<Arguments> getPart2Examples() {
        return Stream.empty();
    }
}
