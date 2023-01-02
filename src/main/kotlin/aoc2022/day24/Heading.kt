package aoc2022.day24

import utils.Vector2

enum class Heading(val movementVector: Vector2) {
    NORTH(Vector2(0, -1)),
    SOUTH(Vector2(0, 1)),
    WEST(Vector2(-1, 0)),
    EAST(Vector2(1, 0));
}