package aoc2022

object Day01 {

    fun part1(input: List<String>): Int {
        return getCalorieSums(input).maxOrNull() ?: 0
    }
    fun part2(input: List<String>): Int {
        val calorieSums = getCalorieSums(input)
        calorieSums.sortDescending()
        return calorieSums.subList(0, 3).sum()
    }

    private fun getCalorieSums(input: List<String>): MutableList<Int> {
        val sums = mutableListOf<Int>()
        for (calories in input) {
            if (calories.isEmpty()) {
                sums += 0;
            } else {
                var sum = sums.removeLastOrNull() ?: 0
                sum += calories.toInt()
                sums += sum;
            }
        }
        return sums
    }

}
