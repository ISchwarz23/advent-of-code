package aoc2022.day18

import utils.Vector3

object Day18 {

    fun part1(input: List<Vector3>): Int {
        return LavaDroplet(input).getNumberOfExteriorSides()
    }

    fun part2(input: List<Vector3>): Int {
        return LavaDroplet(input).getNumberOfExteriorSidesWithoutAirPockets()
    }
}