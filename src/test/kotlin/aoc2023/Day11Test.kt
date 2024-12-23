package aoc2023

import aoc2023.day11.Day11
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.aocClient
import utils.readInput
import utils.readInputAsChars
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day11Test {

    private val input = readInputAsChars("aoc2023/day11.txt")
    private val inputExample = readInputAsChars("aoc2023/day11_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day11.part1(inputExample)

        // then
        assertEquals(374, result)

        // submit answer
        val answer = Day11.part1(input)
        val submissionResult = aocClient.submit(2023, 11, 1, answer)
        println("Result of Day 11 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day11.part2(inputExample, 100)

        // then
        assertEquals(8410, result)

        // submit answer
        val answer = Day11.part2(input, 1000000)
        val submissionResult = aocClient.submit(2023, 11, 2, answer)
        println("Result of Day 11 - Part 2: $answer (Submission: $submissionResult)")
    }

}