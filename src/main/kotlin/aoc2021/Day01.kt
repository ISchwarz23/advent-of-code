package aoc2021

object Day01 {

    fun part1(input: List<Int>): Int {
        return input.windowed(2).count { it[0] < it[1] }
    }

    fun part2(input: List<Int>): Int {
        return input.windowed(4).count { it[0] < it[3] }
    }
}
