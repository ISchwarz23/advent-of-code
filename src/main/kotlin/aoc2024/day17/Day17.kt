package aoc2024.day17

import utils.pow


/**
 * My solution for day 17 of Advent of Code 2024.
 * The puzzle can be found at the <a href="https://adventofcode.com/2024/day/17">AoC page</a>.
 */
object Day17 {

    fun part1(input: List<String>): String {
        val valueRegA = input[0].split(": ")[1].toLong()
        val valueRegB = input[1].split(": ")[1].toLong()
        val valueRegC = input[2].split(": ")[1].toLong()
        val program = input[4].split(": ")[1].split(",").map { it.toInt() }

        val registers = Registers(valueRegA, valueRegB, valueRegC)

        return runProgram(program, registers).joinToString(",")
    }

    fun part2(input: List<String>): Long {
        val valueRegB = input[1].split(": ")[1].toLong()
        val valueRegC = input[2].split(": ")[1].toLong()

        val programStr = input[4].split(": ")[1].split(",")
        val program = programStr.map { it.toInt() }

        var valueRegA = 0L
        var previousOutput = emptyList<String>()
        do {
            val delta = calculateDeltaExponent(programStr, previousOutput)
            val step = (8L pow delta)
            valueRegA += step

            val output = runProgram(program, Registers(valueRegA, valueRegB, valueRegC))

            previousOutput = output
        } while (output != programStr)

        return valueRegA
    }

    private fun runProgram(program: List<Int>, registers: Registers): List<String> {
        val outputs = mutableListOf<String>()
        var pointer = 0
        while (pointer + 1 < program.size) {
            val operation = readOperation(program[pointer])
            val literalOperant = program[pointer + 1]
            val (newPointer, output) = operation.run(pointer, literalOperant, registers)
            pointer = newPointer
            output?.let { outputs += it }
        }
        return outputs
    }

}

private fun calculateDeltaExponent(expected: List<String>, actual: List<String>): Int {
    if (expected.size > actual.size) {
        return expected.lastIndex
    }

    (expected.lastIndex downTo 1).forEach { index ->
        if (expected[index] != actual[index]) {
            return index
        }
    }
    return 0
}

private data class Registers(var a: Long, var b: Long, var c: Long) {

    fun toComboOperant(literalOperant: Int): Long {
        if (literalOperant in 0..3) {
            return literalOperant.toLong()
        }
        return when (literalOperant) {
            4 -> a
            5 -> b
            6 -> c
            else -> throw RuntimeException("Combo Operant '$literalOperant' should not appear in valid program")
        }
    }

}

private fun readOperation(opcode: Int): Operation {
    return when(opcode) {
        0 -> Operation.ADV
        1 -> Operation.BXL
        2 -> Operation.BST
        3 -> Operation.JNZ
        4 -> Operation.BXC
        5 -> Operation.OUT
        6 -> Operation.BDV
        7 -> Operation.CDV
        else -> throw RuntimeException("Unknown opcode '$opcode'")
    }
}


private enum class Operation(val run: (pointer: Int, literalOperant: Int, registers: Registers) -> Pair<Int, String?>) {
    // division
    ADV({pointer, literalOperant, registers ->
        val result = registers.a.toDouble() / (2L pow registers.toComboOperant(literalOperant))
        registers.a = result.toLong()
        Pair(pointer + 2, null)
    }),
    // bitwise X-OR
    BXL({pointer, literalOperant, registers ->
        registers.b = registers.b xor literalOperant.toLong()
        Pair(pointer + 2, null)
    }),
    // bitwise X-OR
    BST({pointer, literalOperant, registers ->
        registers.b = registers.toComboOperant(literalOperant) % 8
        Pair(pointer + 2, null)
    }),
    // jump
    JNZ({pointer, literalOperant, registers ->
        if(registers.a == 0L) {
            Pair(pointer + 2, null)
        } else {
            Pair(literalOperant, null)
        }
    }),
    // bitwise X-OR
    BXC({pointer, _, registers ->
        registers.b = registers.b xor registers.c
        Pair(pointer + 2, null)
    }),
    // output
    OUT({pointer, literalOperant, registers ->
        val valueToPrint = registers.toComboOperant(literalOperant) % 8
        Pair(pointer + 2, "$valueToPrint")
    }),
    // division
    BDV({pointer, literalOperant, registers ->
        val result = registers.a.toDouble() / (2L pow registers.toComboOperant(literalOperant))
        registers.b = result.toLong()
        Pair(pointer + 2, null)
    }),
    // division
    CDV({pointer, literalOperant, registers ->
        val result = registers.a.toDouble() / (2L pow registers.toComboOperant(literalOperant))
        registers.c = result.toLong()
        Pair(pointer + 2, null)
    })
}
