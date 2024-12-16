package problems.day16;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import problems.ProblemBase;
import utils.Direction;
import utils.Grid;
import utils.Vector2;

public class Day16 extends ProblemBase {
    Grid grid;
    PositionToScoreRecord scoreRecord;

    @Override
    public Long solvePart1(List<String> inputArray) {
        // Answers attempted: 89460
        grid = new Grid(inputArray);
        var start = grid.findAny('S').get();
        var end = grid.findAny('E').get();
        scoreRecord = new PositionToScoreRecord();
        var startingPosition = new Position(start, Direction.RIGHT);
        var startingPositionAndScore = new PositionAndScore(startingPosition, 0L, scoreRecord);

        insert(startingPositionAndScore, scoreRecord);

        return scoreRecord.entrySet().stream()
                .filter(entry -> entry.getKey().location().equals(end))
                .mapToLong(Map.Entry::getValue).min().getAsLong();
    }

    private void insert(PositionAndScore positionAndScore, PositionToScoreRecord positionToScoreRecord) {
        Queue<PositionAndScore> queue = new PriorityQueue<>(Comparator.comparingLong(PositionAndScore::score));
        queue.add(positionAndScore);

        while (!queue.isEmpty()) {
            PositionAndScore current = queue.poll();
            if (positionToScoreRecord.containsKey(current.position()) && positionToScoreRecord.get(current.position()) <= current.score()) {
                continue;
            }
            positionToScoreRecord.insert(current);

            for (PositionAndScore newPosAndScore : current.getNext(grid)) {
                if (!positionToScoreRecord.containsKey(newPosAndScore.position()) || positionToScoreRecord.get(newPosAndScore.position()) > newPosAndScore.score()) {
                    queue.add(newPosAndScore);
                }
            }
        }
    }

    @Override
    public Long solvePart2(List<String> inputArray) {
        return 0L;
    }

    class PositionToScoreRecord extends HashMap<Position, Long> {

        public void insert(PositionAndScore positionAndScore) {
            var position = positionAndScore.position();
            var score = positionAndScore.score();
            if (this.containsKey(position)) {
                if (this.get(position) > score) {
                    this.put(position, score);
                }
            } else {
                this.put(position, score);
            }
        }
    }

    record PositionAndScore(Position position, Long score, PositionToScoreRecord record) {
        Set<PositionAndScore> getNext(Grid grid) {
            var result = new HashSet<PositionAndScore>();
            for (Direction nextDirection : Direction.getCardinalDirections().toList()) {
                if (nextDirection == position.direction().getOpposite()) {
                    continue;
                }
                var nextPosition = new Position(position.location().add(nextDirection.toVector()), nextDirection);
                if (record.containsKey(nextPosition)) {
                    var existingScore = record.get(nextPosition);
                    if (existingScore <= score()) {
                        continue;
                    }
                }
                if (grid.get(position.location().add(nextDirection.toVector())).equals('.') ||
                        grid.get(position.location().add(nextDirection.toVector())).equals('E')) {
                    if (nextDirection == position.direction()) {
                        result.add(
                                new PositionAndScore(new Position(position.location().add(nextDirection.toVector()),
                                        nextDirection),
                                        score() + 1L, record)
                        );
                    } else {
                        result.add(
                                new PositionAndScore(new Position(position.location().add(nextDirection.toVector()),
                                        nextDirection),
                                        score() + 1001L, record)
                        );
                    }
                }
            }
            return result;
        }

    }

    record Position(Vector2 location, Direction direction) {
    }

    @Override
    public Stream<Arguments> getPart1Examples() {
        return Stream.of(
//                Arguments.of("""
//                        ######
//                        #S..E#
//                        ######""", 3L),
//                Arguments.of("""
//                        ######
//                        #.####
//                        #S..E#
//                        ######""", 3L),
                Arguments.of("""
                        ######
                        #....#
                        #.##.#
                        #....#
                        #.#.##
                        #S..E#
                        ######""", 3L),
                Arguments.of("""
                        ###############
                        #.......#....E#
                        #.#.###.#.###.#
                        #.....#.#...#.#
                        #.###.#####.#.#
                        #.#.#.......#.#
                        #.#.#####.###.#
                        #...........#.#
                        ###.#.#####.#.#
                        #...#.....#.#.#
                        #.#.#.###.#.#.#
                        #.....#...#.#.#
                        #.###.#.#.#.#.#
                        #S..#.....#...#
                        ###############""", 7036L),

                Arguments.of("""
                        #################
                        #...#...#...#..E#
                        #.#.#.#.#.#.#.#.#
                        #.#.#.#...#...#.#
                        #.#.#.#.###.#.#.#
                        #...#.#.#.....#.#
                        #.#.#.#.#.#####.#
                        #.#...#.#.#.....#
                        #.#.#####.#.###.#
                        #.#.#.......#...#
                        #.#.###.#####.###
                        #.#.#...#.....#.#
                        #.#.#.#####.###.#
                        #.#.#.........#.#
                        #.#.#.#########.#
                        #S#.............#
                        #################""", 11048L)
        );
    }

    @Override
    public Stream<Arguments> getPart2Examples() {
        return Stream.empty();
    }
}
