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
internal class Day11Test {

    private val aocClient = AocClient()

    private val testInput = readInput("aoc2022/Day11_test.txt")
    private val input = readInput("aoc2022/Day11.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day11.part1(testInput)

        // then
        assertEquals(10605, result)

        // submit answer
        val answer = Day11.part1(input)
        val submissionResult = "(Submission: ${aocClient.submit(2022, 11, 1, answer)})"
        println("Result of Day 11 - Part 1: $answer $submissionResult")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day11.part2(testInput)

        // then
        assertEquals(2713310158, result)

        // submit answer
        val answer = Day11.part2(input)
        val submissionResult = "(Submission: ${aocClient.submit(2022, 11, 2, answer)})"
        println("Result of Day 11 - Part 2: $answer $submissionResult")
    }

}