package aoc2021

object Day06 {

    fun part1(input: List<Int>): Int {
        var fishAges: MutableList<Int> = input.toMutableList()
        repeat(80) {
            val numberOfFishToAdd = fishAges.count { it == 0 }
            fishAges = fishAges.map { if (it > 0) it - 1 else 6 }.toMutableList()
            repeat(numberOfFishToAdd) { fishAges.add(8) }
        }
        return fishAges.size
    }

    fun part2(input: List<Int>): Long {
        val fishAges: MutableList<Long> = MutableList(9) { 0 }
        input.forEach { fishAges[it]++ }
        repeat(256) {
            val numberOfFishToAdd = fishAges[0]
            for (i in 0 until 8) {
                fishAges[i] = fishAges[i + 1]
            }
            fishAges[6] += numberOfFishToAdd
            fishAges[8] = numberOfFishToAdd
        }
        return fishAges.sum()
    }

}
