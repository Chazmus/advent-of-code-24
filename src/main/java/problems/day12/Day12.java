/*
 * Copyright 2024 ForgeRock AS. All Rights Reserved
 *
 * Use of this code requires a commercial software license with ForgeRock AS.
 * or with one of its affiliates. All use shall be exclusively subject
 * to such license between the licensee and ForgeRock AS.
 */
package problems.day12;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import problems.ProblemBase;

public class Day12 extends ProblemBase {
    @Override
    public Long solvePart1(List<String> inputArray) {
        return 0L;
    }

    @Override
    public Long solvePart2(List<String> inputArray) {
        return 0L;
    }

    @Override
    public Stream<Arguments> getPart1Examples() {
        return Stream.of(
                Arguments.of("""
                        AAAA
                        BBCD
                        BBCC
                        EEEC
                        """, 140L),
                Arguments.of("""
                        OOOOO
                        OXOXO
                        OOOOO
                        OXOXO
                        OOOOO
                        """, 772L),
                Arguments.of("""
                        RRRRIICCFF
                        RRRRIICCCF
                        VVRRRCCFFF
                        VVRCCCJFFF
                        VVVVCJJCFE
                        VVIVCCJJEE
                        VVIIICJJEE
                        MIIIIIJJEE
                        MIIISIJEEE
                        MMMISSJEEE
                        """, 1930L)
        );
    }

    @Override
    public Stream<Arguments> getPart2Examples() {
        return Stream.empty();
    }
}
