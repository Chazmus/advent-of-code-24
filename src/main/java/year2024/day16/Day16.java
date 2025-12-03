package year2024.day16;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import utils.ProblemBase;
import utils.Direction;
import utils.Grid;
import utils.Vector2;

public class Day16 extends ProblemBase {
    Grid grid;
    PositionToScoreRecord scoreRecord;

    public Day16() {
        super("2024");
    }

    @Override
    public Long solvePart1(List<String> inputArray) {
        grid = new Grid(inputArray);
        var start = grid.findAny('S').get();
        var end = grid.findAny('E').get();
        scoreRecord = new PositionToScoreRecord();
        var startingPosition = new Position(start, Direction.RIGHT);
        var startingPositionAndScore = new PositionAndScore(startingPosition, 0L, scoreRecord);
        insert(startingPositionAndScore);
        return scoreRecord.entrySet().stream()
                .filter(entry -> entry.getKey().location().equals(end))
                .mapToLong(Map.Entry::getValue).min().getAsLong();
    }

    @Override
    public Long solvePart2(List<String> inputArray) {
        grid = new Grid(inputArray);
        var start = grid.findAny('S').get();
        var end = grid.findAny('E').get();
        scoreRecord = new PositionToScoreRecord();
        var startingPosition = new Position(start, Direction.RIGHT);
        var startingPositionAndScore = new PositionAndScore(startingPosition, 0L, scoreRecord);
        var predecessors = new HashMap<Position, Set<Position>>();
        insertWithPredecessorTracking(startingPositionAndScore, predecessors);
        Set<List<Position>> allBestPaths = new HashSet<>();
        Set<Position> endPositions = Set.of(
                new Position(end, Direction.RIGHT),
                new Position(end, Direction.DOWN),
                new Position(end, Direction.UP),
                new Position(end, Direction.LEFT)
        );

        for (Position endPosition : endPositions) {
            List<Position> currentPath = new ArrayList<>();
            currentPath.add(endPosition);
            if (scoreRecord.containsKey(endPosition)) {
                reconstructPaths(endPosition, scoreRecord.get(endPosition), predecessors, currentPath, allBestPaths);
            }
        }
        var entries = allBestPaths.stream().flatMap(List::stream)
                .map(Position::location)
                .collect(Collectors.toSet());
        return (long) entries.size();
    }

    private void reconstructPaths(Position currentPosition, long currentScore,
            Map<Position, Set<Position>> predecessors, List<Position> currentPath, Set<List<Position>> allBestPaths) {
        if (currentPosition == null) {
            return;
        }

        if (predecessors.containsKey(currentPosition)) {
            for (Position predecessor : predecessors.get(currentPosition)) {
                if (scoreRecord.get(predecessor) == currentScore - 1) {
                    currentPath.add(0, predecessor);
                    reconstructPaths(predecessor, currentScore - 1, predecessors, currentPath, allBestPaths);
                    currentPath.remove(0);
                } else if (scoreRecord.get(predecessor) == currentScore - 1001) {
                    currentPath.add(0, predecessor);
                    reconstructPaths(predecessor, currentScore - 1001, predecessors, currentPath, allBestPaths);
                    currentPath.remove(0);
                }
            }
        } else {
            allBestPaths.add(new ArrayList<>(currentPath));
        }
    }

    private void insert(PositionAndScore positionAndScore) {
        Queue<PositionAndScore> queue = new PriorityQueue<>(Comparator.comparingLong(PositionAndScore::score));
        queue.add(positionAndScore);

        while (!queue.isEmpty()) {
            PositionAndScore current = queue.poll();
            if (scoreRecord.containsKey(current.position()) && scoreRecord.get(current.position()) <= current.score()) {
                continue;
            }
            scoreRecord.insert(current);

            for (PositionAndScore newPosAndScore : current.getNext(grid)) {
                if (!scoreRecord.containsKey(newPosAndScore.position()) || scoreRecord.get(newPosAndScore.position()) > newPosAndScore.score()) {
                    queue.add(newPosAndScore);
                }
            }
        }
    }


    private void insertWithPredecessorTracking(PositionAndScore positionAndScore,
            Map<Position, Set<Position>> predecessors) {
        Queue<PositionAndScore> queue = new PriorityQueue<>(Comparator.comparingLong(PositionAndScore::score));
        queue.add(positionAndScore);

        while (!queue.isEmpty()) {
            PositionAndScore current = queue.poll();
            if (scoreRecord.containsKey(current.position()) && scoreRecord.get(current.position()) <= current.score()) {
                continue;
            }
            scoreRecord.insert(current);

            for (PositionAndScore newPosAndScore : current.getNext(grid)) {
                if (!scoreRecord.containsKey(newPosAndScore.position()) || scoreRecord.get(newPosAndScore.position()) > newPosAndScore.score()) {
                    queue.add(newPosAndScore);
                    predecessors.computeIfAbsent(newPosAndScore.position(), k -> new HashSet<>()).add(current.position());
                }
            }
        }
    }

    static class PositionToScoreRecord extends HashMap<Position, Long> {

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
