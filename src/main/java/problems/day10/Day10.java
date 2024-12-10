package problems.day10;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import problems.ProblemBase;
import utils.Grid;
import utils.Vector2;

public class Day10 extends ProblemBase {

    @Override
    public Long solvePart1(List<String> inputArray) {
        var grid = new Grid(inputArray);
        var cells = grid.findAll('0');
        var scoreMap = new HashMap<Vector2, Set<Vector2>>();
        cells.forEach(cell ->
                new HotSteppa1(cell, grid, scoreMap, cell.position()).step());

        AtomicLong total = new AtomicLong(0);
        scoreMap.entrySet().stream().forEach(vector2SetEntry -> {
            total.addAndGet(vector2SetEntry.getValue().size());
        });
        return total.get();
    }

    @Override
    public Long solvePart2(List<String> inputArray) {
        var grid = new Grid(inputArray);
        var cells = grid.findAll('0');
        var scoreMap = new HashMap<Vector2, Integer>();
        cells.forEach(cell ->
                new HotSteppa2(cell, grid, scoreMap, cell.position()).step());

        return scoreMap.values().stream()
                .map(Long::valueOf)
                .reduce(0L, Long::sum);
    }

    record HotSteppa1(Grid.Cell currentCell, Grid grid, Map<Vector2, Set<Vector2>> score, Vector2 startingPosition) {
        public void step() {
            if (currentCell.value() == '9') {
                if (!score.containsKey(startingPosition)) {
                    score.put(startingPosition, new HashSet<>());
                }
                score.get(startingPosition).add(currentCell.position());
                return;
            }
            grid.getCardinalNeighbors(currentCell())
                    .filter(neighbor -> neighbor.value() != '.')
                    .filter(neighbor -> Character.getNumericValue(neighbor.value()) == Character.getNumericValue(currentCell.value()) + 1)
                    .forEach(neighbor -> new HotSteppa1(neighbor, grid, score, startingPosition).step());
        }
    }

    record HotSteppa2(Grid.Cell currentCell, Grid grid, Map<Vector2, Integer> score, Vector2 startingPosition) {
        public void step() {
            if (currentCell.value() == '9') {
                if (!score.containsKey(startingPosition)) {
                    score.put(startingPosition, 1);
                } else {
                    score.put(startingPosition, score.get(startingPosition) + 1);
                }
                return;
            }
            grid.getCardinalNeighbors(currentCell())
                    .filter(neighbor -> neighbor.value() != '.')
                    .filter(neighbor -> Character.getNumericValue(neighbor.value()) == Character.getNumericValue(currentCell.value()) + 1)
                    .forEach(neighbor -> new HotSteppa2(neighbor, grid, score, startingPosition).step());
        }
    }

    @Override
    public Stream<Arguments> getPart1Examples() {
        return Stream.of(
                Arguments.of("""
                        89010123
                        78121874
                        87430965
                        96549874
                        45678903
                        32019012
                        01329801
                        10456732""", 36L),
                Arguments.of("""
                        ...0...
                        ...1...
                        ...2...
                        6543456
                        7.....7
                        8.....8
                        9.....9
                        """, 2L),
                Arguments.of("""
                        ..90..9
                        ...1.98
                        ...2..7
                        6543456
                        765.987
                        876....
                        987....
                        """, 4L)

        );
    }

    @Override
    public Stream<Arguments> getPart2Examples() {
        return Stream.of(
                Arguments.of("""
                        89010123
                        78121874
                        87430965
                        96549874
                        45678903
                        32019012
                        01329801
                        10456732""", 81L)

        );
    }
}
