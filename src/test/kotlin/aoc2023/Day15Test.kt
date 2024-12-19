package aoc2023

import aoc2023.day15.Day15
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.aocClient
import utils.readInput
import utils.readOneLineInputAsString
import utils.split
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day15Test {

    private val input = readOneLineInputAsString("aoc2023/day15.txt").split(",")
    private val inputExample = readOneLineInputAsString("aoc2023/day15_example.txt").split(",")

    @Test
    internal fun testPart1() {
        // when
        val result = Day15.part1(inputExample)

        // then
        assertEquals(1320, result)

        // submit answer
        val answer = Day15.part1(input)
        val submissionResult = aocClient.submit(2023, 15, 1, answer)
        println("Result of Day 15 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day15.part2(inputExample)

        // then
        assertEquals(145, result)

        // submit answer
        val answer = Day15.part2(input)
        val submissionResult = aocClient.submit(2023, 15, 2, answer)
        println("Result of Day 15 - Part 2: $answer (Submission: $submissionResult)")
    }

}