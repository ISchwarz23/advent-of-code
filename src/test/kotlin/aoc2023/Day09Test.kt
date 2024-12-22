package aoc2023

import aoc2023.day09.Day09
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.aocClient
import utils.readInput
import utils.readInputAs2dIntArray
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day09Test {

    private val input = readInputAs2dIntArray("aoc2023/day09.txt", " ")
    private val inputExample = readInputAs2dIntArray("aoc2023/day09_example.txt", " ")

    @Test
    internal fun testPart1() {
        // when
        val result = Day09.part1(inputExample)

        // then
        assertEquals(114, result)

        // submit answer
        val answer = Day09.part1(input)
        val submissionResult = aocClient.submit(2023, 9, 1, answer)
        println("Result of Day 09 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day09.part2(inputExample)

        // then
        assertEquals(2, result)

        // submit answer
        val answer = Day09.part2(input)
        val submissionResult = aocClient.submit(2023, 9, 2, answer)
        println("Result of Day 09 - Part 2: $answer (Submission: $submissionResult)")
    }

}