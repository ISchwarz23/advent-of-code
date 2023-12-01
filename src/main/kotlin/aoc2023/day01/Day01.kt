package aoc2023.day01

private val DIGIT_MAPPINGS = mapOf(
    "one" to "1",
    "two" to "2",
    "three" to "3",
    "four" to "4",
    "five" to "5",
    "six" to "6",
    "seven" to "7",
    "eight" to "8",
    "nine" to "9"
)

/**
 * My solution for day 01 of Advent of Code 2023.
 * The puzzle can be found at the <a href="https://adventofcode.com/2023/day/01">AoC page</a>.
 */
object Day01 {

    fun part1(lines: List<String>): Int {
        return lines.map { it.removeNonDigitChars() }
            .map { "${it.first()}${it.last()}" }
            .sumOf { it.toInt() }
    }

    fun part2(lines: List<String>): Int {
        return lines.asSequence()
            // .map { replaceFirstLastSpelledDigitByCharacter(it) }
            // .map { replaceLastSpelledOutDigitsByChar(it) }
            .map { replaceSpelledOutDigitsByChar(it) }
            .map { it.removeNonDigitChars() }
            .map { "${it.first()}${it.last()}" }
            .sumOf { it.toInt() }
    }

}

fun replaceSpelledOutDigitsByChar(line: String): String {

    var adaptedLine = line;
    do {
        val mappingToApply = DIGIT_MAPPINGS.entries.map { it to adaptedLine.indexOf(it.key) }
            .filter { it.second >= 0 }
            .minByOrNull { it.second }?.first

         if (mappingToApply != null) {
             adaptedLine = adaptedLine.replaceFirst(mappingToApply.key, "${mappingToApply.value}${mappingToApply.key.substring(1)}")
        }
    } while (mappingToApply != null)
    return adaptedLine
}

fun String.removeNonDigitChars(): String {
    return this.toCharArray().filter { it in '0'..'9' }.joinToString("")
}