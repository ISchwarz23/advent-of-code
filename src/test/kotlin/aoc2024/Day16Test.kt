package aoc2024

import aoc2024.day16.Day16
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.aocClient
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day16Test {

    private val input = readInput("aoc2024/day16.txt")
    private val inputExample = readInput("aoc2024/day16_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day16.part1(inputExample)

        // then
        assertEquals(7036, result)

        // submit answer
        val answer = Day16.part1(input)
        val submissionResult = aocClient.submit(2024, 16, 1, answer)
        println("Result of Day 16 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day16.part2(inputExample)

        // then
        assertEquals(45, result)

        // submit answer
        val answer = Day16.part2(input)
        val submissionResult = aocClient.submit(2024, 16, 2, answer)
        println("Result of Day 16 - Part 2: $answer (Submission: $submissionResult)")
    }

}