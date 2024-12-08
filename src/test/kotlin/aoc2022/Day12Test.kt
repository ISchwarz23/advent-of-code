package aoc2022

import aoc2022.day12.Day12
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.TestMethodOrder
import utils.aocClient
import utils.readInput
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day12Test {

    private val input = readInput("aoc2022/day12.txt")
    private val inputExample = readInput("aoc2022/day12_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day12.part1(inputExample)

        // then
        assertEquals(31, result)

        // submit answer
        val answer = Day12.part1(input)
        val submissionResult = aocClient.submit(2022, 12, 1, answer)
        println("Result of Day 12 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    @Tag("slow")
    internal fun testPart2() {
        // when
        val result = Day12.part2(inputExample)

        // then
        assertEquals(29, result)

        // submit answer
        val answer = Day12.part2(input)
        val submissionResult = aocClient.submit(2022, 12, 2, answer)
        println("Result of Day 12 - Part 2: $answer (Submission: $submissionResult)")
    }

}