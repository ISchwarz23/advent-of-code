package aoc2021

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.readInput
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
@Ignore("Not working")
internal class Day24Test {

    // private val testInput = readInputAsInstructions("aoc2021/day24_example.txt")
    // private val input = readInputAsInstructions("aoc2021/day24.txt")

    @Test
    internal fun testPart1() {
        // get solution
        println("Result of Day 24 - Part 1: ${Day24.part1(emptyList())}")
    }

    @Test
    internal fun testPart2() {
        // get solution
        println("Result of Day 24 - Part 2: ${Day24.part2(emptyList())}")
    }

    private fun readInputAsInstructions(name: String): List<Instruction> {
        return readInput(name).map { it.split(" ") }.map { Instruction(it[0].toOperation(), it[1], if(it.size > 2) it[2] else null) }
    }

    private fun String.toOperation(): Operation {
        return when (this) {
            "inp" -> Operation.INPUT
            "add" -> Operation.ADD
            "mul" -> Operation.MULTIPLY
            "div" -> Operation.DIVIDE
            "mod" -> Operation.MODULO
            "eql" -> Operation.EQUALS
            else -> throw IllegalArgumentException("Unknown operation '$this'!")
        }
    }

}