package problems.day3;

import static java.util.Map.entry;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import problems.ProblemBase;

public class Day3 extends ProblemBase {
    Pattern mulPattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)");

    @Override
    public Long solvePart1(List<String> inputArray) {
        var total = 0L;
        for (var line : inputArray) {
            var matcher = mulPattern.matcher(line);
            while (matcher.find()) {
                var first = Long.parseLong(matcher.group(1));
                var second = Long.parseLong(matcher.group(2));
                total += first * second;
            }
        }

        return total;
    }

    @Override
    public Long solvePart2(List<String> inputArray) {
        var totalLine = inputArray.get(0);
        String regex = "(?<=don't\\(\\))(.*?)(?=do\\(\\))";
        String result = totalLine.replaceAll(regex, "");
        return solvePart1(List.of(result));
    }

    @Override
    public Stream<Map.Entry<String, Long>> getPart1Examples() {
        var input = """
                xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))""";
        var expected = 161L;
        return Stream.of(entry(input, expected));
    }

    @Override
    public Stream<Map.Entry<String, Long>> getPart2Examples() {
        return Stream.of(entry("""
                xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))""", 48L));
    }
}
