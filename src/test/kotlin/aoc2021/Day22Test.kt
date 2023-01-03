package aoc2021

import aoc2021.day22.Day22
import aoc2021.day22.RebootAction
import aoc2021.day22.RebootStep
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.readInput
import java.lang.RuntimeException
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day22Test {

    private val testInput = readInputAsRebootSteps("aoc2021/day22_example.txt")
    private val input = readInputAsRebootSteps("aoc2021/day22.txt")

    internal fun customTest() {
        // given
        val rebootSteps = listOf(
            RebootStep(RebootAction.TURN_ON, 1..4, 1..4, 0..0),
            RebootStep(RebootAction.TURN_ON, 5..7, 1..4, 0..0),
            RebootStep(RebootAction.TURN_ON, 3..6, 3..5, 0..0)
        )

        // when
        val result = Day22.part1(rebootSteps)

        // then
        assertEquals(32, result)
    }

    @Test
    internal fun testPart1() {
        // when
        val result = Day22.part1(testInput)

        // then
        assertEquals(474140, result)

        // get solution
        println("Result of Day 22 - Part 1: ${Day22.part1(input)}")
    }

    @Test
    // @Ignore("too slow for real input")
    internal fun testPart2() {
        // when
        val result = Day22.part2(testInput)

        // then
        assertEquals(2758514936282235, result)

        // get solution
        println("Result of Day 22 - Part 2: ${Day22.part2(input)}")
    }

    private fun readInputAsRebootSteps(name: String): List<RebootStep> {
        return readInput(name).map { it.split(" ") }
            .map {
                val action = it[0].toRebootAction()
                val ranges = it[1].split(",")
                    .map { it.substring(2) }
                    .map { it.split("..") }
                    .map { IntRange(it[0].toInt(), it[1].toInt()) }
                RebootStep(action, ranges[0], ranges[1], ranges[2])
            }
    }

    private fun String.toRebootAction(): RebootAction {
        return when (this) {
            "on" -> RebootAction.TURN_ON
            "off" -> RebootAction.TURN_OFF
            else -> throw RuntimeException("Unknown reboot action: $this")
        }
    }
}
