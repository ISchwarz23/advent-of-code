package aoc2023

import aoc2023.day08.Day08
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.aocClient
import utils.readInput
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day08Test {

    private val input = readInput("aoc2023/day08.txt")
    private val inputExamplePart1 = readInput("aoc2023/day08_example_part1.txt")
    private val inputExamplePart2 = readInput("aoc2023/day08_example_part2.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day08.part1(inputExamplePart1)

        // then
        assertEquals(2, result)

        // submit answer
        val answer = Day08.part1(input)
        val submissionResult = aocClient.submit(2023, 8, 1, answer)
        println("Result of Day 08 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    @Ignore("Solution not implemented")
    internal fun testPart2() {
        // when
        val result = Day08.part2(inputExamplePart2)

        // then
        assertEquals(6, result)

        // submit answer
        val answer = Day08.part2(input)
        val submissionResult = aocClient.submit(2023, 8, 2, answer)
        println("Result of Day 08 - Part 2: $answer (Submission: $submissionResult)")
    }

}