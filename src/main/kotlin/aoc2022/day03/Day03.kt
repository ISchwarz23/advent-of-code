package aoc2022.day03

import utils.split

object Day03 {

    fun part1(input: List<String>): Int {
        return input.map { it.split(it.length / 2) }
            .map { findCommonChar(it.first, it.second) }
            .sumOf { toPriority(it) }
    }

    fun part2(input: List<String>): Int {
        return input.chunked(3)
            .map { findCommonChar(it[0], it[1], it[2]) }
            .sumOf { toPriority(it) }
    }
}

private fun findCommonChar(first: String, vararg second: String): Char? {
    return first.toCharArray().find { c -> second.all { it.contains(c) } }
}

private fun toPriority(c: Char?): Int {
    if (c == null) return 0

    return if (c.code >= 'a'.code) {
        // 'a' through 'z' have priorities 1 through 26
        c.code - 'a'.code + 1
    } else {
        // 'A' through 'Z' have priorities 27 through 52
        c.code - 'A'.code + 27
    }
}