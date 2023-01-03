package aoc2021

import aoc2021.day05.Coords
import aoc2021.day05.Day05
import aoc2021.day05.Line
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day05Test {

    private val testInput = readInputAsLines("aoc2021/day05_example.txt")
    private val input = readInputAsLines("aoc2021/day05.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day05.part1(testInput)

        // then
        assertEquals(5, result)

        // get solution
        println("Result of Day 05 - Part 1: ${Day05.part1(input)}")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day05.part2(testInput)

        // then
        assertEquals(12, result)

        // get solution
        println("Result of Day 05 - Part 2: ${Day05.part2(input)}")
    }

    private fun readInputAsLines(name: String): List<Line> {
        return readInput(name).map { it.split(" -> ") }
            .map {
                val startCoords = it[0].split(",").map { i -> i.toInt() }
                val endCoords = it[1].split(",").map { i -> i.toInt() }
                Line(Coords(startCoords[0], startCoords[1]), Coords(endCoords[0], endCoords[1]))
            }
    }
}