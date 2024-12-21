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

class MathUtils {

    companion object {

        fun greatestCommonDivisor(n1: Int, n2: Int): Int {
            return greatestCommonDivisor(n1.toLong(), n2.toLong()).toInt()
        }

        fun greatestCommonDivisor(n1: Long, n2: Long): Long {
            var gcd = 1L
            var i = 1L
            while (i <= n1 && i <= n2) {
                // Checks if i is factor of both integers
                if (n1 % i == 0L && n2 % i == 0L)
                    gcd = i
                ++i
            }
            return gcd
        }

        fun leastCommonMultiple(n1: Int, n2: Int): Int {
            return leastCommonMultiple(n1.toLong(), n2.toLong()).toInt()
        }

        fun leastCommonMultiple(n1: Long, n2: Long): Long {
            return n1 * n2 / greatestCommonDivisor(n1, n2)
        }

    }

}
