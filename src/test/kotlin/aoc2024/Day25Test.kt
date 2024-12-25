package aoc2024

import aoc2024.day25.Day25
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.aocClient
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day25Test {

    private val input = readInput("aoc2024/day25.txt")
    private val inputExample = readInput("aoc2024/day25_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day25.part1(inputExample)

        // then
        assertEquals(3, result)

        // submit answer
        val answer = Day25.part1(input)
        val submissionResult = aocClient.submit(2024, 25, 1, answer)
        println("Result of Day 25 - Part 1: $answer (Submission: $submissionResult)")
    }

}