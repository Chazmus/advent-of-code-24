/*
 * Copyright 2024 ForgeRock AS. All Rights Reserved
 *
 * Use of this code requires a commercial software license with ForgeRock AS.
 * or with one of its affiliates. All use shall be exclusively subject
 * to such license between the licensee and ForgeRock AS.
 */

import java.util.Arrays;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import utils.FileUtils;

public abstract class TestBase {

    private Part part;

    @BeforeEach
    void setup() {
        part = getPart();
    }

    protected abstract Part getPart();

    protected abstract Map<String, Integer> getExamples();

    protected abstract String getInputFileName();

    @Test
    protected void runExamples() {
        var examples = getExamples();
        for (var entry : examples.entrySet()) {
            runExample(entry.getKey(), entry.getValue());
        }
    }

    protected void runExample(String input, int expected) {
        var inputArray = Arrays.stream(input.split(System.lineSeparator())).toList();
        var result = part.solve(inputArray);
        Assertions.assertEquals(expected, result);
        System.out.println(result);
    }

    @Test
    protected void runFinalTest() {
        var inputFile = getInputFileName();
        var input = FileUtils.readFileFromResources(inputFile);
        var result = part.solve(input);
        System.out.println(result);
    }
}
