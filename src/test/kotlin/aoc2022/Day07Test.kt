package aoc2022

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day07Test {

    private val testInput = readInput("Day07_test")
    private val input = readInput("Day07")

    @Test
    internal fun testPart1() {
        // when
        val result = Day07.part1(testInput)

        // then
        assertEquals(95437, result)

        // get solution
        println("Result of Day 07 - Part 1: ${Day07.part1(input)}")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day07.part2(testInput)

        // then
        assertEquals(24933642, result)

        // get solution
        println("Result of Day 07 - Part 2: ${Day07.part2(input)}")
    }

}