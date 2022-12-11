package aoc2022

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day01Test {

    private val testInput = readInput("aoc2022/Day01_test.txt")
    private val input = readInput("aoc2022/Day01.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day01.part1(testInput)

        // then
        assertEquals(24000, result)

        // get solution
        println("Result of Day 01 - Part 1: ${Day01.part1(input)}")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day01.part2(testInput)

        // then
        assertEquals(45000, result)

        // get solution
        println("Result of Day 01 - Part 2: ${Day01.part2(input)}")
    }

}