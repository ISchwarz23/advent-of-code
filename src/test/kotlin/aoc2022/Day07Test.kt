package aoc2022

import aoc2022.day07.Day07
import aoc2022.day07.Directory
import aoc2022.day07.File
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day07Test {

    private val testInput = readInput("aoc2022/Day07_test.txt")
    private val input = readInput("aoc2022/Day07.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day07.part1(testInput)

        // then
        assertEquals(95437, result)

        // get solution
        println("Result of Day 07 - Part 1: ${Day07.part1(input)}")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day07.part2(testInput)

        // then
        assertEquals(24933642, result)

        // get solution
        println("Result of Day 07 - Part 2: ${Day07.part2(input)}")
    }

}


private fun print(dir: Directory, indent: Int = 0) {
    repeat(indent) { print(" ") }
    println("- ${dir.name} (dir, size=${dir.size})")

    for (file in dir.files) {
        if (file is File) {
            repeat(indent) { print(" ") }
            println("  - ${file.name} (file, size=${file.size})")
        } else if (file is Directory) {
            print(file, indent + 2)
        }
    }
}