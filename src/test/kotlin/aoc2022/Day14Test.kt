package aoc2022

import aoc2022.day14.Day14
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.*
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day14Test {

    private val aocClient = AocClient()

    private val testInput = readInput("aoc2022/Day14_test.txt")
    private val input = readInput("aoc2022/Day14.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day14.part1(testInput)

        // then
        assertEquals(24, result)

        // submit answer
        val answer = Day14.part1(input)
        val submissionResult = aocClient.submit(2022, 14, 1, answer)
        println("Result of Day 14 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day14.part2(testInput)

        // then
        assertEquals(93, result)

        // submit answer
        val answer = Day14.part2(input)
        val submissionResult = aocClient.submit(2022, 14, 2, answer)
        println("Result of Day 14 - Part 2: $answer (Submission: $submissionResult)")
    }

}