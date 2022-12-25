package utils

import kotlin.math.abs
import kotlin.math.sign

data class Vector2(val x: Long, val y: Long) {

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
    fun manhattanDistance(): Long = this.abs().linearMagnitude()
    fun sign(): Vector2 = Vector2(this.x.sign.toLong(), this.y.sign.toLong())

    infix fun plusX(x: Int): Vector2 = Vector2(this.x + x, this.y)
    infix fun plusX(x: Long): Vector2 = Vector2(this.x + x, this.y)
    infix fun minusX(x: Int): Vector2 = Vector2(this.x - x, this.y)
    infix fun minusX(x: Long): Vector2 = Vector2(this.x - x, this.y)
    infix fun plusY(y: Int): Vector2 = Vector2(this.x, this.y + y)
    infix fun plusY(y: Long): Vector2 = Vector2(this.x, this.y + y)
    infix fun minusY(y: Int): Vector2 = Vector2(this.x, this.y - y)
    infix fun minusY(y: Long): Vector2 = Vector2(this.x, this.y - y)

    override fun toString(): String {
        return "($x, $y)"
    }
}