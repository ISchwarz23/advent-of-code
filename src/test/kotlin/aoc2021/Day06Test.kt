package aoc2021

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.readOneLineInputAsInts
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day06Test {

    private val testInput = readOneLineInputAsInts("aoc2021/day06_example.txt")
    private val input = readOneLineInputAsInts("aoc2021/day06.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day06.part1(testInput)

        // then
        assertEquals(5934, result)

        // get solution
        println("Result of Day 06 - Part 1: ${Day06.part1(input)}")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day06.part2(testInput)

        // then
        assertEquals(26984457539, result)

        // get solution
        println("Result of Day 06 - Part 2: ${Day06.part2(input)}")
    }
}