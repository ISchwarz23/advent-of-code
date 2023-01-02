package utils

import java.math.BigInteger
import java.security.MessageDigest


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


val String.allCharsUnique: Boolean
    get() = all(hashSetOf<Char>()::add)

enum class SplitCharBehavior {
    ADD_CHAR_AT_SPLIT_INDEX_TO_FIRST,
    ADD_CHAR_AT_SPLIT_INDEX_TO_SECOND,
    OMIT_CHAR_AT_SPLIT_INDEX
}

fun Char.repeat(i: Int): String {
    var result = ""
    repeat(i) { result += this }
    return result
}
