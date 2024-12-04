/*
 * Copyright 2024 ForgeRock AS. All Rights Reserved
 *
 * Use of this code requires a commercial software license with ForgeRock AS.
 * or with one of its affiliates. All use shall be exclusively subject
 * to such license between the licensee and ForgeRock AS.
 */
package problems.day4;

import static java.util.Map.entry;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import problems.ProblemBase;
import utils.Grid;
import utils.LinearMask;
import utils.Vector2;
import utils.WindowMask;

public class Day4 extends ProblemBase {
    @Override
    public Long solvePart1(List<String> inputArray) {
        var grid = new Grid(inputArray);
        var horizontalMask = new LinearMask(List.of(Vector2.of(0, 0), Vector2.of(0, 1), Vector2.of(0, 2),
                Vector2.of(0, 3)));
        var verticalMask = horizontalMask.rotateRight();
        var diagMask = new LinearMask(List.of(Vector2.of(0, 0), Vector2.of(1, 1), Vector2.of(2, 2), Vector2.of(3, 3)));
        var otherDiag = diagMask.rotateRight();
        var masks = List.of(
                horizontalMask,
                diagMask,
                otherDiag,
                verticalMask
        );
        var totalXmas = 0L;
        var search = "XMAS";
        for (var mask : masks) {
            var strings = mask.getAllStrings(grid, true);
            for (var word : strings) {
                if (word.equals(search)) {
                    totalXmas++;
                }
            }
        }
        return totalXmas;
    }

    @Override
    public Long solvePart2(List<String> inputArray) {
        var grid = new Grid(inputArray);
        var mask = new WindowMask(3, 3);
        var search = "MAS";
        var subGrids = mask.getAllSubGrids(grid);
        var diagMask = new LinearMask(List.of(Vector2.of(0, 0), Vector2.of(1, 1), Vector2.of(2, 2)));
        var otherDiag = new LinearMask(List.of(Vector2.of(0, 2), Vector2.of(1, 1), Vector2.of(2, 0)));;
        var totalMas = 0L;
        for (var subGrid : subGrids) {
            var words1 = diagMask.getAllStrings(subGrid, true);
            var word1 = words1.get(0);
            var reversedWord1 = words1.get(1);
            var words2 = otherDiag.getAllStrings(subGrid, true);
            var word2 = words2.get(0);
            var reversedWord2 = words2.get(1);
            if ((word1.equals(search) || reversedWord1.equals(search)) &&
                    (word2.equals(search) || reversedWord2.equals(search))) {
                totalMas++;
            }
        }

        return totalMas;
    }

    @Override
    public Stream<Map.Entry<String, Long>> getPart1Examples() {
        return Stream.of(entry("""
                MMMSXXMASM
                MSAMXMSMSA
                AMXSXMAAMM
                MSAMASMSMX
                XMASAMXAMM
                XXAMMXXAMA
                SMSMSASXSS
                SAXAMASAAA
                MAMMMXMMMM
                MXMXAXMASX
                """, 18L));
    }

    @Override
    public Stream<Map.Entry<String, Long>> getPart2Examples() {
        return Stream.of(entry("""
                MMMSXXMASM
                MSAMXMSMSA
                AMXSXMAAMM
                MSAMASMSMX
                XMASAMXAMM
                XXAMMXXAMA
                SMSMSASXSS
                SAXAMASAAA
                MAMMMXMMMM
                MXMXAXMASX
                """, 9L));
    }
}
