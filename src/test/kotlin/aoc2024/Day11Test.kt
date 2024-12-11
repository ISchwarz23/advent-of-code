package aoc2024

import aoc2024.day11.Day11
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.*
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day11Test {

    private val input = readInput("aoc2024/day11.txt")[0].split("\\s".toRegex()).map { it.toLong() }
    private val inputExample = readInput("aoc2024/day11_example.txt")[0].split("\\s".toRegex()).map { it.toLong() }

    @Test
    internal fun testPart1() {
        // when
        val result = Day11.part1(inputExample)

        // then
        assertEquals(55312, result)

        // submit answer
        val answer = Day11.part1(input)
        val submissionResult = aocClient.submit(2024, 11, 1, answer)
        println("Result of Day 11 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // submit answer
        val answer = Day11.part2(input)
        val submissionResult = aocClient.submit(2024, 11, 2, answer)
        println("Result of Day 11 - Part 2: $answer (Submission: $submissionResult)")
    }

}