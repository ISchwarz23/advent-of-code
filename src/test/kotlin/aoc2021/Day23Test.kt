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
@Ignore("Reason: Not working")
internal class Day23Test {

    private val inputPart1Example = readInputAsFloorLayoutAndAmphipodLocations("aoc2021/day23_part1_example.txt")
    private val inputPart1 = readInputAsFloorLayoutAndAmphipodLocations("aoc2021/day23_part1.txt")

    private val inputPart2Example = readInputAsFloorLayoutAndAmphipodLocations("aoc2021/day23_part2_example.txt")
    private val inputPart2 = readInputAsFloorLayoutAndAmphipodLocations("aoc2021/day23_part2.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day23.part1(inputPart1Example.first, inputPart1Example.second)

        // then
        assertEquals(12521, result)

        // get solution
        println("Result of Day 23 - Part 1: ${Day23.part1(inputPart1.first, inputPart1.second)}")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day23.part2(inputPart2Example.first, inputPart2Example.second)

        // then
        assertEquals(44169, result)

        // get solution
        println("Result of Day 23 - Part 2: ${Day23.part2(inputPart2.first, inputPart2.second)}")
    }

    private fun readInputAsFloorLayoutAndAmphipodLocations(name: String): Pair<FloorLayout, Map<Amphipod, Space>> {
        val floorLayout = FloorLayout()
        val amphipodLocations = mutableMapOf<Amphipod, Space>()

        val lines = readInput(name)

        fun canStopHere(x: Int, y: Int): Boolean {
            return lines[y + 1][x] == '#'
        }

        var notAddedYet: Space? = null
        fun addSpaceToLayout(space: Space) {
            val spaceToLeft = floorLayout.getSpaceAt(space.x - 1, space.y)
            if (spaceToLeft != null) {
                floorLayout.addConnectedSpaces(spaceToLeft, space)
            } else {
                val spaceAbove = floorLayout.getSpaceAt(space.x, space.y - 1)
                if (spaceAbove != null) {
                    floorLayout.addConnectedSpaces(spaceAbove, space)
                } else if (notAddedYet != null) {
                    floorLayout.addConnectedSpaces(notAddedYet!!, space)
                    notAddedYet = null
                } else {
                    notAddedYet = space
                }
            }
        }

        for (y in 1 until lines.size) {
            val line = lines[y]
            val lineAsChars = line.toCharArray()
            var target: Char = 'A' - 1
            for (x in 1 until line.length) {
                when (lineAsChars[x]) {
                    '.' -> {
                        val hallwaySpace = HallwaySpace(x - 1, y - 1, floorLayout, canStopHere(x, y))
                        addSpaceToLayout(hallwaySpace)
                    }
                    'A' -> {
                        val roomSpace = RoomSpace(x - 1, y - 1, floorLayout, ++target)
                        addSpaceToLayout(roomSpace)
                        amphipodLocations[Amphipod.Amber()] = roomSpace
                    }
                    'B' -> {
                        val roomSpace = RoomSpace(x - 1, y - 1, floorLayout, ++target)
                        addSpaceToLayout(roomSpace)
                        amphipodLocations[Amphipod.Bronze()] = roomSpace
                    }
                    'C' -> {
                        val roomSpace = RoomSpace(x - 1, y - 1, floorLayout, ++target)
                        addSpaceToLayout(roomSpace)
                        amphipodLocations[Amphipod.Copper()] = roomSpace
                    }
                    'D' -> {
                        val roomSpace = RoomSpace(x - 1, y - 1, floorLayout, ++target)
                        addSpaceToLayout(roomSpace)
                        amphipodLocations[Amphipod.Desert()] = roomSpace
                    }
                }
            }
        }
        return Pair(floorLayout, amphipodLocations)
    }

}