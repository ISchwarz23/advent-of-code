package aoc2020

import aoc2020.day03.Day03
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.Vector2
import utils.aocClient
import utils.readInput
import utils.readInput2dIndexed
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day03Test {

    private val input = readInput2dIndexed("aoc2020/day03.txt") { x, y, data -> if(data == '#') Vector2(x, y) else null }
    private val inputExample = readInput2dIndexed("aoc2020/day03_example.txt") { x, y, data -> if(data == '#') Vector2(x, y) else null }

    @Test
    internal fun testPart1() {
        // when
        val result = Day03.part1(inputExample)

        // then
        assertEquals(7, result)

        // submit answer
        val answer = Day03.part1(input)
        val submissionResult = aocClient.submit(2020, 3, 1, answer)
        println("Result of Day 03 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day03.part2(inputExample)

        // then
        assertEquals(336, result)

        // submit answer
        val answer = Day03.part2(input)
        val submissionResult = aocClient.submit(2020, 3, 2, answer)
        println("Result of Day 03 - Part 2: $answer (Submission: $submissionResult)")
    }

}