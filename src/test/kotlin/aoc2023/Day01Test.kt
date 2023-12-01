package aoc2023

import aoc2023.day01.Day01
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.aocClient
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day01Test {

    private val inputPart1 = readInput("aoc2023/day01.txt")
    private val inputExamplePart1 = readInput("aoc2023/day01_example_part1.txt")
    private val inputExamplePart2 = readInput("aoc2023/day01_example_part2.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day01.part1(inputExamplePart1)

        // then
        assertEquals(142, result)

        // submit answer
        val answer = Day01.part1(inputPart1)
        val submissionResult = aocClient.submit(2023, 1, 1, answer)
        println("Result of Day 01 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day01.part2(inputExamplePart2)

        // then
        assertEquals(281, result)

        // submit answer
        val answer = Day01.part2(inputPart1)
        val submissionResult = aocClient.submit(2023, 1, 2, answer)
        println("Result of Day 01 - Part 2: $answer (Submission: $submissionResult)")
    }

}