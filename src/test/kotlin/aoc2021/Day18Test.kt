package aoc2021

import aoc2021.day18.Day18
import aoc2021.day18.SnailfishNumber
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.SplitCharBehavior
import utils.readInput
import utils.split
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day18Test {

    private val testInput = readInput("aoc2021/day18_example.txt").map { it.toSnailfishNumber() }
    private val input = readInput("aoc2021/day18.txt").map { it.toSnailfishNumber() }

    @Test
    internal fun testPart1() {
        // when
        val result = Day18.part1(testInput)

        // then
        assertEquals(4140, result)

        // get solution
        println("Result of Day 18 - Part 1: ${Day18.part1(input)}")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day18.part2(testInput)

        // then
        assertEquals(3993, result)

        // get solution
        println("Result of Day 18 - Part 2: ${Day18.part2(input)}")
    }


    private fun String.toSnailfishNumber(): SnailfishNumber {
        if (this.length == 1) return SnailfishNumber.Literal(this.toInt())

        var depth = 1
        var pointer = 1
        val chars = this.toCharArray()
        do {
            when (chars[pointer]) {
                '[' -> depth++
                ']' -> depth--
            }
            pointer++
        } while (depth != 1 || chars[pointer] != ',')

        val (left, right) = this.split(pointer, 1, 1, behavior = SplitCharBehavior.OMIT_CHAR_AT_SPLIT_INDEX)
        return SnailfishNumber.Pair(left.toSnailfishNumber(), right.toSnailfishNumber())
    }

}