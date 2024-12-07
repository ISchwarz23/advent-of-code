package aoc2024

import aoc2024.day01.Day01
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

    private val input = readInput("aoc2024/day01.txt")
    private val inputExample = readInput("aoc2024/day01_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day01.part1(inputExample)

        // then
        assertEquals(11, result)

        // submit answer
        val answer = Day01.part1(input)
        val submissionResult = aocClient.submit(2024, 1, 1, answer)
        println("Result of Day 01 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day01.part2(inputExample)

        // then
        assertEquals(31, result)

        // submit answer
        val answer = Day01.part2(input)
        val submissionResult = aocClient.submit(2024, 1, 2, answer)
        println("Result of Day 01 - Part 2: $answer (Submission: $submissionResult)")
    }

}