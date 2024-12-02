import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import client.AdventOfCodeClient;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class ProblemBase {

    public abstract Long solvePart1(List<String> inputArray);

    public abstract Long solvePart2(List<String> inputArray);

    public abstract Stream<Map.Entry<String, Long>> getPart1Examples();

    public abstract Stream<Map.Entry<String, Long>> getPart2Examples();


    @ParameterizedTest()
    @MethodSource("getPart1Examples")
    @Order(0)
    public void runPart1Examples(Map.Entry<String, Long> entry) {
        var input = Arrays.stream(entry.getKey().split(System.lineSeparator())).toList();
        var solution = solvePart1(input);
        var expected = entry.getValue();
        Assertions.assertEquals(expected, solution);
        System.out.println(solution);
    }

    @Test
    @Order(1)
    public void runPart1() {
        var input = getInput();
        var solution = solvePart1(input);
        System.out.println(solution);
    }


    @ParameterizedTest
    @MethodSource("getPart2Examples")
    @Order(2)
    public void runPart2Examples(Map.Entry<String, Long> entry) {
        var input = Arrays.stream(entry.getKey().split(System.lineSeparator())).toList();
        var expected = entry.getValue();
        var solution = solvePart2(input);
        Assertions.assertEquals(expected, solution);
        System.out.println(solution);
    }

    @Test
    @Order(3)
    public void runPart2() {
        var input = getInput();
        var solution = solvePart2(input);
        System.out.println(solution);
    }

    private List<String> getInput() {
        var day = getClass().getSimpleName();
        ClassLoader classLoader = ProblemBase.class.getClassLoader();
        try {
            var resource = classLoader.getResource(day);
            if (resource == null) {
                var client = new AdventOfCodeClient();
                client.getInput(day);
                classLoader = ProblemBase.class.getClassLoader();
                resource = classLoader.getResource(day);
            }
            var filePath = Paths.get(resource.toURI());
            var lines = Files.readAllLines(filePath);
            if (lines.getLast().isBlank()) {
                lines.removeLast();
            }
            return lines;
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException("Can't read the input file");
        }
    }
}
