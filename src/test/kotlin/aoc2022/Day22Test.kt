package aoc2022

import aoc2022.day22.*
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.AocClient
import utils.Vector2
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class Day22Test {

    private val aocClient = AocClient()

    private val testInputMap = readInputAsMap("aoc2022/Day22_test.txt")
    private val testInputMoves = readInputAsMovementInstructions("aoc2022/Day22_test.txt")
    private val inputMap = readInputAsMap("aoc2022/Day22.txt")
    private val inputMoves = readInputAsMovementInstructions("aoc2022/Day22.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = Day22.part1(testInputMap, testInputMoves)

        // then
        assertEquals(6032, result)

        // submit answer
        val answer = Day22.part1(inputMap, inputMoves)
        val submissionResult = aocClient.submit(2022, 22, 1, answer)
        println("Result of Day 22 - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = Day22.part2(testInputMap, testInputMoves)

        // then
        assertEquals(5031, result)

        // submit answer
        val answer = Day22.part2(inputMap, inputMoves)
        val submissionResult = aocClient.submit(2022, 22, 2, answer)
        println("Result of Day 22 - Part 2: $answer (Submission: $submissionResult)")
    }

}

private fun readInputAsMap(name: String): Map<Vector2, FieldType> {
    return readInput(name).takeWhile { it.isNotEmpty() }.flatMapIndexed { index, row -> toMapFields(index + 1, row) }.toMap()
}

private fun toMapFields(rowIndex: Int, rowData: String): List<Pair<Vector2, FieldType>> {
    return rowData.split("").mapIndexed { columnIndex: Int, data: String ->
        when (data) {
            "." -> Vector2(columnIndex, rowIndex) to FieldType.EMPTY
            "#" -> Vector2(columnIndex, rowIndex) to FieldType.WALL
            else -> null
        }
    }.filterNotNull()
}

private fun readInputAsMovementInstructions(name: String): List<MoveInstruction> {
    val instructions = mutableListOf<MoveInstruction>()

    val regex = "([LRN])(\\d+)".toRegex()
    val result = regex.findAll("N${readInput(name).last()}")
    for (match in result) {
        val numberOfSteps = match.groupValues[2].toInt()
        val turnDirection = when (match.groupValues[1]) {
            "L" -> TurnDirection.LEFT
            "R" -> TurnDirection.RIGHT
            else -> TurnDirection.NONE
        }
        instructions += MoveInstruction(turnDirection, numberOfSteps)
    }
    return instructions
}