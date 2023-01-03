package aoc2021

import aoc2021.day20.Day20
import aoc2021.day20.Filter
import aoc2021.day20.Image
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day20Test {

    private val testInput = readInputAsFilterAndImage("aoc2021/day20_example.txt")
    private val input = readInputAsFilterAndImage("aoc2021/day20.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day20.part1(testInput.first, testInput.second)

        // then
        assertEquals(35, result)

        // get solution
        println("Result of Day 20 - Part 1: ${Day20.part1(input.first, input.second)}")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day20.part2(testInput.first, testInput.second)

        // then
        assertEquals(3351, result)

        // get solution
        println("Result of Day 20 - Part 2: ${Day20.part2(input.first, input.second)}")
    }

    private fun readInputAsFilterAndImage(name: String): Pair<Filter, Image> {
        val lines = readInput(name)
        val filterData = lines[0].toCharArray().toList()

        val imageData = mutableListOf<List<Char>>()
        for(lineIndex in 2 until lines.size) {
            imageData += lines[lineIndex].toCharArray().toList()
        }
        return Pair(Filter(filterData), Image(imageData))
    }

}
