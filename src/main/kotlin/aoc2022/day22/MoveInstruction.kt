package aoc2022.day22

data class MoveInstruction(
    val turnDirection: TurnDirection,
    val numberOfSteps: Int
)