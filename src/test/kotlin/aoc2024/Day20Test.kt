package aoc2024

import aoc2024.day20.Day20
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.TestMethodOrder
import utils.aocClient
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day20Test {

    private val input = readInput("aoc2024/day20.txt")
    private val inputExample = readInput("aoc2024/day20_example.txt")

    @Test
    @Tag("slow")
    internal fun testPart1() {
        // when
        val result = Day20.part1(inputExample, 10)

        // then
        assertEquals(10, result)

        // submit answer
        val answer = Day20.part1(input, 100)
        val submissionResult = aocClient.submit(2024, 20, 1, answer)
        println("Result of Day 20 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    @Tag("slow")
    internal fun testPart2() {
        // when
        val result = Day20.part2(inputExample, 50)

        // then
        assertEquals(285, result)

        // submit answer
        val answer = Day20.part2(input, 100)
        val submissionResult = aocClient.submit(2024, 20, 2, answer)
        println("Result of Day 20 - Part 2: $answer (Submission: $submissionResult)")
    }

}