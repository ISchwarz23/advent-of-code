package aoc2024

import aoc2024.day10.Day10
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.aocClient
import utils.readInput
import utils.readInputAs2dIntArray
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day10Test {

    private val input = readInputAs2dIntArray("aoc2024/day10.txt")
    private val inputExample = readInputAs2dIntArray("aoc2024/day10_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day10.part1(inputExample)

        // then
        assertEquals(36, result)

        // submit answer
        val answer = Day10.part1(input)
        val submissionResult = aocClient.submit(2024, 10, 1, answer)
        println("Result of Day 10 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day10.part2(inputExample)

        // then
        assertEquals(81, result)

        // submit answer
        val answer = Day10.part2(input)
        val submissionResult = aocClient.submit(2024, 10, 2, answer)
        println("Result of Day 10 - Part 2: $answer (Submission: $submissionResult)")
    }

}