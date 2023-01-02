package aoc2021

object Day08 {

    fun part1(input: List<SignalInput>): Int {
        return input.sumOf {
            it.outputPatterns.count { pattern ->
                pattern.length == 2 || pattern.length == 3 || pattern.length == 4 || pattern.length == 7
            }
        }
    }

    fun part2(input: List<SignalInput>): Int {
        return input.sumOf {
            val digitIdentifier = DigitIdentifier(it.signalPatterns)

            it.outputPatterns
                .map { pattern -> digitIdentifier.resolve(pattern) }
                .joinToString("")
                .toInt()
        }
    }

}

class DigitIdentifier(trainingData: List<Pattern>) {

    private val digitResolution = mutableMapOf<Pattern, Char>()

    init {
        // unique patterns
        val pattern1 = trainingData.findOrThrow { it.length == 2 }
        val pattern7 = trainingData.findOrThrow { it.length == 3 }
        val pattern4 = trainingData.findOrThrow { it.length == 4 }
        val pattern8 = trainingData.findOrThrow { it.length == 7 }

        // other patterns
        val pattern3 = trainingData.filter { it.length == 5 }.findOrThrow { it.contains(pattern1) }
        val pattern6 = trainingData.filter { it.length == 6 }.findOrThrow { it.contains(pattern1).not() }
        val pattern5 = trainingData.filter { it.length == 5 }.findOrThrow { it.contains(pattern6.inverse()).not() }
        val pattern9 = pattern5 + pattern1
        val pattern0 = pattern7 + pattern4.inverse() + pattern3.inverse()
        val pattern2 = pattern4.inverse() + pattern0.inverse() + pattern6.inverse()

        digitResolution[pattern0] = '0'
        digitResolution[pattern1] = '1'
        digitResolution[pattern2] = '2'
        digitResolution[pattern3] = '3'
        digitResolution[pattern4] = '4'
        digitResolution[pattern5] = '5'
        digitResolution[pattern6] = '6'
        digitResolution[pattern7] = '7'
        digitResolution[pattern8] = '8'
        digitResolution[pattern9] = '9'
    }

    fun resolve(pattern: Pattern): Char {
        return digitResolution[pattern] ?: throw IllegalArgumentException("Unknown pattern")
    }

    private fun <T> List<T>.findOrThrow(
        ex: RuntimeException = RuntimeException("Can't find item using given predicate."),
        predicate: (T) -> Boolean
    ): T {
        return this.find(predicate) ?: throw ex
    }

}

class Pattern(value: String) {

    private val pattern: String = value.sorted()
    val length: Int = pattern.length

    fun contains(other: Pattern): Boolean {
        return other.pattern.toCharArray().find { this.pattern.contains(it).not() } == null
    }

    fun contains(char: Char): Boolean {
        return this.pattern.contains(char)
    }

    operator fun get(index: Int): Char {
        return pattern[index]
    }

    operator fun minus(other: Pattern): Pattern {
        var result = pattern
        other.pattern.toCharArray().forEach { result = result.replace("$it", "") }
        return Pattern(result)
    }

    operator fun plus(other: Pattern): Pattern {
        var result = pattern
        other.pattern.toCharArray().forEach { if (contains(it).not()) result += it }
        return Pattern(result)
    }

    operator fun minus(char: Char): Pattern {
        return Pattern(pattern.replace("$char", ""))
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Pattern)
            other.pattern == this.pattern
        else
            false
    }

    override fun hashCode(): Int {
        return pattern.hashCode()
    }

    override fun toString(): String {
        return "Pattern{ value='$pattern' }"
    }

    private fun String.sorted(): String {
        return this.toCharArray().sorted().joinToString("")
    }

    fun inverse(): Pattern {
        var result = ""
        for (c in 'a'..'g') {
            if (contains(c).not()) result += c
        }
        return Pattern(result)
    }
}

data class SignalInput(
    val signalPatterns: List<Pattern>,
    val outputPatterns: List<Pattern>
)

