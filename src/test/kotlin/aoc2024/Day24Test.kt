package aoc2024

import aoc2024.day24.Day24
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.aocClient
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day24Test {

    private val input = readInput("aoc2024/day24.txt")
    private val inputExample = readInput("aoc2024/day24_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day24.part1(inputExample)

        // then
        assertEquals(2024, result)

        // submit answer
        val answer = Day24.part1(input)
        val submissionResult = aocClient.submit(2024, 24, 1, answer)
        println("Result of Day 24 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day24.part2(inputExample)

        // then
        assertEquals(-1, result)

        // submit answer
        val answer = Day24.part2(input)
        val submissionResult = aocClient.submit(2024, 24, 2, answer)
        println("Result of Day 24 - Part 2: $answer (Submission: $submissionResult)")
    }

}