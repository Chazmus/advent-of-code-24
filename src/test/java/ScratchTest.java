import java.util.Map;

public class ScratchTest {

    public static String INPUT_FILE_NAME = "Scratch";

    public static class Part1Test extends TestBase {
        String example1Input = """
                1abc2
                pqr3stu8vwx
                a1b2c3d4e5f
                treb7uchet
                """;

        @Override
        protected Part getPart() {
            return new Scratch.Part1();
        }

        @Override
        protected String getInputFileName() {
            return INPUT_FILE_NAME;
        }

        @Override
        protected Map<String, Integer> getExamples() {
            return Map.of(example1Input, 142);
        }
    }

    public static class Part2Test extends TestBase {
        String example1Input = """
                two1nine
                eightwothree
                abcone2threexyz
                xtwone3four
                4nineeightseven2
                zoneight234
                7pqrstsixteen""";

        @Override
        protected Part getPart() {
            return new Scratch.Part2();
        }

        @Override
        protected Map<String, Integer> getExamples() {
            return Map.of(example1Input, 281);
        }

        @Override
        protected String getInputFileName() {
            return INPUT_FILE_NAME;
        }
    }
}
