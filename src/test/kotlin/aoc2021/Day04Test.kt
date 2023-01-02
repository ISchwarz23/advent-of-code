package aoc2021

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day04Test {

    private val testInput = readInput("aoc2021/day04_example.txt")
    private val input = readInput("aoc2021/day04.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day04.part1(testInput)

        // then
        assertEquals(4512, result)

        // get solution
        println("Result of Day 04 - Part 1: ${Day04.part1(input)}")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day04.part2(testInput)

        // then
        assertEquals(1924, result)

        // get solution
        println("Result of Day 04 - Part 2: ${Day04.part2(input)}")
    }

}