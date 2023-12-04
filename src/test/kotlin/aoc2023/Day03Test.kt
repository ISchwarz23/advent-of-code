package aoc2023

import aoc2023.day03.Day03
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.aocClient
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day03Test {

    private val input = readInput("aoc2023/day03.txt")
    private val inputExample = readInput("aoc2023/day03_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day03.part1(inputExample)

        // then
        assertEquals(4361, result)

        // submit answer
        val answer = Day03.part1(input)
        val submissionResult = aocClient.submit(2023, 3, 1, answer)
        println("Result of Day 03 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day03.part2(inputExample)

        // then
        assertEquals(467835, result)

        // submit answer
        val answer = Day03.part2(input)
        val submissionResult = aocClient.submit(2023, 3, 2, answer)
        println("Result of Day 03 - Part 2: $answer (Submission: $submissionResult)")
    }

}