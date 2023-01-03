package aoc2021

import aoc2021.day12.Day12
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day12Test {

    private val testInput = readInputAsCaveConnections("aoc2021/day12_example.txt")
    private val input = readInputAsCaveConnections("aoc2021/day12.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day12.part1(testInput)

        // then
        assertEquals(10, result)

        // get solution
        println("Result of Day 12 - Part 1: ${Day12.part1(input)}")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day12.part2(testInput)

        // then
        assertEquals(36, result)

        // get solution
        println("Result of Day 12 - Part 2: ${Day12.part2(input)}")
    }

    private fun readInputAsCaveConnections(name: String): Map<String, List<String>> {
        return readInput(name).map { it.split("-") }
            .map { listOf(CaveConnection(it[0], it[1]), CaveConnection(it[1], it[0])) }
            .flatten()
            .groupBy(CaveConnection::from, CaveConnection::to)
    }

    data class CaveConnection(val from: String, val to: String )

}