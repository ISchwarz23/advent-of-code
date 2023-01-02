package aoc2021

import java.lang.Long.max
import java.util.*

object Day21 {

    fun part1(startPositionPlayer1: Int, startPositionPlayer2: Int): Int {

        val die = DeterministicDie(100)
        val player1 = Player(startPositionPlayer1)
        val player2 = Player(startPositionPlayer2)
        var game = Game(player1, player2, die, 1000)

        while (game.isGameOver.not()) {
            game = game.playTurn()[0]
        }

        val loosingPlayer = if (game.didPlayer1Win) game.player2 else game.player1
        return loosingPlayer.score * game.die.numberOfRolls
    }

    fun part2(startPositionPlayer1: Int, startPositionPlayer2: Int): Long {

        val die = QuantumDie(3)
        val player1 = Player(startPositionPlayer1)
        val player2 = Player(startPositionPlayer2)

        var winsPlayer1 = 0L
        var winsPlayer2 = 0L

        val gameStates = hashMapOf(Game(player1, player2, die, 21) to 1L).toMutableMap()
        while (gameStates.isNotEmpty()) {
            val oldGameStates = gameStates.toMap()
            gameStates.clear()
            for ((gameState, quantity) in oldGameStates) {
                val newGameStates = gameState.playTurn()
                for (newGameState in newGameStates) {
                    if (newGameState.didPlayer1Win) {
                        winsPlayer1 += quantity
                    } else if (newGameState.didPlayer2Win) {
                        winsPlayer2 += quantity
                    } else {
                        gameStates[newGameState] = gameStates.getOrDefault(newGameState, 0L) + quantity
                    }
                }
            }
        }
        return max(winsPlayer1, winsPlayer2)
    }
}


data class Player(val position: Int, val score: Int = 0) {

    fun copy(): Player {
        return Player(position, score)
    }
}

interface Die {
    val numberOfRolls: Int
    fun roll(numberOfRolls: Int = 1): List<Int>
    fun copy(): Die
}

class DeterministicDie(private val sides: Int, private var _numberOfRolls: Int = 0, startValue: Int = 0) : Die {

    private var lastValue = startValue

    override val numberOfRolls: Int
        get() = _numberOfRolls

    override fun roll(numberOfRolls: Int): List<Int> {
        var eyes = 0
        repeat(numberOfRolls) {
            lastValue++
            eyes += lastValue
            if (lastValue > sides) {
                lastValue = 1
            }
        }
        _numberOfRolls += numberOfRolls
        return listOf(eyes)
    }

    override fun copy(): DeterministicDie {
        return DeterministicDie(sides, numberOfRolls, lastValue)
    }
}

class QuantumDie(private val sides: Int, private var _numberOfRolls: Int = 0) : Die {

    override val numberOfRolls: Int
        get() = _numberOfRolls

    override fun roll(numberOfRolls: Int): List<Int> {
        val possibleEyes = (1..sides).toList()
        var eyes = possibleEyes.toList()
        repeat(numberOfRolls - 1) {
            eyes = eyes.flatMap { possibleEyes.map { eye -> eye + it } }
        }
        _numberOfRolls += numberOfRolls
        return eyes
    }

    override fun copy(): QuantumDie {
        return QuantumDie(sides, _numberOfRolls)
    }

}

class Game(
    val player1: Player,
    val player2: Player,
    val die: Die,
    private val pointsToWin: Int,
    private val rollsPerTurn: Int = 3
) {

    val didPlayer1Win = player1.score >= pointsToWin
    val didPlayer2Win = player2.score >= pointsToWin
    val isGameOver = didPlayer1Win || didPlayer2Win

    val isPlayer1Turn = !isGameOver && (die.numberOfRolls % (rollsPerTurn * 2)) < rollsPerTurn
    val isPlayer2Turn = !isGameOver && !isPlayer1Turn

    fun playTurn(): List<Game> {
        val possibleEyes = die.roll(rollsPerTurn)
        return if (isPlayer1Turn) {
            getPlayerClones(possibleEyes, player1).map {
                Game(it, player2.copy(), die.copy(), pointsToWin, rollsPerTurn)
            }
        } else if (isPlayer2Turn) {
            getPlayerClones(possibleEyes, player2).map {
                Game(player1.copy(), it, die.copy(), pointsToWin, rollsPerTurn)
            }
        } else {
            emptyList()
        }
    }

    private fun getPlayerClones(possibleEyes: List<Int>, player: Player): List<Player> {
        val playerClones = mutableListOf<Player>()
        for (eyes in possibleEyes) {
            var position = player.position
            position += eyes
            position %= 10
            if (position == 0) position = 10
            playerClones += Player(position, player.score + position)
        }
        return playerClones
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Game) return false
        if (other.player1 != this.player1) return false
        if (other.player2 != this.player2) return false
        if (other.isPlayer1Turn != this.isPlayer1Turn) return false
        if (other.isPlayer2Turn != this.isPlayer2Turn) return false
        return true
    }

    override fun hashCode(): Int {
        return Objects.hash(player1, player2, isPlayer1Turn, isPlayer2Turn)
    }
}
