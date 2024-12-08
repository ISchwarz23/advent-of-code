package aoc2024

import aoc2024.day07.Day07
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.TestMethodOrder
import utils.aocClient
import utils.readInput
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day07Test {

    private val input = readInput("aoc2024/day07.txt")
    private val inputExample = readInput("aoc2024/day07_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day07.part1(inputExample)

        // then
        assertEquals(3749, result)

        // submit answer
        val answer = Day07.part1(input)
        val submissionResult = aocClient.submit(2024, 7, 1, answer)
        println("Result of Day 07 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    @Tag("slow")
    internal fun testPart2() {
        // when
        val result = Day07.part2(inputExample)

        // then
        assertEquals(11387, result)

        // submit answer
        val answer = Day07.part2(input)
        val submissionResult = aocClient.submit(2024, 7, 2, answer)
        println("Result of Day 07 - Part 2: $answer (Submission: $submissionResult)")
    }

}