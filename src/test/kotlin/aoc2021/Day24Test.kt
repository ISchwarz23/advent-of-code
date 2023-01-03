package aoc2021

import aoc2021.day24.Day24
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.readInput
import kotlin.test.Test

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day24Test {

    private val input = readInput("aoc2021/day24.txt")

    @Test
    internal fun testPart1() {
        // get solution
        println("Result of Day 24 - Part 1: ${Day24.part1(input)}")
    }

    @Test
    internal fun testPart2() {
        // get solution
        println("Result of Day 24 - Part 2: ${Day24.part2(input)}")
    }

}