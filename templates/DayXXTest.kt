package aocYYYY

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.AocClient
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class DayXXTest {

    private val aocClient = AocClient()

    private val testInput = readInput("aocYYYY/DayXX_test.txt")
    private val input = readInput("aocYYYY/DayXX.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = DayXX.part1(testInput)

        // then
        assertEquals(1, result)

        // submit answer
        val answer = DayXX.part1(input)
        val submissionResult = aocClient.submit(YYYY, XX, 1, answer)
        println("Result of Day XX - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = DayXX.part2(testInput)

        // then
        assertEquals(1, result)

        // submit answer
        val answer = DayXX.part2(input)
        val submissionResult = aocClient.submit(YYYY, XX, 2, answer)
        println("Result of Day XX - Part 2: $answer (Submission: $submissionResult)")
    }

}