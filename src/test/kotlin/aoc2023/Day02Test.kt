package aoc2023

import aoc2023.day02.Day02
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.aocClient
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day02Test {

    private val input = readInput("aoc2023/day02.txt")
    private val inputExample = readInput("aoc2023/day02_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day02.part1(inputExample)

        // then
        assertEquals(8, result)

        // submit answer
        val answer = Day02.part1(input)
        val submissionResult = aocClient.submit(2023, 2, 1, answer)
        println("Result of Day 02 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day02.part2(inputExample)

        // then
        assertEquals(2286, result)

        // submit answer
        val answer = Day02.part2(input)
        val submissionResult = aocClient.submit(2023, 2, 2, answer)
        println("Result of Day 02 - Part 2: $answer (Submission: $submissionResult)")
    }

}