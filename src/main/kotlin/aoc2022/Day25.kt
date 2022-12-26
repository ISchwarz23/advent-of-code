package aoc2022

import utils.pow
import kotlin.math.abs

object Day25 {

    fun part1(input: List<String>): String {
        val decimal = input.sumOf { safuToDecimal(it) }
        return decimalToSafu(decimal)
    }

}

fun safuToDecimal(safu: String): Long {
    return safu.chunked(1)
        .map { safuDigitToDecimalDigit(it) }
        .reversed()
        .foldIndexed(0L) { index, acc, next ->
            acc + (next * (5L pow index))
        }
}

fun decimalToSafu(decimal: Long): String {

    fun getMaxRestValue(exponent: Int): Long {
        return (0 until exponent).sumOf { 2L * (5L pow it) } // Could be cached
    }

    fun getDigit(value: Long, digitIndex: Int): Pair<Int, Long> {
        var digit = 2
        while (abs(value - (digit.toLong() * (5L pow digitIndex))) > getMaxRestValue(digitIndex)) digit--
        return Pair(digit, (value - (digit.toLong() * (5L pow digitIndex))))
    }

    // get number of digits of safu number
    var numberOfDigits = 0
    while (decimal > 2L * (5L pow numberOfDigits)) numberOfDigits++

    // get safu digits
    var rest = decimal
    val digits = mutableListOf<Int>()
    while (numberOfDigits >= 0) {
        val result = getDigit(rest, numberOfDigits)
        digits += result.first
        rest = result.second
        numberOfDigits--
    }
    return digits.joinToString("") { decimalDigitToSafuDigit(it) }
}

fun safuDigitToDecimalDigit(safu: String): Long {
    return when (safu) {
        "=" -> -2
        "-" -> -1
        "0" -> 0
        "1" -> 1
        "2" -> 2
        else -> error("Unknown safu digit '$safu'")
    }
}

fun decimalDigitToSafuDigit(value: Int): String {
    return when (value) {
        -2 -> "="
        -1 -> "-"
        0 -> "0"
        1 -> "1"
        2 -> "2"
        else -> error("Decimal digit '$value' out of safu range")
    }
}
