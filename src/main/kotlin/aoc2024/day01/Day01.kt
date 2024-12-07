package aoc2024.day01

import kotlin.math.abs

/**
 * My solution for day 1 of Advent of Code 2024.
 * The puzzle can be found at the <a href="https://adventofcode.com/2024/day/1">AoC page</a>.
 */
object Day01 {

    fun part1(input: List<String>): Int {
        val firstList = mutableListOf<Int>()
        val secondsList = mutableListOf<Int>()
        input.map { it.split("\\s+".toRegex()) }.forEach {
            firstList += it[0].toInt()
            secondsList += it[1].toInt()
        }

        firstList.sort()
        secondsList.sort()

        var delta = 0
        for (index in firstList.indices) {
            delta += abs(firstList[index] - secondsList[index])
        }
        return delta
    }

    fun part2(input: List<String>): Int {
        val firstList = mutableListOf<Int>()
        val secondsList = mutableListOf<Int>()
        input.map { it.split("\\s+".toRegex()) }.forEach {
            firstList += it[0].toInt()
            secondsList += it[1].toInt()
        }

        return firstList.map { numberInFirstList -> Pair(numberInFirstList, secondsList.count { numberInSecondList -> numberInFirstList == numberInSecondList }) }
            .sumOf { it.first * it.second }
    }

}
