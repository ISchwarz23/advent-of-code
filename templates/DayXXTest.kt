package aocYYYY

import aocYYYY.dayXX.DayXX
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import utils.aocClient
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(
    MethodOrderer.Alphanumeric::class
)
internal class DayXXTest {

    private val aocClient = AocClient()

    private val input = readInput("aocYYYY/dayXX.txt")
    private val inputExample = readInput("aocYYYY/dayXX_example.txt")

    @Test
    internal fun testPart1() {
        // when
        val result = DayXX.part1(inputExample)

        // then
        assertEquals(0, result)

        // submit answer
        val answer = DayXX.part1(input)
        val submissionResult = aocClient.submit(YYYY, XX, 1, answer)
        println("Result of Day XX - Part 1: $answer (Submission: $submissionResult)")
    }

    @Test
    internal fun testPart2() {
        // when
        val result = DayXX.part2(inputExample)

        // then
        assertEquals(0, result)

        // submit answer
        val answer = DayXX.part2(input)
        val submissionResult = aocClient.submit(YYYY, XX, 2, answer)
        println("Result of Day XX - Part 2: $answer (Submission: $submissionResult)")
    }

}