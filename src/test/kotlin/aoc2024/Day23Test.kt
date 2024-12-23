package aoc2024

import aoc2024.day23.Day23
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.aocClient
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day23Test {

    private val input = readInput("aoc2024/day23.txt")
    private val inputExample = readInput("aoc2024/day23_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day23.part1(inputExample)

        // then
        assertEquals(7, result)

        // submit answer
        val answer = Day23.part1(input)
        val submissionResult = aocClient.submit(2024, 23, 1, answer)
        println("Result of Day 23 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day23.part2(inputExample)

        // then
        assertEquals("co,de,ka,ta", result)

        // submit answer
        val answer = Day23.part2(input)
        val submissionResult = aocClient.submit(2024, 23, 2, answer)
        println("Result of Day 23 - Part 2: $answer (Submission: $submissionResult)")
    }

}