package aoc2022

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.AocClient
import utils.readInput
import utils.readInputAsVector3D
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day18Test {

    private val aocClient = AocClient()

    private val testInput = readInputAsVector3D("aoc2022/Day18_test.txt")
    private val input = readInputAsVector3D("aoc2022/Day18.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day18.part1(testInput)

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
        val result = Day18.part2(testInput)

        // then
        assertEquals(58, result)

        // submit answer
        val answer = Day18.part2(input)
        val submissionResult = aocClient.submit(2022, 18, 2, answer)
        println("Result of Day 18 - Part 2: $answer (Submission: $submissionResult)")
    }

}