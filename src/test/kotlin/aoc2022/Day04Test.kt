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

    private val input = readInput("aoc2022/day04.txt")
    private val inputExample = readInput("aoc2022/day04_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day04.part1(inputExample)

        // then
        assertEquals(2, result)

        // get solution
        println("Result of Day 04 - Part 1: ${Day04.part1(input)}")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day04.part2(inputExample)

        // then
        assertEquals(4, result)

        // get solution
        println("Result of Day 04 - Part 2: ${Day04.part2(input)}")
    }

}