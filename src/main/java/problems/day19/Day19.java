/*
 * Copyright 2024 ForgeRock AS. All Rights Reserved
 *
 * Use of this code requires a commercial software license with ForgeRock AS.
 * or with one of its affiliates. All use shall be exclusively subject
 * to such license between the licensee and ForgeRock AS.
 */
package problems.day19;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import problems.ProblemBase;

public class Day19 extends ProblemBase {
    @Override
    public Long solvePart1(List<String> inputArray) {
        var availableTowels = Arrays.stream(inputArray.get(0).split(", ")).toList();
        var designs = inputArray.subList(1, inputArray.size()).stream().filter(s -> !s.isBlank()).toList();

        return designs.stream()
                .filter(d -> designCanBeMade(d, availableTowels))
                .count();
    }

    private boolean designCanBeMade(String design, List<String> availableTowels) {
        var matchingTowels = availableTowels.stream()
                .filter(design::startsWith).toList();

        if (matchingTowels.isEmpty()) {
            return false;
        }

        for (var towel : matchingTowels) {
            var remainingDesign = design.substring(towel.length());
            if (remainingDesign.isBlank() || designCanBeMade(remainingDesign, availableTowels)) {
                System.out.println("Found design");
                return true;
            }
        }

        return false;
    }

    @Override
    public Long solvePart2(List<String> inputArray) {
        return 0L;
    }

    @Override
    public Stream<Arguments> getPart1Examples() {
        return Stream.of(Arguments.of("""
                r, wr, b, g, bwu, rb, gb, br
                
                brwrr
                bggr
                gbbr
                rrbgbr
                ubwu
                bwurrg
                brgr
                bbrgwb""", 6L));
    }

    @Override
    public Stream<Arguments> getPart2Examples() {
        return Stream.empty();
    }
}
