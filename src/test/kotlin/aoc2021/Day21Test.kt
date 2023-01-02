package aoc2021

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day21Test {

    private val testInput = readInputAsPlayerStartPositions("aoc2021/day21_example.txt")
    private val input = readInputAsPlayerStartPositions("aoc2021/day21.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day21.part1(testInput.first, testInput.second)

        // then
        assertEquals(739785, result)

        // get solution
        println("Result of Day 21 - Part 1: ${Day21.part1(input.first, input.second)}")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day21.part2(testInput.first, testInput.second)

        // then
        assertEquals(444356092776315, result)

        // get solution
        println("Result of Day 21 - Part 2: ${Day21.part2(input.first, input.second)}")
    }

    private fun readInputAsPlayerStartPositions(name: String): Pair<Int, Int> {
        val startPositions = readInput(name).map { it.substring(28) }.map { it.toInt() }
        return Pair(startPositions[0], startPositions[1])
    }

}
