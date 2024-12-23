package aoc2024

import aoc2024.day12.Day12
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
internal class Day12Test {

    private val input = readInputAsChars("aoc2024/day12.txt")
    private val inputExample = readInputAsChars("aoc2024/day12_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day12.part1(inputExample)

        // then
        assertEquals(140, result)

        // submit answer
        val answer = Day12.part1(input)
        val submissionResult = aocClient.submit(2024, 12, 1, answer)
        println("Result of Day 12 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day12.part2(inputExample)

        // then
        assertEquals(80, result)

        // submit answer
        val answer = Day12.part2(input)
        val submissionResult = aocClient.submit(2024, 12, 2, answer)
        println("Result of Day 12 - Part 2: $answer (Submission: $submissionResult)")
    }

}