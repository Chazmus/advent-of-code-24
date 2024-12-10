package problems.day10;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import problems.ProblemBase;
import utils.Grid;
import utils.Vector2;

public class Day10 extends ProblemBase {

    record HotSteppa(Grid.Cell currentCell, Grid grid, Map<Vector2, Integer> score, Vector2 startingPosition) {
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
                    .filter(neighbor -> Character.getNumericValue(neighbor.value()) == Character.getNumericValue(currentCell.value()) + 1)
                    .forEach(neighbor -> new HotSteppa(neighbor, grid, score, startingPosition).step());
        }
    }

    @Override
    public Long solvePart1(List<String> inputArray) {
        var grid = new Grid(inputArray);
        var cells = grid.findAll('0');
        var scoreMap = new HashMap<Vector2, Integer>();
        cells.forEach(cell ->
                new HotSteppa(cell, grid, scoreMap, cell.position()).step());

        return 0L;
    }

    @Override
    public Long solvePart2(List<String> inputArray) {
        return 0L;
    }

    @Override
    public Stream<Arguments> getPart1Examples() {
        return Stream.of(Arguments.of("""
                89010123
                78121874
                87430965
                96549874
                45678903
                32019012
                01329801
                10456732""", 36L));
    }

    @Override
    public Stream<Arguments> getPart2Examples() {
        return Stream.empty();
    }
}
