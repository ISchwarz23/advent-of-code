package aoc2022

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class DayXXTest {

    private val testInput = readInput("DayXX_test")
    private val input = readInput("DayXX")

    @Test
    internal fun testPart1() {
        // when
        val result = DayXX.part1(testInput)

        // then
        assertEquals(0, result)

        // get solution
        println("Result of Day XX - Part 1: ${DayXX.part1(input)}")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = DayXX.part2(testInput)

        // then
        assertEquals(0, result)

        // get solution
        println("Result of Day XX - Part 2: ${DayXX.part2(input)}")
    }

}