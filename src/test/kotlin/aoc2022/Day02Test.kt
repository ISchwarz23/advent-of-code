package aoc2022

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.readInput
import java.lang.RuntimeException
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day02Test {

    private val testInput = readInput("Day02_test")
    private val input = readInput("Day02")

    @Test
    internal fun testPart1() {
        // when
        val result = Day02.part1(testInput)

        // then
        assertEquals(15, result)

        // get solution
        println("Result of Day 02 - Part 1: ${Day02.part1(input)}")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day02.part2(testInput)

        // then
        assertEquals(12, result)

        // get solution
        println("Result of Day 02 - Part 2: ${Day02.part2(input)}")
    }

}