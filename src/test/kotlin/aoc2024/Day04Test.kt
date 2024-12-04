package aoc2024

import aoc2024.day04.Day04
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
internal class Day04Test {

    private val input = readInputAsChars("aoc2024/day04.txt")
    private val inputExample = readInputAsChars("aoc2024/day04_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day04.part1(inputExample)

        // then
        assertEquals(18, result)

        // submit answer
        val answer = Day04.part1(input)
        val submissionResult = aocClient.submit(2024, 4, 1, answer)
        println("Result of Day 04 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day04.part2(inputExample)

        // then
        assertEquals(9, result)

        // submit answer
        val answer = Day04.part2(input)
        val submissionResult = aocClient.submit(2024, 4, 2, answer)
        println("Result of Day 04 - Part 2: $answer (Submission: $submissionResult)")
    }

}