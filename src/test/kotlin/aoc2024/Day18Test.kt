package aoc2024

import aoc2024.day18.Day18
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.Rect
import utils.aocClient
import utils.readInput
import utils.readInputAsVector2
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day18Test {

    private val input = readInputAsVector2("aoc2024/day18.txt")
    private val inputExample = readInputAsVector2("aoc2024/day18_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day18.part1(inputExample, Rect(7, 7), 12)

        // then
        assertEquals(22, result)

        // submit answer
        val answer = Day18.part1(input, Rect(71, 71), 1024)
        val submissionResult = aocClient.submit(2024, 18, 1, answer)
        println("Result of Day 18 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day18.part2(inputExample, Rect(7, 7), 12)

        // then
        assertEquals("6,1", result)

        // submit answer
        val answer = Day18.part2(input, Rect(71, 71), 1024)
        val submissionResult = aocClient.submit(2024, 18, 2, answer)
        println("Result of Day 18 - Part 2: $answer (Submission: $submissionResult)")
    }

}