package aoc2020

import aoc2020.day01.Day01
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.aocClient
import utils.readInput
import utils.readInputAsInts
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day01Test {

    private val input = readInputAsInts("aoc2020/day01.txt")
    private val inputExample = readInputAsInts("aoc2020/day01_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day01.part1(inputExample)

        // then
        assertEquals(514579, result)

        // submit answer
        val answer = Day01.part1(input)
        val submissionResult = aocClient.submit(2020, 1, 1, answer)
        println("Result of Day 01 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day01.part2(inputExample)

        // then
        assertEquals(241861950, result)

        // submit answer
        val answer = Day01.part2(input)
        val submissionResult = aocClient.submit(2020, 1, 2, answer)
        println("Result of Day 01 - Part 2: $answer (Submission: $submissionResult)")
    }

}