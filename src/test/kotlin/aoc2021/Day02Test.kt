package aoc2021

import aoc2021.day02.Day02
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day02Test {

    private val testInput = readInput("aoc2021/day02_example.txt")
    private val input = readInput("aoc2021/day02.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day02.part1(testInput)

        // then
        assertEquals(150, result)

        // get solution
        println("Result of Day 02 - Part 1: ${Day02.part1(input)}")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day02.part2(testInput)

        // then
        assertEquals(900, result)

        // get solution
        println("Result of Day 02 - Part 2: ${Day02.part2(input)}")
    }

}