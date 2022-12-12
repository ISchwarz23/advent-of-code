package aoc2022

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.AocClient
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day12Test {

    private val aocClient = AocClient()

    private val testInput = readInput("aoc2022/Day12_test.txt")
    private val input = readInput("aoc2022/Day12.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day12.part1(testInput)

        // then
        assertEquals(31, result)

        // submit answer
        val answer = Day12.part1(input)
        val submissionResult = "(Submission: ${aocClient.submit(2022, 12, 1, answer)})"
        println("Result of Day 12 - Part 1: $answer $submissionResult")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day12.part2(testInput)

        // then
        assertEquals(29, result)

        // submit answer
        val answer = Day12.part2(input)
        val submissionResult = "(Submission: ${aocClient.submit(2022, 12, 2, answer)})"
        println("Result of Day 12 - Part 2: $answer $submissionResult")
    }

}