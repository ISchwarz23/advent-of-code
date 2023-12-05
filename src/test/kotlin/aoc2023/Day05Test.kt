package aoc2023

import aoc2023.day05.Day05
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.aocClient
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day05Test {

    private val input = readInput("aoc2023/day05.txt")
    private val inputExample = readInput("aoc2023/day05_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day05.part1(inputExample)

        // then
        assertEquals(35, result)

        // submit answer
        val answer = Day05.part1(input)
        val submissionResult = aocClient.submit(2023, 5, 1, answer)
        println("Result of Day 05 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day05.part2(inputExample)

        // then
        assertEquals(46, result)

        // submit answer
        val answer = Day05.part2(input)
        val submissionResult = aocClient.submit(2023, 5, 2, answer)
        println("Result of Day 05 - Part 2: $answer (Submission: $submissionResult)")
    }

}