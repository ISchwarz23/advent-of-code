package aoc2022

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.readInput
import utils.readInputAs2dIntArray
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day08Test {

    private val testInput = readInputAs2dIntArray("aoc2022/Day08_test.txt")
    private val input = readInputAs2dIntArray("aoc2022/Day08.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day08.part1(testInput)

        // then
        assertEquals(21, result)

        // get solution
        println("Result of Day 08 - Part 1: ${Day08.part1(input)}")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day08.part2(testInput)

        // then
        assertEquals(8, result)

        // get solution
        println("Result of Day 08 - Part 2: ${Day08.part2(input)}")
    }

}