package aoc2023

import aoc2023.day07.Day07
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.aocClient
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day07Test {

    private val input = readInput("aoc2023/day07.txt")
    private val inputExample = readInput("aoc2023/day07_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day07.part1(inputExample)

        // then
        assertEquals(6440, result)

        // submit answer
        val answer = Day07.part1(input)
        val submissionResult = aocClient.submit(2023, 7, 1, answer)
        println("Result of Day 07 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day07.part2(inputExample)

        // then
        assertEquals(5905, result)

        // submit answer
        val answer = Day07.part2(input)
        val submissionResult = aocClient.submit(2023, 7, 2, answer)
        println("Result of Day 07 - Part 2: $answer (Submission: $submissionResult)")
    }

}