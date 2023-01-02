package aoc2021

object Day25 {

    fun part1(input: List<List<Char>>): Int {
        var counter = 0

        val cucumberMoveOrder = listOf('>', 'v')

        var previousMap: List<List<Char>>
        var currentMap = input
        do {
            previousMap = currentMap.toList()
            currentMap = doMove(previousMap, cucumberMoveOrder)
            counter++
        } while (previousMap != currentMap)

        return counter
    }

    private fun doMove(map: List<List<Char>>, moveOrder: List<Char>): List<List<Char>> {
        val resultingMap = List(map.size) { MutableList(map[0].size) { '.' } }
        for (i in moveOrder.indices) {
            val laterMoves = moveOrder.subList(i + 1, moveOrder.size)
            when (moveOrder[i]) {
                '>' -> doEastMoveSFCT(map, resultingMap, laterMoves)
                'v' -> doMoveSouthSFCT(map, resultingMap, laterMoves)
            }
        }
        return resultingMap
    }

    private fun doEastMoveSFCT(
        originalMap: List<List<Char>>,
        mapToAdapt: List<MutableList<Char>>,
        cucumbersMovingLater: List<Char>
    ) {
        for (y in originalMap.indices) {
            for (x in originalMap[y].indices) {
                val cucumber = originalMap[y][x]
                if (cucumber == '>') {
                    if (x + 1 < originalMap[y].size) {
                        // move right
                        if (originalMap[y][x + 1] != '>' && originalMap[y][x + 1] !in cucumbersMovingLater && mapToAdapt[y][x + 1] == '.') mapToAdapt[y][x + 1] =
                            '>'
                        else mapToAdapt[y][x] = '>'
                    } else {
                        // jump to left
                        if (originalMap[y][0] != '>' && originalMap[y][0] !in cucumbersMovingLater && mapToAdapt[y][0] == '.') mapToAdapt[y][0] =
                            '>'
                        else mapToAdapt[y][x] = '>'
                    }
                }
            }
        }
    }

    private fun doMoveSouthSFCT(
        originalMap: List<List<Char>>,
        mapToAdapt: List<MutableList<Char>>,
        cucumbersMovingLater: List<Char>
    ) {
        for (y in originalMap.indices) {
            for (x in originalMap[y].indices) {
                val cucumber = originalMap[y][x]
                if (cucumber == 'v') {
                    if (y + 1 < originalMap.size) {
                        // move down
                        if (originalMap[y + 1][x] != 'v' && originalMap[y + 1][x] !in cucumbersMovingLater && mapToAdapt[y + 1][x] == '.') mapToAdapt[y + 1][x] =
                            'v'
                        else mapToAdapt[y][x] = 'v'
                    } else {
                        // jump to top
                        if (originalMap[0][x] != 'v' && originalMap[0][x] !in cucumbersMovingLater && mapToAdapt[0][x] == '.') mapToAdapt[0][x] =
                            'v'
                        else mapToAdapt[y][x] = 'v'
                    }
                }
            }
        }
    }

}
