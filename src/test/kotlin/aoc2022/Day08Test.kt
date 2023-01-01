package aoc2022

import aoc2022.day08.Day08
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.readInputAs2dIntArray
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day08Test {

    private val input = readInputAs2dIntArray("aoc2022/day08.txt")
    private val inputExample = readInputAs2dIntArray("aoc2022/day08_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day08.part1(inputExample)

        // then
        assertEquals(21, result)

        // get solution
        println("Result of Day 08 - Part 1: ${Day08.part1(input)}")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day08.part2(inputExample)

        // then
        assertEquals(8, result)

        // get solution
        println("Result of Day 08 - Part 2: ${Day08.part2(input)}")
    }

}