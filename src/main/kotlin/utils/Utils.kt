package utils

import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.math.absoluteValue
import kotlin.math.pow

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src/main/resources", "$name.txt").readLines()

/**
 * Reads lines from the given input txt file as in values.
 */
fun readInputAsInts(name: String) = readInput(name).map { it.toInt() }

/**
 * Reads one line as String.
 */
fun readOneLineInputAsString(name: String): String {
    return readInput(name)[0]
}

/**
 * Reads one line and map to Ints.
 */
fun readOneLineInputAsInts(name: String, delimiter: String = ","): List<Int> {
    return readInput(name)[0].split(delimiter).map { it.toInt() }
}

fun readInputAs2dIntArray(name: String, delimiter: String = ""): List<List<Int>> {
    return readInput(name).map { it.toCharArray().map { c -> c.digitToInt() } }
}

/**
 * Converts string to com.schwarz.aoc.utils.md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)


/**
 * Splits the string in two parts at the given index.
 */
fun String.split(
    index: Int,
    trimStart: Int = 0,
    trimEnd: Int = 0,
    behavior: SplitCharBehavior = SplitCharBehavior.ADD_CHAR_AT_SPLIT_INDEX_TO_SECOND
): Pair<String, String> {
    val firstEndIndex: Int
    val secondStartIndex: Int
    when(behavior) {
        SplitCharBehavior.ADD_CHAR_AT_SPLIT_INDEX_TO_FIRST -> {
            firstEndIndex = index + 1
            secondStartIndex = index + 1
        }
        SplitCharBehavior.ADD_CHAR_AT_SPLIT_INDEX_TO_SECOND -> {
            firstEndIndex = index
            secondStartIndex = index
        }
        SplitCharBehavior.OMIT_CHAR_AT_SPLIT_INDEX -> {
            firstEndIndex = index
            secondStartIndex = index + 1
        }
    }
    return Pair(substring(trimStart, firstEndIndex), substring(secondStartIndex, length - trimEnd))
}

enum class SplitCharBehavior {
    ADD_CHAR_AT_SPLIT_INDEX_TO_FIRST,
    ADD_CHAR_AT_SPLIT_INDEX_TO_SECOND,
    OMIT_CHAR_AT_SPLIT_INDEX
}

/**
 * Math util to calculate partial sum.
 */
fun Int.calcPartialSum(): Int {
    val value = (this.absoluteValue * (this.absoluteValue + 1)) / 2
    return if (this < 0) -value else value
}


infix fun Int.pow(exponent: Int): Int {
    return this.toDouble().pow(exponent).toInt()
}

fun Char.repeat(i: Int): String {
    var result = ""
    repeat(i) { result += this }
    return result
}
