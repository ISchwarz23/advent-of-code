package aoc2022.day07


class InputReader(private val input: List<String>, skipFirstLine: Boolean = true) {
    private var lineIndex = -1

    init {
        if (skipFirstLine) getNextLine()
    }

    fun getNextLine(): String? {
        return if (moreLinesAvailable()) {
            input[++lineIndex]
        } else {
            null
        }
    }

    private fun moreLinesAvailable(): Boolean {
        return input.size - 1 > lineIndex
    }

}