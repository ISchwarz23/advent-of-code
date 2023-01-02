package aoc2021

import java.util.*

object Day23 {

    fun part1(floorLayout: FloorLayout, amphipodLocations: Map<Amphipod, Space>): Long {
        return getCostOfCheapestRoomSwitching(floorLayout, amphipodLocations)
    }

    fun part2(floorLayout: FloorLayout, amphipodLocations: Map<Amphipod, Space>): Long {
        return getCostOfCheapestRoomSwitching(floorLayout, amphipodLocations)
    }


    private fun getCostOfCheapestRoomSwitching(florLayout: FloorLayout, amphipodLocations: Map<Amphipod, Space>): Long {
        val prioritizedSteps = PriorityStepQueue { s1, s2 -> (s1.consumedEnergy - s2.consumedEnergy).toInt() }
        prioritizedSteps += RoomSwitchingStep(florLayout, AmphipodLocations(amphipodLocations), 0)
        var currentStep: RoomSwitchingStep? = prioritizedSteps.poll()
        while (currentStep != null && !currentStep.allAmphipodsInTargetRooms) {
            prioritizedSteps += currentStep.getNextPossibleSteps()
            currentStep = prioritizedSteps.poll()
        }
        return currentStep?.consumedEnergy ?: -1
    }

}

data class RoomSwitchingStep(
    val floorLayout: FloorLayout,
    val amphipodLocations: AmphipodLocations,
    val consumedEnergy: Long
) {

    fun getNextPossibleSteps(): List<RoomSwitchingStep> {
        val nextSteps = mutableListOf<RoomSwitchingStep>()
        for ((amphipod, currentSpace) in amphipodLocations.locations) {
            if (isAtTargetSpace(amphipod, currentSpace)) {
                continue
            }

            var costs = consumedEnergy
            var spacesToProcess = listOf(currentSpace)
            val alreadyProcessedSpaces = mutableSetOf(currentSpace)
            val otherAmphipodLocations = amphipodLocations.copyAndRemove(amphipod)

            while (spacesToProcess.isNotEmpty()) {
                costs += amphipod.energyPerStep
                val possibleNextSpaces = spacesToProcess.flatMap { floorLayout.getConnectedSpaces(it) }
                    .filterNot { it in alreadyProcessedSpaces }
                alreadyProcessedSpaces += possibleNextSpaces

                nextSteps += possibleNextSpaces.filter { it.canStop(amphipod, currentSpace, otherAmphipodLocations) }.map {
                    val step = RoomSwitchingStep(floorLayout, amphipodLocations.copyAndSet(amphipod, it), costs)
                    //println(step)
                    step
                }
                spacesToProcess = possibleNextSpaces.filter { it.canPass(amphipod, currentSpace, otherAmphipodLocations) }
            }
        }
        return nextSteps
    }

    private fun isAtTargetSpace(amphipod: Amphipod, currentSpace: Space): Boolean {
        if (currentSpace !is RoomSpace) return false
        if (amphipod.shortName != currentSpace.targetAmphipodShortName) return false
        val isAtBottomOfRoom =  floorLayout.roomSpaces.filter { it.targetAmphipodShortName == amphipod.shortName }
            .filter { it.y > currentSpace.y }.all {
                val a = amphipodLocations.getAmphipodAt(it)
                val result = a != null && a.shortName == amphipod.shortName
                result
            }
        return isAtBottomOfRoom
    }

    val allAmphipodsInTargetRooms: Boolean by lazy {
        amphipodLocations.locations.all { (amphipod, space) -> space is RoomSpace && amphipod.shortName == space.targetAmphipodShortName }
    }

    override fun toString(): String {
        var s = ""
        for (y in -1..floorLayout.sizeY) {
            for (x in -1..floorLayout.sizeX) {
                val space = floorLayout.getSpaceAt(x, y)
                s += if (space == null) '█' else amphipodLocations.getAmphipodAt(space)?.shortName ?: ' '
            }
            s += '\n'
        }
        return s
    }
}

private class PriorityStepQueue(comparator: (RoomSwitchingStep, RoomSwitchingStep) -> Int) {

    private val steps = PriorityQueue(comparator)
    private val knownLocations = hashSetOf<Int>()

    operator fun plusAssign(step: RoomSwitchingStep) {
        if (knownLocations.contains(step.amphipodLocations.hashCode())) {
            return
        }
        steps += step
        knownLocations += step.amphipodLocations.hashCode()
    }

    operator fun plusAssign(steps: List<RoomSwitchingStep>) {
        steps.forEach { this += it }
    }

    fun poll(): RoomSwitchingStep? {
        return steps.poll()
    }
}

sealed class Amphipod(val shortName: Char, val energyPerStep: Int) {

    class Amber : Amphipod('A', 1)
    class Bronze : Amphipod('B', 10)
    class Copper : Amphipod('C', 100)
    class Desert : Amphipod('D', 1000)
}

data class AmphipodLocations(val locations: Map<Amphipod, Space>) {
    private val occupiedSpaces: Map<Space, Amphipod> by lazy {
        locations.entries.associate { (k, v) -> v to k }
    }

    fun getLocationOf(amphipod: Amphipod): Space? {
        return locations[amphipod]
    }

    fun getAmphipodAt(space: Space): Amphipod? {
        return occupiedSpaces[space]
    }

