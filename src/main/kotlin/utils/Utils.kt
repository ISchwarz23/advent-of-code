package utils

import java.math.BigInteger
import java.security.MessageDigest
import kotlin.math.absoluteValue
import kotlin.math.pow

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
    when (behavior) {
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

fun <T> List<T>.split(predicate: (T) -> Boolean): List<List<T>> {
    return flatMapIndexed { index, x ->
            when {
                index == 0 || index == lastIndex -> listOf(index)
                predicate(x) -> listOf(index - 1, index + 1)
                else -> emptyList()
            }
        }
        .windowed(size = 2, step = 2) { (from, to) -> slice(from..to) }
}


infix fun Int.pow(exponent: Int): Int {
    return this.toDouble().pow(exponent).toInt()
}

fun Char.repeat(i: Int): String {
    var result = ""
    repeat(i) { result += this }
    return result
}
