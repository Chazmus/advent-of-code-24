package problems.day14;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import problems.ProblemBase;
import utils.Direction;
import utils.Grid;
import utils.LinearMask;
import utils.Vector2;

public class Day14 extends ProblemBase {

    Pattern regex = Pattern.compile("p=(.+),(.+) v=(.+),(.+)");

    @Override
    public Long solvePart1(List<String> inputArray) {
        var mapSize = inputArray.size() > 15 ? new Vector2(101, 103) : new Vector2(11, 7);
        var bots =
                inputArray.stream()
                        .filter(line -> line.startsWith("p="))
                        .map(line -> {
                            Matcher matcher = regex.matcher(line);
                            matcher.find();
                            var px = Integer.parseInt(matcher.group(1));
                            var py = Integer.parseInt(matcher.group(2));
                            var vx = Integer.parseInt(matcher.group(3));
                            var vy = Integer.parseInt(matcher.group(4));
                            return new Robot(new Vector2(px, py), new Vector2(vx, vy), mapSize);
                        }).toList();
        IntStream.range(0, 100)
                .forEach(i -> {
                    bots.forEach(Robot::move);
                });

        List<Robot> topLeft = new ArrayList<>();
        List<Robot> topRight = new ArrayList<>();
        List<Robot> bottomLeft = new ArrayList<>();
        List<Robot> bottomRight = new ArrayList<>();

        var horizontalMiddle = (mapSize.x()) / 2;
        var verticalMiddle = (mapSize.y()) / 2;
        bots.forEach(b -> {
            var x = b.getPosition().x();
            var y = b.getPosition().y();
            if (x < horizontalMiddle && y < verticalMiddle) {
                topLeft.add(b);
            } else if (x > horizontalMiddle && y < verticalMiddle) {
                topRight.add(b);
            } else if (x < horizontalMiddle && y > verticalMiddle) {
                bottomLeft.add(b);
            } else if (x > horizontalMiddle && y > verticalMiddle) {
                bottomRight.add(b);
            }
        });

        return (long) topLeft.size() * topRight.size() * bottomLeft.size() * bottomRight.size();
    }

    @Override
    public Long solvePart2(List<String> inputArray) {
        var mapSize = inputArray.size() > 15 ? new Vector2(101, 103) : new Vector2(11, 7);
        var bots =
                inputArray.stream()
                        .filter(line -> line.startsWith("p="))
                        .map(line -> {
                            Matcher matcher = regex.matcher(line);
                            matcher.find();
                            var px = Integer.parseInt(matcher.group(1));
                            var py = Integer.parseInt(matcher.group(2));
                            var vx = Integer.parseInt(matcher.group(3));
                            var vy = Integer.parseInt(matcher.group(4));
                            return new Robot(new Vector2(px, py), new Vector2(vx, vy), mapSize);
                        }).toList();

        var linearMask = new LinearMask(Direction.DOWN, 7);
        IntStream.range(0, 10000)
                .forEach(i -> {
                    var grid = new Grid(mapSize.x(), mapSize.y(), '.');
                    bots.forEach(b -> grid.set(b.getPosition(), '#'));
                    bots.forEach(Robot::move);
                    if (linearMask.getAllStringsStream(grid, false)
                            .anyMatch(s -> s.equals("#######"))) {
                        System.out.print("Step " + (i + 1));
                        grid.print();
                    }
                });

        return 0L;
    }

    @Override
    public Stream<Arguments> getPart1Examples() {
        return Stream.of(
//                Arguments.of("""
//                        p=2,4 v=2,-3
//                        """, 1L),
                Arguments.of("""
                        p=0,4 v=3,-3
                        p=6,3 v=-1,-3
                        p=10,3 v=-1,2
                        p=2,0 v=2,-1
                        p=0,0 v=1,3
                        p=3,0 v=-2,-2
                        p=7,6 v=-1,-3
                        p=3,0 v=-1,-2
                        p=9,3 v=2,3
                        p=7,3 v=-1,2
                        p=2,4 v=2,-3
                        p=9,5 v=-3,-3""", 12L)
        );
    }

    @Override
    public Stream<Arguments> getPart2Examples() {
        return Stream.empty();
    }
}
