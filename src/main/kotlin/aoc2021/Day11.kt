package aoc2021

object Day11 {

    fun part1(octopusField: OctopusField): Int {
        var flashes = 0
        repeat(100) { flashes += octopusField.step() }
        return flashes
    }

    fun part2(octopusField: OctopusField): Int {
        var stepCount = 0
        while (octopusField.all { it.energyLevel == 0 }.not()) {
            octopusField.step()
            stepCount++
        }
        return stepCount
    }
}


class OctopusField(octopusMapData: List<List<Int>>) {

    private val octopuses: List<Octopus> = octopusMapData.mapIndexed { y, rows ->
        rows.mapIndexed { x, value -> Octopus(x, y, value, this) }
    }.flatten()

    val width: Int = octopusMapData[0].size
    val height: Int = octopusMapData.size

    /**
     * @return number of flashes in this step
     */
    fun step(): Int {
        var flashCount: Int
        octopuses.forEach { it.increaseEnergyLevel() }
        var newFlashCount = Int.MIN_VALUE
        do {
            flashCount = newFlashCount
            octopuses.forEach { if (it.energyLevel > 9) it.flash() }
            newFlashCount = octopuses.count { it.didFlash }
        } while (flashCount != newFlashCount)
        octopuses.forEach { it.prepareForNextStep() }
        return flashCount
    }

    private fun forEachCoordinate(operation: (x: Int, y: Int) -> Unit) {
        for (y in 0 until height)
            for (x in 0 until width)
                operation.invoke(x, y)
    }

    fun getOctopusAt(x: Int, y: Int): Octopus = octopuses[y * width + x]

    override fun toString(): String {
        return buildString {
            forEachCoordinate { x, y ->
                if (x == 0) append("\n")
                append(getOctopusAt(x, y).energyLevel)
            }
        }
    }

    fun all(predicate: (octopus: Octopus) -> Boolean): Boolean {
        return octopuses.all(predicate)
    }
}

class Octopus(
    private val x: Int,
    private val y: Int,
    var energyLevel: Int,
    private val field: OctopusField
) {

    private var _didFlash = false

    val didFlash
        get() = _didFlash

    private val neighbours: List<Octopus>
        get() {
            val neighbours = mutableListOf<Octopus>()
            for (tempY in y - 1..y + 1) {
                for (tempX in x - 1..x + 1) {
                    if (tempX < 0) continue
                    if (tempY < 0) continue
                    if (tempX >= this.field.width) continue
                    if (tempY >= this.field.height) continue
                    if (tempX == x && tempY == y) continue
                    neighbours += this.field.getOctopusAt(tempX, tempY)
                }
            }
            return neighbours
        }

    fun increaseEnergyLevel() {
        if (didFlash) return
        energyLevel++
    }

    fun flash() {
        _didFlash = true
        energyLevel = 0
        neighbours.forEach { it.increaseEnergyLevel() }
    }

    fun prepareForNextStep() {
        _didFlash = false
    }

}