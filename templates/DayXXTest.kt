package aoc2022

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.AocClient
import utils.DEFAULT_COOKIE_FILE
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class DayXXTest {

    private val aocClient = AocClient()

    private val testInput = readInput("DayXX_test")
    private val input = readInput("DayXX")

    @Test
    internal fun testPart1() {
        // when
        val result = DayXX.part1(testInput)

        // then
        assertEquals(0, result)

        // get solution
        val answer = DayXX.part1(input)
        var submissionResultString = ""
        if(DEFAULT_COOKIE_FILE.exists()) {
            submissionResultString += "(Submission: ${aocClient.submit(2022, XX, 1, answer)})"
        }
        println("Result of Day XX - Part 1: $answer $submissionResultString")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = DayXX.part2(testInput)

        // then
        assertEquals(0, result)

        // get solution
        val answer = DayXX.part2(input)
        var submissionResult = ""
        if(DEFAULT_COOKIE_FILE.exists()) {
            submissionResult += "(Submission: ${aocClient.submit(2022, XX, 2, answer)})"
        }
        println("Result of Day XX - Part 2: $answer $submissionResult")
    }

}