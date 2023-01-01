package aoc2022

import aoc2022.day02.Day02
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day02Test {

    private val input = readInput("aoc2022/day02.txt")
    private val inputExample = readInput("aoc2022/day02_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day02.part1(inputExample)

        // then
        assertEquals(15, result)

        // get solution
        println("Result of Day 02 - Part 1: ${Day02.part1(input)}")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day02.part2(inputExample)

        // then
        assertEquals(12, result)

        // get solution
        println("Result of Day 02 - Part 2: ${Day02.part2(input)}")
    }

}