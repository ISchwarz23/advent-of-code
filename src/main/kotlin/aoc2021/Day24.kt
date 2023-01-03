package aoc2021

object Day24 {

    fun part1(input: List<String>): Long {
        val constantBlocks = parseConstantFromInput(input)

        val monad = IntArray(14)
        val stack = ArrayDeque<Constants>()
        for (currentBlock in constantBlocks) {
            if (currentBlock.a >= 10) {
                stack.addLast(currentBlock)
            } else {
                val currentIdx = currentBlock.index
                val previousBlock = stack.removeLast()
                if (previousBlock.b + currentBlock.a >= 0) {
                    monad[currentIdx] = 9
                    monad[previousBlock.index] = monad[currentIdx] - (previousBlock.b + currentBlock.a)
                } else {
                    monad[previousBlock.index] = 9
                    monad[currentIdx] = monad[previousBlock.index] + (previousBlock.b + currentBlock.a)
                }
            }
        }

        return monad.joinToString("") { "$it" }.toLong()
    }


    fun part2(input: List<String>): Long {
        val constantBlocks = parseConstantFromInput(input)

        val monad = IntArray(14)
        val stack = ArrayDeque<Constants>()
        for (currentBlock in constantBlocks) {
            if (currentBlock.a >= 10) {
                stack.addLast(currentBlock)
            } else {
                val currentIdx = currentBlock.index
                val previousBlock = stack.removeLast()
                if (previousBlock.b + currentBlock.a >= 0) {
                    monad[previousBlock.index] = 1
                    monad[currentIdx] = monad[previousBlock.index] + (previousBlock.b + currentBlock.a)
                } else {
                    monad[currentIdx] = 1
                    monad[previousBlock.index] = monad[currentIdx] - (previousBlock.b + currentBlock.a)
                }
            }
        }

        return monad.joinToString("") { "$it" }.toLong()
    }
}


data class Constants(val index: Int, val a: Int, val b: Int)

private fun parseConstantFromInput(input: List<String>): List<Constants> {
    val constantBlocks = input.chunked(18).mapIndexed { index, it ->
        Constants(
            index,
            it[5].split(" ").last().toInt(),
            it[15].split(" ").last().toInt()
        )
    }
    return constantBlocks
}