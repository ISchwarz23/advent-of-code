package utils

import kotlin.math.abs
import kotlin.math.sign

data class Vector3(val x: Int, val y: Int, val z: Int) {
    operator fun plus(other: Vector3): Vector3 = Vector3(this.x + other.x, this.y + other.y, this.z + other.z)
    operator fun minus(other: Vector3): Vector3 = Vector3(this.x - other.x, this.y - other.y, this.z - other.z)
    operator fun times(other: Vector3): Vector3 = Vector3(this.x * other.x, this.y * other.y, this.z * other.z)
    operator fun div(other: Vector3): Vector3 = Vector3(this.x / other.x, this.y / other.y, this.z / other.z)

    operator fun plus(value: Int): Vector3 = Vector3(this.x + value, this.y + value, this.z + value)
    operator fun minus(value: Int): Vector3 = Vector3(this.x - value, this.y - value, this.z - value)
    operator fun times(value: Int): Vector3 = Vector3(this.x * value, this.y * value, this.z * value)
    operator fun div(value: Int): Vector3 = Vector3(this.x / value, this.y / value, this.z / value)

    fun linearMagnitude(): Int = this.x + this.y + this.z
    fun abs(): Vector3 = Vector3(abs(this.x), abs(this.y), abs(this.z))
    fun sign(): Vector3 = Vector3(this.x.sign, this.y.sign, this.z.sign)
    fun minusX(x: Int): Vector3 = Vector3(this.x - x, this.y, this.z)
    fun minusY(y: Int): Vector3 = Vector3(this.x, this.y - y, this.z)
    fun minusZ(z: Int): Vector3 = Vector3(this.x, this.y, this.z - z)
    fun manhattanDistance(): Int = this.abs().linearMagnitude()
}