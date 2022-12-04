package aoc2022

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day04Test {

    private val testInput = readInput("Day04_test")
    private val input = readInput("Day04")

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