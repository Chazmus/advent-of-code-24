import java.util.Map;

public class Day1Test {

    public static String INPUT_FILE_NAME = "Day1";

    public static class Part1Test extends TestBase {
        String example1Input = """
                3   4
                4   3
                2   5
                1   3
                3   9
                3   3
                """;

        @Override
        protected Part getPart() {
            return new Day1.Part1();
        }

        @Override
        protected String getInputFileName() {
            return INPUT_FILE_NAME;
        }

        @Override
        protected Map<String, Integer> getExamples() {
            return Map.of(example1Input, 11);
        }
    }

    public static class Part2Test extends TestBase {
        String example1Input = """
                3   4
                4   3
                2   5
                1   3
                3   9
                3   3
                """;

        @Override
        protected Part getPart() {
            return new Day1.Part2();
        }

        @Override
        protected Map<String, Integer> getExamples() {
            return Map.of(example1Input, 31);
        }

        @Override
        protected String getInputFileName() {
            return INPUT_FILE_NAME;
        }
    }
}
