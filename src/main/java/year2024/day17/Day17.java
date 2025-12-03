/*
 * Copyright 2024 ForgeRock AS. All Rights Reserved
 *
 * Use of this code requires a commercial software license with ForgeRock AS.
 * or with one of its affiliates. All use shall be exclusively subject
 * to such license between the licensee and ForgeRock AS.
 */
package year2024.day17;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import utils.ProblemBase;

public class Day17 extends ProblemBase {
    public Day17() {
        super("2024");
    }

    @Override
    public Long solvePart1(List<String> inputArray) {
        var compooter = getCompooter(inputArray);
        compooter.run();
        Arrays.stream(compooter.output).asLongStream().forEach(x -> System.out.print("," + x));
        return 0L;
    }

    @Override
    public Long solvePart2(List<String> inputArray) {
        var compooter = getCompooter(inputArray);
        compooter.setOutputSize(compooter.program.length);
        var registerA = 0;
        var originalRegisterA = 0;
        while (!compooter.isSolved) {
            registerA++; // probs not gonna be zero
            compooter.reset(registerA);
            originalRegisterA = registerA;
            compooter.run();
            if (registerA % 1000000 == 0) {
                System.out.println(registerA);
            }
        }
        var output = Arrays.stream(compooter.output)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(","));
        System.out.println(output);
        return (long) originalRegisterA;
    }


    class Compooter {

        boolean isSolved;
        long registerA;
        long registerB;
        long registerC;
        int[] program;
        int[] output;
        int outputPointer;
        int instructionPointer;

        public void setProgram(int[] program) {
            this.program = program;
            output = new int[16];
        }

        long getCombo(int input) {
            return switch (input) {
                case 0, 1, 2, 3 -> (long) input;
                case 4 -> registerA;
                case 5 -> registerB;
                case 6 -> registerC;
                default -> throw new RuntimeException("Invalid input");
            };
        }


        public void run() {
            while (instructionPointer < program.length && !isSolved) {
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
                        output[outputPointer] = (int) (getCombo(operand) % 8);
                        outputPointer++;
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
                isSolved = Arrays.equals(output, program);
            }
        }

        public void setOutputSize(int length) {
            output = new int[length];
        }

        public void reset(long registerA) {
            instructionPointer = 0;
            outputPointer = 0;
            isSolved = false;
            registerB = 0;
            registerC = 0;
            this.registerA = registerA;
            Arrays.fill(output, 0);
        }

        enum Instruction {
            adv, bxl, bst, jnz, bxc, out, bdv, cdv
        }
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
                computer.setProgram(Arrays.stream(program).mapToInt(Integer::parseInt).toArray());
            }
        }
        return computer;
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
        return Stream.of(
                Arguments.of("""
                        Register A: 2024
                        Register B: 0
                        Register C: 0
                        
                        Program: 0,3,5,4,3,0""", 117440L)
        );
    }
}
