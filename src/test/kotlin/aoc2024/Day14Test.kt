package aoc2024

import aoc2024.day14.Day14
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.Rect
import utils.aocClient
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day14Test {

    private val input = readInput("aoc2024/day14.txt")
    private val inputExample = readInput("aoc2024/day14_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day14.part1(inputExample, Rect(11, 7))

        // then
        assertEquals(12, result)

        // submit answer
        val answer = Day14.part1(input, Rect(101, 103))
        val submissionResult = aocClient.submit(2024, 14, 1, answer)
        println("Result of Day 14 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // submit answer
        val answer = Day14.part2(input, Rect(101, 103))
        val submissionResult = aocClient.submit(2024, 14, 2, answer)
        println("Result of Day 14 - Part 2: $answer (Submission: $submissionResult)")
    }

}