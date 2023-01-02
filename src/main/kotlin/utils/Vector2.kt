package utils

import kotlin.math.abs
import kotlin.math.min
import kotlin.math.sign

data class Vector2(val x: Long, val y: Long) : Comparable<Vector2> {

    constructor(x: Int, y: Int) : this(x.toLong(), y.toLong())

    operator fun plus(other: Vector2): Vector2 = Vector2(this.x + other.x, this.y + other.y)
    operator fun minus(other: Vector2): Vector2 = Vector2(this.x - other.x, this.y - other.y)
    operator fun times(other: Vector2): Vector2 = Vector2(this.x * other.x, this.y * other.y)
    operator fun div(other: Vector2): Vector2 = Vector2(this.x / other.x, this.y / other.y)

    operator fun plus(value: Int): Vector2 = Vector2(this.x + value, this.y + value)
    operator fun plus(value: Long): Vector2 = Vector2(this.x + value, this.y + value)
    operator fun minus(value: Int): Vector2 = Vector2(this.x - value, this.y - value)
    operator fun minus(value: Long): Vector2 = Vector2(this.x - value, this.y - value)
    operator fun times(value: Int): Vector2 = Vector2(this.x * value, this.y * value)
    operator fun times(value: Long): Vector2 = Vector2(this.x * value, this.y * value)
    operator fun div(value: Int): Vector2 = Vector2(this.x / value, this.y / value)
    operator fun div(value: Long): Vector2 = Vector2(this.x / value, this.y / value)

    fun linearMagnitude(): Long = this.x + this.y
    fun abs(): Vector2 = Vector2(abs(this.x), abs(this.y))
    fun manhattanDistanceTo(other: Vector2): Long = (this - other).abs().linearMagnitude()
    fun sign(): Vector2 = Vector2(this.x.sign.toLong(), this.y.sign.toLong())

    infix fun plusX(x: Int): Vector2 = Vector2(this.x + x, this.y)
    infix fun plusX(x: Long): Vector2 = Vector2(this.x + x, this.y)
    infix fun minusX(x: Int): Vector2 = Vector2(this.x - x, this.y)
    infix fun minusX(x: Long): Vector2 = Vector2(this.x - x, this.y)
    infix fun plusY(y: Int): Vector2 = Vector2(this.x, this.y + y)
    infix fun plusY(y: Long): Vector2 = Vector2(this.x, this.y + y)
    infix fun minusY(y: Int): Vector2 = Vector2(this.x, this.y - y)
    infix fun minusY(y: Long): Vector2 = Vector2(this.x, this.y - y)

    operator fun rangeTo(other: Vector2) = Vector2Progression(this, other)

    override fun compareTo(other: Vector2): Int {
        var result = this.x - other.x
        if (result == 0L) result = this.y - other.y
        return result.toInt()
    }

    override fun toString(): String {
        return "($x, $y)"
    }

}

class Vector2Progression(
    override val start: Vector2,
    override val endInclusive: Vector2,
    private val stepSize: Long = 1
) : Iterable<Vector2>, ClosedRange<Vector2> {

    override fun contains(other: Vector2): Boolean {
        TODO("Not implemented")
    }

    override fun iterator(): Iterator<Vector2> = Vector2Iterator(start, endInclusive, stepSize)

    infix fun step(days: Long) = Vector2Progression(start, endInclusive, days)

    override fun toString(): String {
        return "$start..$endInclusive"
    }
}

class Vector2Iterator(
    start: Vector2,
    private val endInclusive: Vector2,
    private val stepSize: Long = 1): Iterator<Vector2> {

    private var currentVal = start

    init {
        val stepX = min(endInclusive.x - currentVal.x, stepSize)
        val stepY = min(endInclusive.y - currentVal.y, stepSize)
        currentVal = Vector2(currentVal.x - stepX, currentVal.y - stepY)
    }

    override fun hasNext(): Boolean {
        return currentVal < endInclusive
    }

    override fun next(): Vector2 {
        val deltaX = endInclusive.x - currentVal.x
        val deltaY = endInclusive.y - currentVal.y
        if(deltaX == 0L && deltaY == 0L) error("No more items")

        val stepX = min(deltaX, stepSize)
        val stepY = min(deltaY, stepSize)

        currentVal = Vector2(currentVal.x + stepX, currentVal.y + stepY)
        return currentVal
    }

}
