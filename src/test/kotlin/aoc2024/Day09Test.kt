package aoc2024

import aoc2024.day09.Day09
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
internal class Day09Test {

    private val input = readInputAsChars("aoc2024/day09.txt")[0]
    private val inputExample = readInputAsChars("aoc2024/day09_example.txt")[0]

    @Test
    internal fun testPart1() {
        // when
        val result = Day09.part1(inputExample)

        // then
        assertEquals(1928, result)

        // submit answer
        val answer = Day09.part1(input)
        val submissionResult = aocClient.submit(2024, 9, 1, answer)
        println("Result of Day 09 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day09.part2(inputExample)

        // then
        assertEquals(2858, result)

        // submit answer
        val answer = Day09.part2(input)
        val submissionResult = aocClient.submit(2024, 9, 2, answer)
        println("Result of Day 09 - Part 2: $answer (Submission: $submissionResult)")
    }

}