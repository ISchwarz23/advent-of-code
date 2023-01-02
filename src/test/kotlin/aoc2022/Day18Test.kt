package aoc2022

import aoc2022.day18.Day18
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.aocClient
import utils.readInputAsVector3
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day18Test {

    private val input = readInputAsVector3("aoc2022/day18.txt")
    private val inputExample = readInputAsVector3("aoc2022/day18_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day18.part1(inputExample)

        // then
        assertEquals(64, result)

        // submit answer
        val answer = Day18.part1(input)
        val submissionResult = aocClient.submit(2022, 18, 1, answer)
        println("Result of Day 18 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day18.part2(inputExample)

        // then
        assertEquals(58, result)

        // submit answer
        val answer = Day18.part2(input)
        val submissionResult = aocClient.submit(2022, 18, 2, answer)
        println("Result of Day 18 - Part 2: $answer (Submission: $submissionResult)")
    }

}