package utils

import kotlin.math.abs
import kotlin.math.sign

data class Vector3(val x: Long, val y: Long, val z: Long) {

    constructor(x: Int, y: Int, z: Int): this(x.toLong(), y.toLong(), z.toLong())

    operator fun plus(other: Vector3): Vector3 = Vector3(this.x + other.x, this.y + other.y, this.z + other.z)
    operator fun minus(other: Vector3): Vector3 = Vector3(this.x - other.x, this.y - other.y, this.z - other.z)
    operator fun times(other: Vector3): Vector3 = Vector3(this.x * other.x, this.y * other.y, this.z * other.z)
    operator fun div(other: Vector3): Vector3 = Vector3(this.x / other.x, this.y / other.y, this.z / other.z)

    operator fun plus(value: Int): Vector3 = Vector3(this.x + value, this.y + value, this.z + value)
    operator fun plus(value: Long): Vector3 = Vector3(this.x + value, this.y + value, this.z + value)
    operator fun minus(value: Int): Vector3 = Vector3(this.x - value, this.y - value, this.z - value)
    operator fun minus(value: Long): Vector3 = Vector3(this.x - value, this.y - value, this.z - value)
    operator fun times(value: Int): Vector3 = Vector3(this.x * value, this.y * value, this.z * value)
    operator fun times(value: Long): Vector3 = Vector3(this.x * value, this.y * value, this.z * value)
    operator fun div(value: Int): Vector3 = Vector3(this.x / value, this.y / value, this.z / value)
    operator fun div(value: Long): Vector3 = Vector3(this.x / value, this.y / value, this.z / value)

    fun linearMagnitude(): Long = this.x + this.y + this.z
    fun abs(): Vector3 = Vector3(abs(this.x), abs(this.y), abs(this.z))
    fun manhattanDistance(): Long = this.abs().linearMagnitude()
    fun sign(): Vector3 = Vector3(this.x.sign.toLong(), this.y.sign.toLong(), this.z.sign.toLong())

    fun minusX(x: Int): Vector3 = Vector3(this.x - x, this.y, this.z)
    fun minusX(x: Long): Vector3 = Vector3(this.x - x, this.y, this.z)
    fun plusX(x: Int): Vector3 = Vector3(this.x + x, this.y, this.z)
    fun plusX(x: Long): Vector3 = Vector3(this.x + x, this.y, this.z)
    fun minusY(y: Int): Vector3 = Vector3(this.x, this.y - y, this.z)
    fun minusY(y: Long): Vector3 = Vector3(this.x, this.y - y, this.z)
    fun plusY(y: Int): Vector3 = Vector3(this.x, this.y + y, this.z)
    fun plusY(y: Long): Vector3 = Vector3(this.x, this.y + y, this.z)
    fun minusZ(z: Int): Vector3 = Vector3(this.x, this.y, this.z - z)
    fun minusZ(z: Long): Vector3 = Vector3(this.x, this.y, this.z - z)
    fun plusZ(z: Int): Vector3 = Vector3(this.x, this.y, this.z + z)
    fun plusZ(z: Long): Vector3 = Vector3(this.x, this.y, this.z + z)

    override fun toString(): String {
        return "($x, $y, $z)"
    }
}