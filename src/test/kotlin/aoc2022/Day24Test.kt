package aoc2022

import aoc2022.day24.Blizzard
import aoc2022.day24.Day24
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.TestMethodOrder
import utils.*
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day24Test {

    private val inputBlizzards = readInput2dIndexed("aoc2022/day24.txt") { x, y, data -> toBlizzard(x, y, data) }
    private val inputBasinDimension = readBasinDimensions("aoc2022/day24.txt")
    private val inputExample = readInput2dIndexed("aoc2022/day24_example.txt") { x, y, data -> toBlizzard(x, y, data) }
    private val inputExampleBasinDimension = readBasinDimensions("aoc2022/day24_example.txt")

    @Test
    @Tag("slow")
    internal fun testPart1() {
        // when
        val result = Day24.part1(inputExampleBasinDimension, inputExample)

        // then
        assertEquals(18, result)

        // submit answer
        val answer = Day24.part1(inputBasinDimension, inputBlizzards)
        val submissionResult = aocClient.submit(2022, 24, 1, answer)
        println("Result of Day 24 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    @Tag("slow")
    internal fun testPart2() {
        // when
        val result = Day24.part2(inputExampleBasinDimension, inputExample)

        // then
        assertEquals(54, result)

        // submit answer
        val answer = Day24.part2(inputBasinDimension, inputBlizzards)
        val submissionResult = aocClient.submit(2022, 24, 2, answer)
        println("Result of Day 24 - Part 2: $answer (Submission: $submissionResult)")
    }

}

private fun toBlizzard(x: Int, y: Int, data: Char): Blizzard? {
    return when (data) {
        '>' -> Blizzard(Vector2(x, y), Heading.EAST)
        '<' -> Blizzard(Vector2(x, y), Heading.WEST)
        'v' -> Blizzard(Vector2(x, y), Heading.SOUTH)
        '^' -> Blizzard(Vector2(x, y), Heading.NORTH)
        else -> null
    }
}

private fun readBasinDimensions(name: String): Rect {
    val input = readInput(name)
    return Rect(1, input[0].length - 2, 1, input.size - 2)
}