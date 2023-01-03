package aoc2021

import aoc2021.day13.Day13
import aoc2021.day13.Dot
import aoc2021.day13.FoldInstruction
import aoc2021.day13.FoldOrientation
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day13Test {

    private val testInput = readInputAsDotsAndFoldInstructions("aoc2021/day13_example.txt")
    private val input = readInputAsDotsAndFoldInstructions("aoc2021/day13.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day13.part1(testInput.first, testInput.second)

        // then
        assertEquals(17, result)

        // get solution
        println("Result of Day 13 - Part 1: ${Day13.part1(testInput.first, testInput.second)}")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day13.part2(testInput.first, testInput.second)

        // then
        assertEquals(16, result)

        // get solution
        println("Result of Day 13 - Part 2: ${Day13.part2(input.first, input.second)}")
    }

    private fun readInputAsDotsAndFoldInstructions(name: String): Pair<List<Dot>, List<FoldInstruction>> {
        val input = readInput(name)
        val indexOfEmptyLine = input.indexOf("")
        val dots = input.subList(0, indexOfEmptyLine)
            .map { it.split(",") }
            .map { it.map { coord -> coord.toInt() } }
            .map { Dot(it[0], it[1]) }
        val foldInstructions = input.subList(indexOfEmptyLine + 1, input.size)
            .map { it.substring(11) }
            .map { it.split("=") }
            .map {
                val foldLocation = it[1].toInt()
                when (it[0]) {
                    "x" -> FoldInstruction(FoldOrientation.HORIZONTAL, foldLocation)
                    "y" -> FoldInstruction(FoldOrientation.VERTICAL, foldLocation)
                    else -> throw IllegalArgumentException("Unknown fold instruction")
                }
            }
        return Pair(dots, foldInstructions)
    }

}