package aoc2022

import aoc2022.day01.Day01
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day01Test {

    private val input = readInput("aoc2022/day01.txt")
    private val inputExample = readInput("aoc2022/day01_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day01.part1(inputExample)

        // then
        assertEquals(24000, result)

        // get solution
        println("Result of Day 01 - Part 1: ${Day01.part1(input)}")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day01.part2(inputExample)

        // then
        assertEquals(45000, result)

        // get solution
        println("Result of Day 01 - Part 2: ${Day01.part2(input)}")
    }

}