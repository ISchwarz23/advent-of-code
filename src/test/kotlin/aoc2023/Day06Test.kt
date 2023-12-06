package aoc2023

import aoc2023.day06.Day06
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.aocClient
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day06Test {

    private val input = readInput("aoc2023/day06.txt")
    private val inputExample = readInput("aoc2023/day06_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day06.part1(inputExample)

        // then
        assertEquals(288, result)

        // submit answer
        val answer = Day06.part1(input)
        val submissionResult = aocClient.submit(2023, 6, 1, answer)
        println("Result of Day 06 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day06.part2(inputExample)

        // then
        assertEquals(71503, result)

        // submit answer
        val answer = Day06.part2(input)
        val submissionResult = aocClient.submit(2023, 6, 2, answer)
        println("Result of Day 06 - Part 2: $answer (Submission: $submissionResult)")
    }

}