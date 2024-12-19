package aoc2024

import aoc2024.day19.Day19
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.aocClient
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day19Test {

    private val input = readInput("aoc2024/day19.txt")
    private val inputExample = readInput("aoc2024/day19_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day19.part1(inputExample)

        // then
        assertEquals(6, result)

        // submit answer
        val answer = Day19.part1(input)
        val submissionResult = aocClient.submit(2024, 19, 1, answer)
        println("Result of Day 19 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day19.part2(inputExample)

        // then
        assertEquals(16, result)

        // submit answer
        val answer = Day19.part2(input)
        val submissionResult = aocClient.submit(2024, 19, 2, answer)
        println("Result of Day 19 - Part 2: $answer (Submission: $submissionResult)")
    }

}