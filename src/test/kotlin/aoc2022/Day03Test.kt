package aoc2022

import aoc2022.day03.Day03
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day03Test {

    private val input = readInput("aoc2022/day03.txt")
    private val inputExample = readInput("aoc2022/day03_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day03.part1(inputExample)

        // then
        assertEquals(157, result)

        // get solution
        println("Result of Day 03 - Part 1: ${Day03.part1(input)}")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day03.part2(inputExample)

        // then
        assertEquals(70, result)

        // get solution
        println("Result of Day 03 - Part 2: ${Day03.part2(input)}")
    }

}