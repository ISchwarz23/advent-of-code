package aoc2022

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.AocClient
import utils.Vector2
import utils.readOneLineInputAsString
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day17Test {

    private val aocClient = AocClient()

    private val testInput = readInputAsWind("aoc2022/Day17_test.txt")
    private val input = readInputAsWind("aoc2022/Day17.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day17.part1(testInput)

        // then
        assertEquals(3068, result)

        // submit answer
        val answer = Day17.part1(input)
        val submissionResult = aocClient.submit(2022, 17, 1, answer)
        println("Result of Day 17 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day17.part2(testInput)

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