    fun copyAndSet(amphipod: Amphipod, newLocation: Space): AmphipodLocations {
        val newLocations = locations.toMutableMap()
        newLocations[amphipod] = newLocation
        return AmphipodLocations(newLocations)
    }

    fun copyAndRemove(amphipod: Amphipod): AmphipodLocations {
        val newLocations = locations.toMutableMap()
        newLocations.remove(amphipod)
        return AmphipodLocations(newLocations)
    }
}

class FloorLayout {

    private val spaceConnections = mutableMapOf<Space, MutableSet<Space>>()
    private val spaces = spaceConnections.keys

    val hallwaySpaces: List<HallwaySpace>
        get() = spaces.filterIsInstance<HallwaySpace>()
    val roomSpaces: List<RoomSpace>
        get() = spaces.filterIsInstance<RoomSpace>()

    val sizeX: Int
        get() = (spaces.maxOfOrNull { it.x } ?: 0) + 1
    val sizeY: Int
        get() = (spaces.maxOfOrNull { it.y } ?: 0) + 1

    fun addConnectedSpaces(firstSpace: Space, secondSpace: Space) {
        addUnidirectionalConnectedSpaces(firstSpace, secondSpace)
        addUnidirectionalConnectedSpaces(secondSpace, firstSpace)
    }

    fun getSpaceAt(x: Int, y: Int): Space? {
        return spaces.find { it.x == x && it.y == y }
    }

    private fun addUnidirectionalConnectedSpaces(space: Space, connectedSpace: Space) {
        val alreadyConnectedSpaces = spaceConnections[space]
        if (alreadyConnectedSpaces == null) {
            // space is new
            spaceConnections[space] = mutableSetOf(connectedSpace)
        } else {
            // space is known
            alreadyConnectedSpaces += connectedSpace
        }
    }

    fun getConnectedSpaces(space: Space): Set<Space> {
        return spaceConnections[space] ?: emptySet()
    }

}

abstract class Space(val x: Int, val y: Int, val floorLayout: FloorLayout) {

    abstract fun canStop(amphipod: Amphipod, previousSpace: Space, otherAmphipodLocations: AmphipodLocations): Boolean

    abstract fun canPass(amphipod: Amphipod, previousSpace: Space, otherAmphipodLocations: AmphipodLocations): Boolean

}

class HallwaySpace(x: Int, y: Int, floorLayout: FloorLayout, private val canStopHere: Boolean = true) :
    Space(x, y, floorLayout) {
    override fun canStop(amphipod: Amphipod, previousSpace: Space, otherAmphipodLocations: AmphipodLocations): Boolean {
        if (!canStopHere) return false
        if (otherAmphipodLocations.getAmphipodAt(this) != null) return false

        // only allowed changing location in hallway, if alone in hallway
        if(previousSpace is HallwaySpace) {
            val otherAmphipodInHallway =
                floorLayout.hallwaySpaces.any { otherAmphipodLocations.getAmphipodAt(it) != null }
            if (otherAmphipodInHallway) return false
        }

        return true
    }

    override fun canPass(amphipod: Amphipod, previousSpace: Space, otherAmphipodLocations: AmphipodLocations): Boolean {
        return otherAmphipodLocations.getAmphipodAt(this) == null
    }
}

class RoomSpace(x: Int, y: Int, floorLayout: FloorLayout, val targetAmphipodShortName: Char) :
    Space(x, y, floorLayout) {
    override fun canStop(amphipod: Amphipod, previousSpace: Space, otherAmphipodLocations: AmphipodLocations): Boolean {
        // check if this space is occupied
        if (otherAmphipodLocations.getAmphipodAt(this) != null) return false

        // check if already in this room -> should leave, so not allowed to stop
        if(previousSpace is RoomSpace && previousSpace.targetAmphipodShortName == targetAmphipodShortName) return false

        // check if this room is the destination for this amphipod
        if (amphipod.shortName != targetAmphipodShortName) return false
        // check if this is bottom most free space
        val isBottomMostFreePositionInRoom =
            floorLayout.roomSpaces.filter { it.targetAmphipodShortName == targetAmphipodShortName }
                .filter { it.y > this.y }
                .map { otherAmphipodLocations.getAmphipodAt(it) }
                .all { it != null && it.shortName == amphipod.shortName }
        if (isBottomMostFreePositionInRoom) return true
        return false
    }

    override fun canPass(amphipod: Amphipod, previousSpace: Space, otherAmphipodLocations: AmphipodLocations): Boolean {
        // check if this space is occupied
        if (otherAmphipodLocations.getAmphipodAt(this) != null) return false

        // check if already in this room
        if(previousSpace is RoomSpace && previousSpace.targetAmphipodShortName == targetAmphipodShortName) return true

        // check if this room is the destination for this amphipod
        if (amphipod.shortName != targetAmphipodShortName) return false
        // check if other kind is still in room
        val otherKindOfAmphipodInRoom =
            floorLayout.roomSpaces.filter { it.targetAmphipodShortName == targetAmphipodShortName }
                .mapNotNull { otherAmphipodLocations.getAmphipodAt(it) }
                .any { it.shortName != amphipod.shortName }
        if (otherKindOfAmphipodInRoom) return false
        return true
    }
}