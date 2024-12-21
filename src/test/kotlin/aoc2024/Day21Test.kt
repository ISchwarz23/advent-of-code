package aoc2024

import aoc2024.day21.Day21
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.aocClient
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day21Test {

    private val input = readInput("aoc2024/day21.txt")
    private val inputExample = readInput("aoc2024/day21_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day21.part1(inputExample)

        // then
        assertEquals(126384, result)

        // submit answer
        val answer = Day21.part1(input)
        val submissionResult = aocClient.submit(2024, 21, 1, answer)
        println("Result of Day 21 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // submit answer
        val answer = Day21.part2(input)
        val submissionResult = aocClient.submit(2024, 21, 2, answer)
        println("Result of Day 21 - Part 2: $answer (Submission: $submissionResult)")
    }

}