package aoc2022

import aoc2022.day20.Day20
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.AocClient
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day20Test {

    private val aocClient = AocClient()

    private val testInput = readInput("aoc2022/Day20_test.txt")
    private val input = readInput("aoc2022/Day20.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day20.part1(testInput)

        // then
        assertEquals(3, result)

        // submit answer
        val answer = Day20.part1(input)
        val submissionResult = aocClient.submit(2022, 20, 1, answer)
        println("Result of Day 20 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day20.part2(testInput)

        // then
        assertEquals(1623178306, result)

        // submit answer
        val answer = Day20.part2(input)
        val submissionResult = aocClient.submit(2022, 20, 2, answer)
        println("Result of Day 20 - Part 2: $answer (Submission: $submissionResult)")
    }

}