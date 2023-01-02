package aoc2021

import utils.calcPartialSum
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

object Day07 {

    fun part1(input: List<Int>): Int {
        val median = input.sorted()[input.size / 2]
        return input.sumOf { (it - median).absoluteValue }
    }

    fun part2(input: List<Int>): Int {
        val average = (input.sum() / input.size.toDouble()).roundToInt()

        // check around average
        var minFuelCosts = Integer.MAX_VALUE
        for (avg in average - 1..average + 1) {
            val fuelCosts = input.sumOf { (it - avg).absoluteValue.calcPartialSum() }
            if (fuelCosts < minFuelCosts) minFuelCosts = fuelCosts
        }
        return minFuelCosts
    }

}
