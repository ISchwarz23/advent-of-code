package aoc2022

object Day06 {

    fun part1(input: String): Int {
        return getIndexOfStartSequence(input, 4)
    }

    fun part2(input: String): Int {
        return getIndexOfStartSequence(input, 14)
    }
}

private fun getIndexOfStartSequence(message: String, startSequenceLength: Int): Int {
    return message.windowed(startSequenceLength, 1).takeWhile { !it.allCharsUnique() }.count() + startSequenceLength
}

private fun String.allCharsUnique(): Boolean = all(hashSetOf<Char>()::add)