package aoc2021

import kotlin.math.abs


object Day19 {

    fun part1(scannerResults: List<ScannerResult>): Int {

        val scans = scannerResults.toMutableList()
        var collectedScan = scans[0].copyAndSetPosition(Position3D(0, 0, 0))
        scans -= scans[0]

        while (scans.any { it.position == null }) {
            for (scanWithoutPosition in scans.filter { it.position == null }) {
                collectedScan.locateOverlappingVariant(scanWithoutPosition)?.let {
                    scans.removeIf { s -> s.id == it.id }
                    collectedScan = collectedScan.copyAndAddUniqueBeacons(it)
                }
            }
        }

        return collectedScan.relativeBeaconPositions.size
    }

    fun part2(scannerResults: List<ScannerResult>): Int {

        val scans = scannerResults.toMutableList()
        var collectedScan = scans[0].copyAndSetPosition(Position3D(0, 0, 0))
        scans -= scans[0]

        val centerLocations = mutableListOf(Position3D(0, 0, 0))
        while (scans.any { it.position == null }) {
            for (scanWithoutPosition in scans.filter { it.position == null }) {
                collectedScan.locateOverlappingVariant(scanWithoutPosition)?.let {
                    scans.removeIf { s -> s.id == it.id }
                    collectedScan = collectedScan.copyAndAddUniqueBeacons(it)
                    centerLocations += it.position!!
                }
            }
        }

        var biggestDistance = Int.MIN_VALUE
        for (i in centerLocations.indices) {
            for (j in i until centerLocations.size) {
                biggestDistance = maxOf(biggestDistance, centerLocations[i] manhattanDistanceTo centerLocations[j])
            }
        }
        return biggestDistance
    }

}

class ScannerResult(val id: Int, val relativeBeaconPositions: List<Position3D>, val position: Position3D? = null) {

    private val variations: List<ScannerResult> by lazy {
        val variations = mutableListOf<ScannerResult>()
        for (up in 0 until 6) {
            for (rot in 0 until 4) {
                variations += ScannerResult(
                    id,
                    relativeBeaconPositions.map { it.flipCoordSystem(up).rotateCoordSystem(rot) })
            }
        }
        variations.toList()
    }

    private fun findSensorLocation(other: ScannerResult): Position3D? {
        for (mine in relativeBeaconPositions) {
            for (their in other.relativeBeaconPositions) {
                val (deltaX, deltaY, deltaZ) = their - mine
                var count = 0
                for (secondMine in relativeBeaconPositions) {
                    for (secondTheir in other.relativeBeaconPositions) {
                        val (secondDeltaX, secondDeltaY, secondDeltaZ) = secondTheir - secondMine
                        if (deltaX == secondDeltaX && deltaY == secondDeltaY && deltaZ == secondDeltaZ) {
                            count++
                            if (count >= 12) {
                                return mine - their
                            }
                            break
                        }
                    }
                }
            }
        }
        return null
    }

    fun locateOverlappingVariant(other: ScannerResult): ScannerResult? {
        for (variation in other.variations) {
            val scannerLocation = findSensorLocation(variation)
            if (scannerLocation != null) return variation.copyAndSetPosition(scannerLocation)
        }
        return null
    }

    fun copyAndSetPosition(position: Position3D): ScannerResult {
        return ScannerResult(id, relativeBeaconPositions, position)
    }

    fun copyAndAddUniqueBeacons(other: ScannerResult): ScannerResult {
        if (this.position == null) throw RuntimeException("Can't calculate absolute beacon locations without scan position")
        if (other.position == null) throw RuntimeException("Can't calculate absolute beacon locations without scan position")
        val absoluteOtherBeacons = other.relativeBeaconPositions.map { it + other.position }
        return ScannerResult(id, (relativeBeaconPositions + absoluteOtherBeacons).distinct(), position)
    }

}

data class Position3D(val x: Int, val y: Int, val z: Int) {

    fun flipCoordSystem(up: Int): Position3D {
        return when (up) {
            0 -> this
            1 -> Position3D(x, -y, -z)
            2 -> Position3D(x, -z, y)
            3 -> Position3D(-y, -z, x)
            4 -> Position3D(-x, -z, -y)
            5 -> Position3D(y, -z, -x)
            else -> throw RuntimeException("Invalid value $up")
        }
    }

    fun rotateCoordSystem(rot: Int): Position3D {
        return when (rot) {
            0 -> this
            1 -> Position3D(-y, x, z)
            2 -> Position3D(-x, -y, z)
            3 -> Position3D(y, -x, z)
            else -> throw RuntimeException("Invalid value $rot")
        }
    }

    operator fun plus(other: Position3D): Position3D {
        return Position3D(this.x + other.x, this.y + other.y, this.z + other.z)
    }

    operator fun minus(other: Position3D): Position3D {
        return Position3D(this.x - other.x, this.y - other.y, this.z - other.z)
    }

    infix fun manhattanDistanceTo(other: Position3D): Int {
        return abs(other.x - x) + abs(other.y - y) + abs(other.z - z)
    }

}
