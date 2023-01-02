package aoc2021

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.readOneLineInputAsInts
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day07Test {

    private val testInput = readOneLineInputAsInts("aoc2021/day07_example.txt")
    private val input = readOneLineInputAsInts("aoc2021/day07.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day07.part1(testInput)

        // then
        assertEquals(37, result)

        // get solution
        println("Result of Day 07 - Part 1: ${Day07.part1(input)}")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day07.part2(testInput)

        // then
        assertEquals(168, result)

        // get solution
        println("Result of Day 07 - Part 2: ${Day07.part2(input)}")
    }
}