package aoc2022

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day05Test {

    private val testInput = parseInput(readInput("Day05_test"))
    private val input = parseInput(readInput("Day05"))

    @Test
    internal fun testPart1() {
        // when
        val result = Day05.part1(testInput.first, testInput.second)

        // then
        assertEquals("CMZ", result)

        // get solution
        println("Result of Day 05 - Part 1: ${Day05.part1(input.first, input.second)}")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day05.part2(testInput.first, testInput.second)

        // then
        assertEquals("MCD", result)

        // get solution
        println("Result of Day 05 - Part 2: ${Day05.part2(input.first, input.second)}")
    }

}

private fun parseInput(input: List<String>): Pair<List<ArrayDeque<Char>>, List<Procedure>> {
    val stackStrings = input.takeWhile { it.isNotEmpty() }
    val stacks = parseStacks(stackStrings)

    val procedureStrings = input.subList(stackStrings.size + 1, input.size)
    val procedures = parseProcedures(procedureStrings)

    return Pair(stacks, procedures)
}

private fun parseStacks(stackStrings: List<String>): List<ArrayDeque<Char>> {
    var lines = stackStrings.reversed()

    // get number of stacks
    val stacks = mutableListOf<ArrayDeque<Char>>()
    repeat(lines[0].count { it != ' ' }) {
        stacks.add(ArrayDeque())
    }
    lines = lines.subList(1, stackStrings.size)

    // parse stacks
    for (stackString in lines) {
        var index = 0
        var str = stackString;

        while (str.isNotEmpty()) {
            val c = str[1]
            if (c != ' ') {
                stacks[index].add(c)
            }
            str = if (str.length > 4) {
                str.substring(4)
            } else {
                ""
            }
            index++
        }
    }
    return stacks
}

private fun parseProcedures(procedureStrings: List<String>): List<Procedure> {
    return procedureStrings.map { parseProcedure(it) }
}

private fun parseProcedure(procedureString: String): Procedure {
    val regex = "move (\\d+) from (\\d+) to (\\d+)".toRegex()
    val matches = regex.matchEntire(procedureString)!!
    return Procedure(
        matches.groups[1]!!.value.toInt(),
        matches.groups[2]!!.value.toInt(),
        matches.groups[3]!!.value.toInt()
    )
}