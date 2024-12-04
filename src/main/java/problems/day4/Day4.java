package problems.day4;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import problems.ProblemBase;
import utils.Direction;
import utils.Grid;
import utils.LinearMask;
import utils.WindowMask;

public class Day4 extends ProblemBase {
    @Override
    public Long solvePart1(List<String> inputArray) {
        var grid = new Grid(inputArray);
        var masks = List.of(
                new LinearMask(Direction.RIGHT, 4),
                new LinearMask(Direction.DOWN, 4),
                new LinearMask(Direction.DOWN_RIGHT, 4),
                new LinearMask(Direction.DOWN_LEFT, 4)
        );
        var searchTerm = "XMAS";
        return masks.stream()
                .map(mask -> mask.getAllStrings(grid, true))
                .flatMap(Collection::stream)
                .filter(word -> word.equals(searchTerm))
                .count();
    }

    @Override
    public Long solvePart2(List<String> inputArray) {
        var grid = new Grid(inputArray);
        var searchTerm = "MAS";
        return new WindowMask(3, 3).getAllSubGrids(grid).stream()
                .map(subGrid -> {
                    var words1 = new LinearMask(Direction.DOWN_RIGHT, 3).getAllStrings(subGrid, true);
                    var words2 = new LinearMask(Direction.DOWN_LEFT, 3).getAllStrings(subGrid, true);
                    return List.of(words1.get(0), words1.get(1), words2.get(0), words2.get(1));
                })
                .filter(words -> (words.get(0).equals(searchTerm) || words.get(1).equals(searchTerm)) &&
                        (words.get(2).equals(searchTerm) || words.get(3).equals(searchTerm)))
                .count();
    }

    @Override
    public Stream<Arguments> getPart1Examples() {
        return Stream.of(Arguments.of("""
                MMMSXXMASM
                MSAMXMSMSA
                AMXSXMAAMM
                MSAMASMSMX
                XMASAMXAMM
                XXAMMXXAMA
                SMSMSASXSS
                SAXAMASAAA
                MAMMMXMMMM
                MXMXAXMASX
                """, 18L));
    }

    @Override
    public Stream<Arguments> getPart2Examples() {
        return Stream.of(Arguments.of("""
                MMMSXXMASM
                MSAMXMSMSA
                AMXSXMAAMM
                MSAMASMSMX
                XMASAMXAMM
                XXAMMXXAMA
                SMSMSASXSS
                SAXAMASAAA
                MAMMMXMMMM
                MXMXAXMASX
                """, 9L));
    }
}
