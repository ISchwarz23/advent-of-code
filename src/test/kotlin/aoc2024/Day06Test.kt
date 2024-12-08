package aoc2024

import aoc2024.day06.Day06
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.TestMethodOrder
import utils.aocClient
import utils.readInput
import utils.readInputAsChars
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day06Test {

    private val input = readInputAsChars("aoc2024/day06.txt")
    private val inputExample = readInputAsChars("aoc2024/day06_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day06.part1(inputExample)

        // then
        assertEquals(41, result)

        // submit answer
        val answer = Day06.part1(input)
        val submissionResult = aocClient.submit(2024, 6, 1, answer)
        println("Result of Day 06 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    @Tag("slow")
    internal fun testPart2() {
        // when
        val result = Day06.part2(inputExample)

        // then
        assertEquals(6, result)

        // submit answer
        val answer = Day06.part2(input)
        val submissionResult = aocClient.submit(2024, 6, 2, answer)
        println("Result of Day 06 - Part 2: $answer (Submission: $submissionResult)")
    }

}