package aoc2022

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day10Test {

    private val testInput = readInput("Day10_test")
    private val input = readInput("Day10")

    @Test
    internal fun testPart1() {
        // when
        val result = Day10.part1(testInput)

        // then
        assertEquals(13140, result)

        // get solution
        println("Result of Day 10 - Part 1: ${Day10.part1(input)}")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day10.part2(testInput)

        // then
        assertEquals(0, result)

        // get solution
        println("Result of Day 10 - Part 2: ${Day10.part2(input)}")
    }

}