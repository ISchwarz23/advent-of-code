package aoc2021

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.readInput
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
@Ignore("Ignore as processing is pretty slow")
internal class Day19Test {

    private val testInput = readInputAsScannerResults("aoc2021/day19_example.txt")
    private val input = readInputAsScannerResults("aoc2021/day19.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day19.part1(testInput)

        // then
        assertEquals(79, result)

        // get solution
        println("Result of Day 19 - Part 1: ${Day19.part1(input)}")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day19.part2(testInput)

        // then
        assertEquals(3621, result)

        // get solution
        println("Result of Day 19 - Part 2: ${Day19.part2(input)}")
    }

    private fun readInputAsScannerResults(name: String): List<ScannerResult> {
        val lines = readInput(name)
        val scannerResults = mutableListOf<ScannerResult>()
        var currentScannerId = 0
        var currentScannerBeacons = mutableListOf<Position3D>()
        for (line in lines) {
            if(line.isEmpty()) {
                scannerResults += ScannerResult(currentScannerId, currentScannerBeacons)
            } else if(line.startsWith("---")) {
                val fromIndex = "--- scanner ".length
                val toIndex = line.indexOf(" ---")
                currentScannerId = line.substring(fromIndex, toIndex).toInt()
                currentScannerBeacons = mutableListOf()
            } else {
                currentScannerBeacons += line.toPosition3D()
            }
        }
        scannerResults += ScannerResult(currentScannerId, currentScannerBeacons)
        return scannerResults
    }

    private fun String.toPosition3D(): Position3D {
        val coords = split(",").map { it.toInt() }
        return Position3D(coords[0], coords[1], coords[2])
    }

}
