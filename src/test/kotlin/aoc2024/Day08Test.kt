package aoc2024

import aoc2024.day08.Day08
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.aocClient
import utils.readInput
import utils.readInputAsChars
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day08Test {

    private val input = readInputAsChars("aoc2024/day08.txt")
    private val inputExample = readInputAsChars("aoc2024/day08_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day08.part1(inputExample)

        // then
        assertEquals(14, result)

        // submit answer
        val answer = Day08.part1(input)
        val submissionResult = aocClient.submit(2024, 8, 1, answer)
        println("Result of Day 08 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day08.part2(inputExample)

        // then
        assertEquals(34, result)

        // submit answer
        val answer = Day08.part2(input)
        val submissionResult = aocClient.submit(2024, 8, 2, answer)
        println("Result of Day 08 - Part 2: $answer (Submission: $submissionResult)")
    }

}