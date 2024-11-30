import java.util.Map;

import org.junit.jupiter.api.Test;

public class ScratchTest extends TestBase {

    String example1Input = """
            1abc2
            pqr3stu8vwx
            a1b2c3d4e5f
            treb7uchet
            """;

    @Override
    protected Problem getProblem() {
        return new Scratch();
    }

    @Override
    protected Map<String, Integer> getExamples() {
        return Map.of(example1Input, 142);
    }

    @Override
    protected String getInputFileName() {
        return "Scratch";
    }
}
