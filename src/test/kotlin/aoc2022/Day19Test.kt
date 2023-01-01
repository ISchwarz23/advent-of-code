package aoc2022

import aoc2022.day19.Blueprint
import aoc2022.day19.Day19
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.aocClient
import utils.readInput
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day19Test {

    private val inputExample = readInputAsBlueprint("aoc2022/day19_example.txt")
    private val input = readInputAsBlueprint("aoc2022/day19.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day19.part1(inputExample)

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
        val result = Day19.part2(inputExample)

        // then
        assertEquals(3472, result)

        // submit answer
        val answer = Day19.part2(input)
        val submissionResult = aocClient.submit(2022, 19, 2, answer)
        println("Result of Day 19 - Part 2: $answer (Submission: $submissionResult)")
    }

}


private fun readInputAsBlueprint(name: String): List<Blueprint> {
    return readInput(name).map { Blueprint.of(it) }
}
