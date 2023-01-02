package aoc2021

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day14Test {

    private val testInput = readInputAsPolymerTemplateAndInsertionRules("aoc2021/day14_example.txt")
    private val input = readInputAsPolymerTemplateAndInsertionRules("aoc2021/day14.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day14.part1(testInput.first, testInput.second)

        // then
        assertEquals(1588, result)

        // get solution
        println("Result of Day 14 - Part 1: ${Day14.part1(input.first, input.second)}")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day14.part2(testInput.first, testInput.second)

        // then
        assertEquals(2188189693529, result)

        // get solution
        println("Result of Day 14 - Part 2: ${Day14.part2(input.first, input.second)}")
    }

    private fun readInputAsPolymerTemplateAndInsertionRules(name: String): Pair<String, List<InsertionRule>> {
        val inputLines = readInput(name)

        val template = inputLines[0]
        val pairInsertions = mutableListOf<InsertionRule>()
        for(i in 2 until inputLines.size) {
            val insertionParts = inputLines[i].split(" -> ")
            pairInsertions += InsertionRule(insertionParts[0], insertionParts[1])
        }
        return Pair(template, pairInsertions)
    }

}