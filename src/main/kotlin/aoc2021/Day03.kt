package aoc2021

import utils.pow

object Day03 {

    fun part1(input: List<String>): Int {
        if (input.isEmpty()) return 0

        val counters = IntArray(input[0].length)
        input.forEach {
            it.forEachIndexed { index, c ->
                if (c == '1') counters[index] += 1
            }
        }

        val majority = input.size / 2
        val maxExponent = input[0].length - 1
        var gammaRate = 0
        var epsilonRate = 0
        counters.forEachIndexed { index, i ->
            val value = 2 pow (maxExponent - index)
            if (i > majority) gammaRate += value else epsilonRate += value
        }
        return gammaRate * epsilonRate
    }

    fun part2(input: List<String>): Int {

        fun count(i: List<String>, c: Char, index: Int): Int {
            return i.count { it.toCharArray()[index] == c }
        }

        fun filterUntilOnlyOneRemaining(filter: (i: List<String>, index: Int) -> List<String>): Int {
            var index = 0
            var inputCp: List<String> = input.toMutableList()
            while (inputCp.size > 1) {
                inputCp = filter(inputCp, index)
                index++
            }
            return Integer.parseInt(inputCp[0], 2)
        }

        fun calculateOxygenGeneratorRating(): Int {
            return filterUntilOnlyOneRemaining { remainingInput, index ->
                val countOf1 = count(remainingInput, '1', index)
                if (countOf1 >= remainingInput.size - countOf1) {
                    remainingInput.filter { it.toCharArray()[index] == '1' }
                } else {
                    remainingInput.filter { it.toCharArray()[index] == '0' }
                }
            }
        }

        fun calculateCO2ScrubberRating(): Int {
            return filterUntilOnlyOneRemaining { remainingInput, index ->
                val countOf0 = count(remainingInput, '0', index)
                if (countOf0 <= remainingInput.size - countOf0) {
                    remainingInput.filter { it.toCharArray()[index] == '0' }
                } else {
                    remainingInput.filter { it.toCharArray()[index] == '1' }
                }
            }
        }

        return calculateOxygenGeneratorRating() * calculateCO2ScrubberRating()
    }

}