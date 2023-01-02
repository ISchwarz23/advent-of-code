package aoc2022

import aoc2022.day17.Day17
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.Vector2
import utils.aocClient
import utils.readOneLineInputAsString
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day17Test {

    private val input = readInputAsWind("aoc2022/day17.txt")
    private val inputExample = readInputAsWind("aoc2022/day17_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day17.part1(inputExample)

        // then
        assertEquals(3068, result)

        // submit answer
        val answer = Day17.part1(input)
        val submissionResult = aocClient.submit(2022, 17, 1, answer)
        println("Result of Day 17 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    @Ignore("Takes too long")
    internal fun testPart2() {
        // when
        val result = Day17.part2(inputExample)

        // then
        assertEquals(1514285714288, result)

        // submit answer
        val answer = Day17.part2(input)
        val submissionResult = aocClient.submit(2022, 17, 2, answer)
        println("Result of Day 17 - Part 2: $answer (Submission: $submissionResult)")
    }

}

private fun readInputAsWind(name: String): List<Vector2> {
    return readOneLineInputAsString(name).chunked(1).map { if (it == "<") Vector2(-1, 0) else Vector2(1, 0) }
}
