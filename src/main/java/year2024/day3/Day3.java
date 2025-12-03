package year2024.day3;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import utils.ProblemBase;

public class Day3 extends ProblemBase {
    Pattern mulPattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)");

    public Day3() {
        super("2024");
    }

    @Override
    public Long solvePart1(List<String> inputArray) {
        return solve(String.join("", inputArray));
    }

    public Long solve(String input) {
        var total = 0L;
        var matcher = mulPattern.matcher(input);
        while (matcher.find()) {
            total += Long.parseLong(matcher.group(1)) * Long.parseLong(matcher.group(2));
        }
        return total;
    }

    @Override
    public Long solvePart2(List<String> inputArray) {
        var input = String.join("", inputArray)
                .replaceAll("(?<=don't\\(\\))(.*?)(?=do\\(\\))", "");
        return solve(input);
    }

    @Override
    public Stream<Arguments> getPart1Examples() {
        return Stream.of(Arguments.of("""
                xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))""", 161L));
    }

    @Override
    public Stream<Arguments> getPart2Examples() {
        return Stream.of(Arguments.of("""
                xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))""", 48L));
    }
}
