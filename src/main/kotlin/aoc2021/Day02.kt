package aoc2021

object Day02 {

    fun part1(input: List<String>): Int {
        var position = 0
        var depth = 0

        input.map { it.split(" ") }
            .map { it[0] to it[1].toInt() }
            .forEach {
                when (it.first) {
                    "forward" -> position += it.second
                    "down" -> depth += it.second
                    "up" -> depth -= it.second
                }
            }
        return position * depth
    }

    fun part2(input: List<String>): Int {
        var position = 0
        var depth = 0
        var aim = 0

        input.map { it.split(" ") }
            .map { it[0] to it[1].toInt() }
            .forEach {
                when (it.first) {
                    "down" -> aim += it.second
                    "up" -> aim -= it.second
                    "forward" -> {
                        position += it.second
                        depth += it.second * aim
                    }
                }
            }
        return position * depth
    }
}
