package aoc2022

import aoc2022.day09.Day09
import aoc2022.day09.Direction
import aoc2022.day09.Movement
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day09Test {

    private val input = readMovementInstructions("aoc2022/day09.txt")
    private val inputExample = readMovementInstructions("aoc2022/day09_example.txt")
    private val inputExampleComplex = readMovementInstructions("aoc2022/day09_example_complex.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day09.part1(inputExample)

        // then
        assertEquals(13, result)

        // get solution
        println("Result of Day 09 - Part 1: ${Day09.part1(input)}")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day09.part2(inputExampleComplex)

        // then
        assertEquals(36, result)

        // get solution
        println("Result of Day 09 - Part 2: ${Day09.part2(input)}")
    }

}

private fun readMovementInstructions(name: String): List<Movement> {
    return readInput(name).map { it.split(" ") }.map { Movement(it[0].toDirection(), it[1].toInt()) }
}

private fun String.toDirection(): Direction {
    return Direction.fromString(this)
}
