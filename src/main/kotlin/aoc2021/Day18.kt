package aoc2021

object Day18 {

    fun part1(snailfishNumbers: List<SnailfishNumber>): Long {
        var result = snailfishNumbers[0]
        for (nextNumber in snailfishNumbers.subList(1, snailfishNumbers.size)) {
            result += nextNumber
        }
        return result.magnitude
    }

    fun part2(snailfishNumbers: List<SnailfishNumber>): Long {
        val magnitudes = mutableListOf<Long>()

        for (pointer1 in 0 until (snailfishNumbers.size - 1)) {
            for (pointer2 in (pointer1 + 1) until snailfishNumbers.size) {
                val snailfishNumber1 = snailfishNumbers[pointer1]
                val snailfishNumber2 = snailfishNumbers[pointer2]
                magnitudes += (snailfishNumber1 + snailfishNumber2).magnitude
                magnitudes += (snailfishNumber2 + snailfishNumber1).magnitude
            }
        }
        return magnitudes.maxOf { it }
    }

}

sealed interface SnailfishNumber {

    val magnitude: Long
    fun addLeft(value: Int)
    fun addRight(value: Int)
    fun clone(): SnailfishNumber

    operator fun plus(other: SnailfishNumber): SnailfishNumber {
        val newNumber = Pair(this.clone(), other.clone())

        var didExplode: Boolean
        var didSplit: Boolean
        do {
            didExplode = newNumber.explode()
            didSplit = if (didExplode) false else newNumber.split()
        } while (didSplit || didExplode)
        return newNumber
    }

    fun split(): Boolean {
        return split(this)
    }

    private fun split(n: SnailfishNumber): Boolean {
        if (n !is Pair) return false

        // execute split on the left
        val left = n.left
        if (left is Literal) {
            if (left.value > 9) {
                n.left = left.toPair()
                return true
            }
        } else {
            if (split(left)) return true
        }
        // execute split on the right
        val right = n.right
        if (right is Literal) {
            if (right.value > 9) {
                n.right = right.toPair()
                return true
            }
        } else {
            if (split(right)) return true
        }
        // no split executed
        return false
    }

    fun explode(): Boolean {
        return explode(this, 1).didExplode
    }

    private fun explode(n: SnailfishNumber, depth: Int = 1): ExplosionResult {
        if (n !is Pair) return ExplosionResult(false)

        if (depth > 4) {
            // too deep -> need to explode
            val left = n.left
            val right = n.right
            if (left !is Literal || right !is Literal) throw IllegalArgumentException("The number to explode should only contain literals")
            return ExplosionResult(true, left.value, right.value)
        }

        // check explosions left
        val explosionResultLeft = explode(n.left, depth + 1)
        if (explosionResultLeft.didExplode) {
            if (explosionResultLeft.rightValue != null) {
                if (depth == 4) n.left = Literal(0)
                n.right.addLeft(explosionResultLeft.rightValue)
            }
            return ExplosionResult(true, explosionResultLeft.leftValue, null)
        }
        // check explosions right
        val explosionResultRight = explode(n.right, depth + 1)
        if (explosionResultRight.didExplode) {
            if (explosionResultRight.leftValue != null) {
                if (depth == 4) n.right = Literal(0)
                n.left.addRight(explosionResultRight.leftValue)
            }
            return ExplosionResult(true, null, explosionResultRight.rightValue)
        }
        // no explosions left or right
        return ExplosionResult(false)
    }

    private data class ExplosionResult(val didExplode: Boolean, val leftValue: Int? = null, val rightValue: Int? = null)

    data class Literal(var value: Int) : SnailfishNumber {
        override val magnitude: Long
            get() = value.toLong()

        override fun toString(): String = "$value"
        override fun addLeft(value: Int) {
            this.value += value
        }

        override fun addRight(value: Int) {
            this.value += value
        }

        override fun clone(): SnailfishNumber = Literal(value)
        fun toPair(): Pair = Pair(Literal(value / 2), Literal((value + 1) / 2))
    }

    data class Pair(var left: SnailfishNumber, var right: SnailfishNumber) : SnailfishNumber {
        override val magnitude: Long
            get() = 3 * left.magnitude + 2 * right.magnitude

        override fun toString(): String = "[$left,$right]"
        override fun addLeft(value: Int) = left.addLeft(value)
        override fun addRight(value: Int) = right.addRight(value)
        override fun clone(): SnailfishNumber = Pair(left.clone(), right.clone())
    }

}
