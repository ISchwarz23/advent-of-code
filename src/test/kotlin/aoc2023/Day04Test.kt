package aoc2023

import aoc2023.day04.Day04
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.aocClient
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day04Test {

    private val input = readInput("aoc2023/day04.txt")
    private val inputExample = readInput("aoc2023/day04_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day04.part1(inputExample)

        // then
        assertEquals(13, result)

        // submit answer
        val answer = Day04.part1(input)
        val submissionResult = aocClient.submit(2023, 4, 1, answer)
        println("Result of Day 04 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day04.part2(inputExample)

        // then
        assertEquals(30, result)

        // submit answer
        val answer = Day04.part2(input)
        val submissionResult = aocClient.submit(2023, 4, 2, answer)
        println("Result of Day 04 - Part 2: $answer (Submission: $submissionResult)")
    }

}