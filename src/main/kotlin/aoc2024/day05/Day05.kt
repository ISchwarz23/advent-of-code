package aoc2024.day05

import java.util.Comparator

/**
 * My solution for day 5 of Advent of Code 2024.
 * The puzzle can be found at the <a href="https://adventofcode.com/2024/day/5">AoC page</a>.
 */
object Day05 {

    fun part1(input: List<String>): Int {
        val rules = input.takeWhile { it.isNotEmpty() }.map { it.split("|") }.map { Pair(it[0].toInt(), it[1].toInt()) }
        val pages = input.takeLastWhile { it.isNotEmpty() }.map { it.split(",").map { it.toInt() } }

        return pages.filter { checkIfPagesSatisfyRules(it, rules) }
            .sumOf { it[it.size / 2] }
    }

    fun part2(input: List<String>): Int {
        val rules = input.takeWhile { it.isNotEmpty() }.map { it.split("|") }.map { Pair(it[0].toInt(), it[1].toInt()) }
        val pages = input.takeLastWhile { it.isNotEmpty() }.map { it.split(",").map { it.toInt() } }
        return pages.filter { !checkIfPagesSatisfyRules(it, rules) }
            .map { it.sortedWith(RuleSetComparator(rules)) }
            .sumOf { it[it.size / 2] }
    }

}


private class RuleSetComparator(private val rules: List<Pair<Int, Int>>) : Comparator<Int> {

    override fun compare(o1: Int, o2: Int): Int {
        return if (rules.contains(Pair(o1, o2))) {
            -1
        } else if (rules.contains(Pair(o2, o1))) {
            1
        } else {
            0
        }
    }

}

private fun checkIfPagesSatisfyRules(pages: List<Int>, rules: List<Pair<Int, Int>>): Boolean {
    return rules.filter { pages.contains(it.first) && pages.contains(it.second) }
        .all { rule -> pages.indexOf(rule.first) < pages.indexOf(rule.second) }
}
