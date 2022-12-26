package aoc2022

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.AocClient
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day25Test {

    private val aocClient = AocClient()

    private val testInput = readInput("aoc2022/Day25_test.txt")
    private val input = readInput("aoc2022/Day25.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day25.part1(testInput)

        // then
        assertEquals("2=-1=0", result)

        // submit answer
        val answer = Day25.part1(input)
        val submissionResult = aocClient.submit(2022, 25, 1, answer)
        println("Result of Day 25 - Part 1: $answer (Submission: $submissionResult)")
    }

}