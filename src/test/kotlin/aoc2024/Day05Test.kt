package aoc2024

import aoc2024.day05.Day05
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.aocClient
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day05Test {

    private val input = readInput("aoc2024/day05.txt")
    private val inputExample = readInput("aoc2024/day05_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day05.part1(inputExample)

        // then
        assertEquals(143, result)

        // submit answer
        val answer = Day05.part1(input)
        val submissionResult = aocClient.submit(2024, 5, 1, answer)
        println("Result of Day 05 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day05.part2(inputExample)

        // then
        assertEquals(123, result)

        // submit answer
        val answer = Day05.part2(input)
        val submissionResult = aocClient.submit(2024, 5, 2, answer)
        println("Result of Day 05 - Part 2: $answer (Submission: $submissionResult)")
    }

}