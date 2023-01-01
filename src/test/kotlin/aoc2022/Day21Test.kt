package aoc2022

import aoc2022.day21.Day21
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.aocClient
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day21Test {

    private val input = readInputAsMonkeyToOperationStr("aoc2022/day21.txt")
    private val inputExample = readInputAsMonkeyToOperationStr("aoc2022/day21_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day21.part1(inputExample)

        // then
        assertEquals(152, result)

        // submit answer
        val answer = Day21.part1(input)
        val submissionResult = aocClient.submit(2022, 21, 1, answer)
        println("Result of Day 21 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day21.part2(inputExample)

        // then
        assertEquals(301, result)

        // submit answer
        val answer = Day21.part2(input)
        val submissionResult = aocClient.submit(2022, 21, 2, answer)
        println("Result of Day 21 - Part 2: $answer (Submission: $submissionResult)")
    }

    private fun readInputAsMonkeyToOperationStr(name: String): Map<String, String> {
        return readInput(name).associate { it.substringBefore(":") to it.substringAfter(": ") }
    }

}