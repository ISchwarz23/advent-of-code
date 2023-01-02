package aoc2021

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day09Test {

    private val testInput = readInputAsHeightMap("aoc2021/day09_example.txt")
    private val input = readInputAsHeightMap("aoc2021/day09.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day09.part1(testInput)

        // then
        assertEquals(15, result)

        // get solution
        println("Result of Day 09 - Part 1: ${Day09.part1(input)}")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day09.part2(testInput)

        // then
        assertEquals(1134, result)

        // get solution
        println("Result of Day 09 - Part 2: ${Day09.part2(input)}")
    }

    private fun readInputAsHeightMap(name: String): HeightMap {
        val data = readInput(name).map { it.toCharArray().map { c -> c.digitToInt() } }
        return HeightMap(data)
    }

}