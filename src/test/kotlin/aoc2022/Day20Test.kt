package aoc2022

import aoc2022.day20.Day20
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.aocClient
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day20Test {

    private val inputExample = readInput("aoc2022/day20_example.txt")
    private val input = readInput("aoc2022/day20.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day20.part1(inputExample)

        // then
        assertEquals(3, result)

        // submit answer
        val answer = Day20.part1(input)
        val submissionResult = aocClient.submit(2022, 20, 1, answer)
        println("Result of Day 20 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day20.part2(inputExample)

        // then
        assertEquals(1623178306, result)

        // submit answer
        val answer = Day20.part2(input)
        val submissionResult = aocClient.submit(2022, 20, 2, answer)
        println("Result of Day 20 - Part 2: $answer (Submission: $submissionResult)")
    }

}