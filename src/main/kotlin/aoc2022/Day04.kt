package aoc2022

object Day04 {

    fun part1(input: List<String>): Int {
        return input.map { toRangePair(it) }
            .count { it.first.contains(it.second) || it.second.contains(it.first) }
    }

    fun part2(input: List<String>): Int {
        return input.map { toRangePair(it) }
            .count { it.first.contains(it.second.first) || it.first.contains(it.second.last) }
    }
}

private fun toRangePair(rangePairString: String): Pair<IntRange, IntRange> {
    val ranges = rangePairString.split(",").map { toRange(it) }.sortedBy { it.first }
    return Pair(ranges[0], ranges[1])
}

private fun toRange(rangeString: String): IntRange {
    val rangePair = rangeString.split("-")
    return rangePair[0].toInt()..rangePair[1].toInt()
}

fun IntRange.contains(other: IntRange): Boolean {
    return this.contains(other.first) && this.contains(other.last)
}