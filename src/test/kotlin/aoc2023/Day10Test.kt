package aoc2023

import aoc2023.day10.Day10
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.TestMethodOrder
import utils.aocClient
import utils.readInput
import utils.readInputAsChars
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day10Test {

    private val input = readInput("aoc2023/day10.txt")
    private val inputExamplePart1 = readInput("aoc2023/day10_example_part1.txt")
    private val inputExamplePart2 = readInput("aoc2023/day10_example_part2.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day10.part1(inputExamplePart1)

        // then
        assertEquals(8, result)

        // submit answer
        val answer = Day10.part1(input)
        val submissionResult = aocClient.submit(2023, 10, 1, answer)
        println("Result of Day 10 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day10.part2(inputExamplePart2)

        // then
        assertEquals(10, result)

        // submit answer
        val answer = Day10.part2(input)
        val submissionResult = aocClient.submit(2023, 10, 2, answer)
        println("Result of Day 10 - Part 2: $answer (Submission: $submissionResult)")
    }

}