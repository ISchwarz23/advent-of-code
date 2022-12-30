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
internal class Day16Test {

    private val aocClient = AocClient()

    private val testInput = readInput("aoc2022/Day16_test.txt")
    private val input = readInput("aoc2022/Day16.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day16.part1(testInput)

        // then
        assertEquals(1651, result)

        // submit answer
        val answer = Day16.part1(input)
        val submissionResult = aocClient.submit(2022, 16, 1, answer)
        println("Result of Day 16 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day16.part2(testInput)

        // then
        assertEquals(0, result)

        // submit answer
        val answer = Day16.part2(input)
        val submissionResult = aocClient.submit(2022, 16, 2, answer)
        println("Result of Day 16 - Part 2: $answer (Submission: $submissionResult)")
    }

}