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

    Problem problem;

    @BeforeEach
    void setup() {
        problem = getProblem();
    }

    protected abstract Problem getProblem();

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
        var result = problem.solve(inputArray);
        Assertions.assertEquals(expected, result);
        System.out.println(result);
    }

    @Test
    protected void runFinalTest() {
        var inputFile = getInputFileName();
        var input = FileUtils.readFileFromResources(inputFile);
        var result = problem.solve(input);
        System.out.println(result);
    }
}
