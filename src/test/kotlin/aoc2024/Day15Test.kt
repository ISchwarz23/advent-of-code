package aoc2024

import aoc2024.day15.Day15
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.aocClient
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day15Test {

    private val input = readInput("aoc2024/day15.txt")
    private val inputExample = readInput("aoc2024/day15_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day15.part1(inputExample)

        // then
        assertEquals(10092, result)

        // submit answer
        val answer = Day15.part1(input)
        val submissionResult = aocClient.submit(2024, 15, 1, answer)
        println("Result of Day 15 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day15.part2(inputExample)

        // then
        assertEquals(-1, result)

        // submit answer
        val answer = Day15.part2(input)
        val submissionResult = aocClient.submit(2024, 15, 2, answer)
        println("Result of Day 15 - Part 2: $answer (Submission: $submissionResult)")
    }

}