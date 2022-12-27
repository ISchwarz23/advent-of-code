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
internal class Day13Test {

    private val aocClient = AocClient()

    private val testInput = readInput("aoc2022/Day13_test.txt")
    private val input = readInput("aoc2022/Day13.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day13.part1(testInput)

        // then
        assertEquals(13, result)

        // submit answer
        val answer = Day13.part1(input)
        val submissionResult = aocClient.submit(2022, 13, 1, answer)
        println("Result of Day 13 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day13.part2(testInput)

        // then
        assertEquals(140, result)

        // submit answer
        val answer = Day13.part2(input)
        val submissionResult = aocClient.submit(2022, 13, 2, answer)
        println("Result of Day 13 - Part 2: $answer (Submission: $submissionResult)")
    }

}