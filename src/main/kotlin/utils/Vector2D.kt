package utils

import kotlin.math.abs
import kotlin.math.sign

data class Vector2(val x: Int, val y: Int) {
    operator fun plus(other: Vector2): Vector2 = Vector2(this.x + other.x, this.y + other.y)
    operator fun minus(other: Vector2): Vector2 = Vector2(this.x - other.x, this.y - other.y)
    operator fun times(other: Vector2): Vector2 = Vector2(this.x * other.x, this.y * other.y)
    operator fun div(other: Vector2): Vector2 = Vector2(this.x / other.x, this.y / other.y)

    operator fun plus(value: Int): Vector2 = Vector2(this.x + value, this.y + value)
    operator fun minus(value: Int): Vector2 = Vector2(this.x - value, this.y - value)
    operator fun times(value: Int): Vector2 = Vector2(this.x * value, this.y * value)
    operator fun div(value: Int): Vector2 = Vector2(this.x / value, this.y / value)

    fun linearMagnitude(): Int = this.x + this.y
    fun abs(): Vector2 = Vector2(abs(this.x), abs(this.y))
    fun sign(): Vector2 = Vector2(this.x.sign, this.y.sign)
    fun minusX(x: Int): Vector2 = Vector2(this.x - x, this.y)
    fun minusY(y: Int): Vector2 = Vector2(this.x, this.y - y)
    fun manhattanDistance(): Int = this.abs().linearMagnitude()
}