package aoc2022.day01

import utils.split

object Day01 {

    fun part1(input: List<String>): Int {
        return input.split { it.isEmpty() }.maxOf { backpackItems -> backpackItems.sumOf { it.toInt() } }
    }

    fun part2(input: List<String>): Int {
        return input.split { it.isEmpty() }
            .map { backpackItems -> backpackItems.sumOf { it.toInt() } }
            .sortedDescending()
            .take(3)
            .sum()
    }

}
