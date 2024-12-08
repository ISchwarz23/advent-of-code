package aoc2022

import aoc2022.day22.FieldType
import aoc2022.day23.Day23
import aoc2022.day23.Elf
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.TestMethodOrder
import utils.Vector2
import utils.aocClient
import utils.readInput
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day23Test {

    private val input = readInputAsElfMap("aoc2022/day23.txt")
    private val inputExample = readInputAsElfMap("aoc2022/day23_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day23.part1(inputExample)

        // then
        assertEquals(110, result)

        // submit answer
        val answer = Day23.part1(input)
        val submissionResult = aocClient.submit(2022, 23, 1, answer)
        println("Result of Day 23 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    @Tag("slow")
    internal fun testPart2() {
        // when
        val result = Day23.part2(inputExample)

        // then
        assertEquals(20, result)

        // submit answer
        val answer = Day23.part2(input)
        val submissionResult = aocClient.submit(2022, 23, 2, answer)
        println("Result of Day 23 - Part 2: $answer (Submission: $submissionResult)")
    }

}

private fun readInputAsElfMap(name: String): List<Elf> {
    return readInput(name).flatMapIndexed { index, row -> toElfs(index + 1, row) }
}

private fun toElfs(rowIndex: Int, rowData: String): List<Elf> {
    return rowData.split("").mapIndexed { columnIndex: Int, data: String ->
        if (data == "#") Elf(Vector2(columnIndex, rowIndex))
        else null
    }.filterNotNull()
}