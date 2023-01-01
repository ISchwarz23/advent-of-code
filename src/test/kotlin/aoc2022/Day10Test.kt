package aoc2022

import aoc2022.day10.Day10
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day10Test {

    private val input = readInput("aoc2022/day10.txt")
    private val inputExample = readInput("aoc2022/day10_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day10.part1(inputExample)

        // then
        assertEquals(13140, result)

        // get solution
        println("Result of Day 10 - Part 1: ${Day10.part1(input)}")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day10.part2(inputExample)

        // then
        assertEquals(0, result)

        // get solution
        println("Result of Day 10 - Part 2: ${Day10.part2(input)}")
    }

}