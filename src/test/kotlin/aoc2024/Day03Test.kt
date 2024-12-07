package aoc2024

import aoc2024.day03.Day03
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

    private val input = readInput("aoc2024/day03.txt")
    private val inputExample = readInput("aoc2024/day03_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day03.part1(inputExample)

        // then
        assertEquals(161, result)

        // submit answer
        val answer = Day03.part1(input)
        val submissionResult = aocClient.submit(2024, 3, 1, answer)
        println("Result of Day 03 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day03.part2(inputExample)

        // then
        assertEquals(48, result)

        // submit answer
        val answer = Day03.part2(input)
        val submissionResult = aocClient.submit(2024, 3, 2, answer)
        println("Result of Day 03 - Part 2: $answer (Submission: $submissionResult)")
    }

}