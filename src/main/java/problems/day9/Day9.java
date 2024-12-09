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
        var result = new ArrayList<String>();

        while (startPointer <= endPointer) {
            currentStart = uncompressed.get(startPointer);
            if (currentStart.equals(".")) {
                currentEnd = uncompressed.get(endPointer);
                while (currentEnd.equals(".")) {
                    endPointer--;
                    currentEnd = uncompressed.get(endPointer);
                }
                result.add(currentEnd);
                startPointer++;
                endPointer--;
            } else {
                result.add(currentStart);
                startPointer++;
            }
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
        return Stream.of(Arguments.of("2333133121414131402", 1928L));
    }

    @Override
    public Stream<Arguments> getPart2Examples() {
        return Stream.empty();
    }
}
