package aoc2022.day02

object Day02 {

    fun part1(input: List<String>): Int {
        return input.map { it.split(" ") }.sumOf { getPointsDependingOnHand(Hand.from(it[0]), Hand.from(it[1])) }
    }

    fun part2(input: List<String>): Int {
        return input.map { it.split(" ") }.sumOf { getPointsDependingOnResult(Hand.from(it[0]), Result.from(it[1])) }
    }
}


private fun getPointsDependingOnHand(opponentHand: Hand, myHand: Hand): Int {
    val result = myHand.playAgainst(opponentHand)
    return myHand.points + result.points
}

private fun getPointsDependingOnResult(opponentHand: Hand, result: Result): Int {
    val myHand = opponentHand.getHandToAchieveResult(result)
    return myHand.points + result.points
}
