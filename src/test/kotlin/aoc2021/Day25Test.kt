package aoc2021

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day25Test {

    private val testInput = readInput("aoc2021/day25_example.txt").map { it.toCharArray().toList() }
    private val input = readInput("aoc2021/day25.txt").map { it.toCharArray().toList() }

    @Test
    internal fun testPart1() {
        // when
        val result = Day25.part1(testInput)

        // then
        assertEquals(58, result)

        // get solution
        println("Result of Day 25 - Part 1: ${Day25.part1(input)}")
    }

}