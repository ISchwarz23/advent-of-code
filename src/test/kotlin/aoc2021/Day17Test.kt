package aoc2021

import aoc2021.day17.Day17
import aoc2021.day17.TargetArea
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.readOneLineInputAsString
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day17Test {

    private val testInput = readInputAsTargetArea("aoc2021/day17_example.txt")
    private val input = readInputAsTargetArea("aoc2021/day17.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day17.part1(testInput)

        // then
        assertEquals(45, result)

        // get solution
        println("Result of Day 17 - Part 1: ${Day17.part1(input)}")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day17.part2(testInput)

        // then
        assertEquals(112, result)

        // get solution
        println("Result of Day 17 - Part 2: ${Day17.part2(input)}")
    }

    private fun readInputAsTargetArea(name: String): TargetArea {
        val input = readOneLineInputAsString(name)
        val ranges = input.substring(13).split(", ")
            .map { it.substring(2) }
            .flatMap { it.split("..") }
            .map { it.toInt() }
        return TargetArea(ranges[0], ranges[1], ranges[2], ranges[3])
    }

}