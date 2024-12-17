package utils

import kotlin.math.absoluteValue
import kotlin.math.pow


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

infix fun Long.pow(exponent: Int): Long {
    return this.toDouble().pow(exponent).toLong()
}

infix fun Long.pow(exponent: Long): Long {
    return this.toDouble().pow(exponent.toDouble()).toLong()
}
