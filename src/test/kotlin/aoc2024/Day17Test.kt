package aoc2024

import aoc2024.day17.Day17
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.aocClient
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day17Test {

    private val input = readInput("aoc2024/day17.txt")
    private val inputExample = readInput("aoc2024/day17_example.txt")
    private val inputExampleSmall = readInput("aoc2024/day17_example_small.txt")

    @Test
    fun manualTest() {
        // when
        val result = Day17.part1(inputExampleSmall)

        // then
        assertEquals("0,1,2", result)
    }

    @Test
    internal fun testPart1() {
        // when
        val result = Day17.part1(inputExample)

        // then
        assertEquals("4,6,3,5,6,3,5,2,1,0", result)

        // submit answer
        val answer = Day17.part1(input)
        val submissionResult = aocClient.submit(2024, 17, 1, answer)
        println("Result of Day 17 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day17.part2(inputExample)

        // then
        assertEquals(-1, result)

        // submit answer
        val answer = Day17.part2(input)
        val submissionResult = aocClient.submit(2024, 17, 2, answer)
        println("Result of Day 17 - Part 2: $answer (Submission: $submissionResult)")
    }

}