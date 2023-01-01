package aoc2022

import aoc2022.day06.Day06
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.readOneLineInputAsString
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day06Test {

    private val testInput = readOneLineInputAsString("aoc2022/Day06_test.txt")
    private val input = readOneLineInputAsString("aoc2022/Day06.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day06.part1(testInput)

        // then
        assertEquals(5, result)

        // get solution
        println("Result of Day 06 - Part 1: ${Day06.part1(input)}")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day06.part2(testInput)

        // then
        assertEquals(23, result)

        // get solution
        println("Result of Day 06 - Part 2: ${Day06.part2(input)}")
    }

}