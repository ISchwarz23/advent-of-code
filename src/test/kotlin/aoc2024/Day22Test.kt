package aoc2024

import aoc2024.day22.Day22
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.TestMethodOrder
import utils.aocClient
import utils.readInputAsInts
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day22Test {

    private val input = readInputAsInts("aoc2024/day22.txt")
    private val inputExamplePart1 = readInputAsInts("aoc2024/day22_example_part1.txt")
    private val inputExamplePart2 = readInputAsInts("aoc2024/day22_example_part2.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day22.part1(inputExamplePart1)

        // then
        assertEquals(37327623, result)

        // submit answer
        val answer = Day22.part1(input)
        val submissionResult = aocClient.submit(2024, 22, 1, answer)
        println("Result of Day 22 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    @Tag("slow")
    internal fun testPart2() {
        // when
        val result = Day22.part2(inputExamplePart2)

        // then
        assertEquals(23, result)

        // submit answer
        val answer = Day22.part2(input)
        val submissionResult = aocClient.submit(2024, 22, 2, answer)
        println("Result of Day 22 - Part 2: $answer (Submission: $submissionResult)")
    }

}