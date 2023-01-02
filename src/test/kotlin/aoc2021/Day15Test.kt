package aoc2021

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.readInputAs2dIntArray
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day15Test {

    private val testInput = readInputAsCave("aoc2021/day15_example.txt")
    private val input = readInputAsCave("aoc2021/day15.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day15.part1(testInput)

        // then
        assertEquals(40, result)

        // get solution
        println("Result of Day 15 - Part 1: ${Day15.part1(input)}")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day15.part2(testInput)

        // then
        assertEquals(315, result)

        // get solution
        println("Result of Day 15 - Part 2: ${Day15.part2(input)}")
    }

    private fun readInputAsCave(name: String): Cave {
        return Cave(readInputAs2dIntArray(name))
    }

}