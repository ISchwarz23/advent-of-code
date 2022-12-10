package utils

import java.io.File
import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

val DEFAULT_COOKIE_FILE = File("session.txt")

class AocClient(cookieFile: File = DEFAULT_COOKIE_FILE) {

    private val cookies = if (cookieFile.exists()) cookieFile.readLines()[0] else ""
    private val client = HttpClient.newBuilder().build()

    fun submit(year: Int, day: Int, level: Int, answer: Long): String = submit(year, day, level, "$answer")

    fun submit(year: Int, day: Int, level: Int, answer: Int): String = submit(year, day, level, answer.toLong())

    fun submit(year: Int, day: Int, level: Int, answer: String): String {
        val values = mapOf("level" to "$level", "answer" to answer)

        val request = HttpRequest.newBuilder()
            .uri(URI.create("https://adventofcode.com/$year/day/$day/answer"))
            .header("cookie", cookies)
            .POST(toFormData(values))
            .header("Content-Type", "application/x-www-form-urlencoded")
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        return if(response.statusCode() in 200..299) {
            val body = response.body().toString()
            if(body.contains("That's not the right answer.")) "Wrong answer" else "Correct answer"
        } else {
            "Unable to submit answer"
        }
    }

    private fun toFormData(data: Map<String, String>): HttpRequest.BodyPublisher? {
        val res = data.map {(k, v) -> "${(k.utf8())}=${v.utf8()}"}.joinToString("&")
        return HttpRequest.BodyPublishers.ofString(res)
    }

    private fun String.utf8(): String = URLEncoder.encode(this, "UTF-8")

}