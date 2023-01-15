package aoc2022

import aoc2022.day05.Day05
import aoc2022.day05.Procedure
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.readInput
import utils.split
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day05Test {

    private val input = parseInputAsStacksAndProcedures("aoc2022/day05.txt")
    private val inputExample = parseInputAsStacksAndProcedures("aoc2022/day05_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day05.part1(inputExample.first, inputExample.second)

        // then
        assertEquals("CMZ", result)

        // get solution
        println("Result of Day 05 - Part 1: ${Day05.part1(input.first, input.second)}")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day05.part2(inputExample.first, inputExample.second)

        // then
        assertEquals("MCD", result)

        // get solution
        println("Result of Day 05 - Part 2: ${Day05.part2(input.first, input.second)}")
    }

}

private fun parseInputAsStacksAndProcedures(name: String): Pair<List<ArrayDeque<Char>>, List<Procedure>> {
    val (stackStrings, procedureStrings) = readInput(name).split { it.isEmpty() }
    val stacks = parseStacks(stackStrings)
    val procedures = parseProcedures(procedureStrings)
    return Pair(stacks, procedures)
}

private fun parseStacks(stackStrings: List<String>): List<ArrayDeque<Char>> {
    // turn stack upside down and remove row containing stack numbers
    val stackLevels = stackStrings.reversed().subList(1, stackStrings.size)

    // get number of stacks by counting empty spaces between stacks
    val numberOfStacks = stackLevels[0].count { it == ' ' } + 1
    val stacks = List(numberOfStacks) {ArrayDeque<Char>() }

    // parse stacks
    for (currentLevel in stackLevels) {
        var stackIndex = 0
        var stringIndex = 1

        while (currentLevel.length > stringIndex) {
            // get char at index 1 of remaining string
            val c = currentLevel[stringIndex]
            // if it is not empty, add it to the current stack
            if (c != ' ') stacks[stackIndex] += c
            // adapt string index for next stack
            stringIndex += 4
            // adapt stack index for next stack
            stackIndex++
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
        matches.groups[2]!!.value.toInt() - 1,
        matches.groups[3]!!.value.toInt() - 1
    )
}