package aoc2024.day17

import utils.pow

/**
 * My solution for day 17 of Advent of Code 2024.
 * The puzzle can be found at the <a href="https://adventofcode.com/2024/day/17">AoC page</a>.
 */
object Day17 {

    fun part1(input: List<String>): String {
        val valueRegA = input[0].split(": ")[1].toInt()
        val valueRegB = input[1].split(": ")[1].toInt()
        val valueRegC = input[2].split(": ")[1].toInt()

        val registers = Registers(valueRegA, valueRegB, valueRegC)
        val program = input[4].split(": ")[1].split(",").map { it.toInt() }

        val outputs = mutableListOf<String>()
        var pointer = 0
        while (pointer + 1 < program.size) {
            val operation = readOperation(program[pointer])
            val literalOperant = program[pointer + 1]
            val (newPointer, output) = operation.run(pointer, literalOperant, registers)
            pointer = newPointer
            output?.let { outputs += it }
        }

        return outputs.joinToString(",")
    }

    fun part2(input: List<String>): Int {
        return 0
    }

}

private data class Registers(var a: Int, var b: Int, var c: Int) {

    fun toComboOperant(literalOperant: Int): Int {
        if (literalOperant in 0..3) {
            return literalOperant
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
        val result = registers.a.toDouble() / (2 pow registers.toComboOperant(literalOperant))
        registers.a = result.toInt()
        Pair(pointer + 2, null)
    }),
    // bitwise X-OR
    BXL({pointer, literalOperant, registers ->
        registers.b = registers.b xor literalOperant
        Pair(pointer + 2, null)
    }),
    // bitwise X-OR
    BST({pointer, literalOperant, registers ->
        registers.b = registers.toComboOperant(literalOperant) % 8
        Pair(pointer + 2, null)
    }),
    // jump
    JNZ({pointer, literalOperant, registers ->
        if(registers.a == 0) {
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
        val result = registers.a.toDouble() / (2 pow registers.toComboOperant(literalOperant))
        registers.b = result.toInt()
        Pair(pointer + 2, null)
    }),
    // division
    CDV({pointer, literalOperant, registers ->
        val result = registers.a.toDouble() / (2 pow registers.toComboOperant(literalOperant))
        registers.c = result.toInt()
        Pair(pointer + 2, null)
    })
}
