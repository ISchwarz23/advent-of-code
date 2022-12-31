package aoc2022

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.AocClient
import utils.readInput
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day19Test {

    private val aocClient = AocClient()

    private val testInput = readInput("aoc2022/Day19_test.txt")
    private val input = readInput("aoc2022/Day19.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day19.part1(testInput)

        // then
        assertEquals(33, result)

        // submit answer
        val answer = Day19.part1(input)
        val submissionResult = aocClient.submit(2022, 19, 1, answer)
        println("Result of Day 19 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    @Ignore
    internal fun testPart2() {
        // when
         val result = Day19.part2(testInput)

        // then
         assertEquals(3472, result)

        // submit answer
        val answer = Day19.part2(input)
        val submissionResult = aocClient.submit(2022, 19, 2, answer)
        println("Result of Day 19 - Part 2: $answer (Submission: $submissionResult)")
    }

}
