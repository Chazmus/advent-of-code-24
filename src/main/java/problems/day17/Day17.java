/*
 * Copyright 2024 ForgeRock AS. All Rights Reserved
 *
 * Use of this code requires a commercial software license with ForgeRock AS.
 * or with one of its affiliates. All use shall be exclusively subject
 * to such license between the licensee and ForgeRock AS.
 */
package problems.day17;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import problems.ProblemBase;

public class Day17 extends ProblemBase {
    @Override
    public Long solvePart1(List<String> inputArray) {
        var compooter = getCompooter(inputArray);
        compooter.run();
        System.out.println(String.join(",", compooter.output.stream().map(String::valueOf).toList()));
        return 0L;
    }

    private Compooter getCompooter(List<String> inputArray) {
        var computer = new Compooter();
        for (var line : inputArray) {
            if (line.startsWith("Register A: ")) {
                computer.registerA = Integer.parseInt(line.split(": ")[1]);
            }
            if (line.startsWith("Register B: ")) {
                computer.registerB = Integer.parseInt(line.split(": ")[1]);
            }
            if (line.startsWith("Register C: ")) {
                computer.registerC = Integer.parseInt(line.split(": ")[1]);
            }
            if (line.startsWith("Program: ")) {
                var program = line.split(": ")[1].split(",");
                computer.program = new int[program.length];
                for (var i = 0; i < program.length; i++) {
                    computer.program[i] = Integer.parseInt(program[i]);
                }
            }
        }
        return computer;
    }

    @Override
    public Long solvePart2(List<String> inputArray) {
        return 0L;
    }


    class Compooter {

        int registerA;
        int registerB;
        int registerC;
        int[] program;
        int instructionPointer;
        List<Integer> output = new ArrayList<>();


        int getCombo(int input) {
            return switch (input) {
                case 0, 1, 2, 3 -> input;
                case 4 -> registerA;
                case 5 -> registerB;
                case 6 -> registerC;
                default -> throw new RuntimeException("Invalid input");
            };
        }

        public void run() {
            while (instructionPointer < program.length) {
                var opcode = Instruction.values()[program[instructionPointer]];
                var operand = program[instructionPointer + 1];
                switch (opcode) {
                    case adv:
                        var numerator = Double.valueOf(registerA);
                        var denominator = Math.pow(2, getCombo(operand));
                        registerA = (int) (numerator / denominator);
                        instructionPointer += 2;
                        break;
                    case bxl:
                        registerB = registerB ^ operand;
                        instructionPointer += 2;
                        break;
                    case bst:
                        registerB = getCombo(operand) % 8;
                        instructionPointer += 2;
                        break;
                    case jnz:
                        if (registerA != 0) {
                            instructionPointer = operand;
                        } else {
                            instructionPointer += 2;
                        }
                        break;
                    case bxc:
                        registerB = registerB ^ registerC;
                        instructionPointer += 2;
                        break;
                    case out:
                        output.add(getCombo(operand) % 8);
                        instructionPointer += 2;
                        break;
                    case bdv:
                        var numerator2 = Double.valueOf(registerA);
                        var denominator2 = Math.pow(2, getCombo(operand));
                        registerB = (int) (numerator2 / denominator2);
                        instructionPointer += 2;
                        break;
                    case cdv:
                        var numerator3 = Double.valueOf(registerA);
                        var denominator3 = Math.pow(2, getCombo(operand));
                        registerC = (int) (numerator3 / denominator3);
                        instructionPointer += 2;
                        break;
                }
            }
        }

        enum Instruction {
            adv, bxl, bst, jnz, bxc, out, bdv, cdv
        }
    }

    @Override
    public Stream<Arguments> getPart1Examples() {
        return Stream.of(
                Arguments.of("""
                        Register A: 729
                        Register B: 0
                        Register C: 0
                        
                        Program: 0,1,5,4,3,0""", 0L)
        );
    }

    @Override
    public Stream<Arguments> getPart2Examples() {
        return Stream.empty();
    }
}
