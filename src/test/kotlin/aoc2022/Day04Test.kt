package aoc2022

import aoc2022.day04.Day04
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day04Test {

    private val testInput = readInput("aoc2022/Day04_test.txt")
    private val input = readInput("aoc2022/Day04.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day04.part1(testInput)

        // then
        assertEquals(2, result)

        // get solution
        println("Result of Day 04 - Part 1: ${Day04.part1(input)}")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day04.part2(testInput)

        // then
        assertEquals(4, result)

        // get solution
        println("Result of Day 04 - Part 2: ${Day04.part2(input)}")
    }

}