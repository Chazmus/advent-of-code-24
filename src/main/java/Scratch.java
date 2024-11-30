/*
 * Copyright 2024 ForgeRock AS. All Rights Reserved
 *
 * Use of this code requires a commercial software license with ForgeRock AS.
 * or with one of its affiliates. All use shall be exclusively subject
 * to such license between the licensee and ForgeRock AS.
 */

import java.util.ArrayList;
import java.util.List;

public class Scratch extends Problem {
    @Override
    int solve(List<String> inputArray) {
        var numbers = new ArrayList<Integer>();

        for (var line : inputArray) {
            Character firstDigit = null;
            Character lastDigit = null;
            for (var c : line.toCharArray()) {
                if (Character.isDigit(c)) {
                    lastDigit = c;
                    if (firstDigit == null) {
                        firstDigit = c;
                    }
                }
            }
            numbers.add(Integer.parseInt(firstDigit.toString() + lastDigit.toString()));
        }

        // Sum all the numbers
        var sum = 0;
        for (var number : numbers) {
            sum += number;
        }
        return sum;
    }
}
