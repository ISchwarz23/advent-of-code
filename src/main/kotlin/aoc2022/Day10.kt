package aoc2022

object Day10 {

    fun part1(input: List<String>): Long {

        val operations = input.map { toOperation(it) }
        var signalStrength: Long = 0
        var registerValue = 1
        var cycle = 0

        for (op in operations) {
            val prevCycle = cycle
            cycle += op.cycles

            val prevMod = (prevCycle - 20).mod(40)
            val currMod = (cycle - 20).mod(40)
            if (currMod < prevMod) {
                signalStrength += getSignalStrength(cycle - currMod, registerValue)
            }

            registerValue += op.value
        }

        return signalStrength
    }

    fun part2(input: List<String>): Int {

        val screen = List(6) { MutableList(40) { " " } }
        val operations = input.map { toOperation(it) }

        var cycle = 0
        var spritePos = 1

        for (op in operations) {
            val sprite = (spritePos - 1)..(spritePos + 1)

            repeat(op.cycles) {
                val screenPosX = cycle.mod(40)
                val screenPosY = cycle / 40
                screen[screenPosY][screenPosX] = if (screenPosX in sprite) "#" else "."
                cycle++
            }
            spritePos += op.value
        }
        print(screen)
        return 0
    }
}

private fun print(screen: List<MutableList<String>>) {
    screen.map { it.joinToString("") }.forEach { println(it) }
    println()
}

private fun getSignalStrength(cycle: Int, value: Int): Int = cycle * value

private fun toOperation(operationStr: String): Operation {
    return if (operationStr == "noop") Operation.NoOperation() else Operation.AddXOperation(operationStr.split(" ")[1].toInt())
}

sealed class Operation(val cycles: Int, val value: Int) {

    class NoOperation : Operation(1, 0)
    class AddXOperation(numberToAdd: Int) : Operation(2, numberToAdd)

}