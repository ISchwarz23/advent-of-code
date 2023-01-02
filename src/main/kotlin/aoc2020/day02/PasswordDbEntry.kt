package aoc2020.day02

class PasswordDbEntry(countRange: IntRange, letter: Char, password: String) {

    val isValidByRange: Boolean by lazy {
        password.count { it == letter } in countRange
    }

    val isValidByIndex: Boolean by lazy {
        (password[countRange.first - 1] == letter) xor (password[countRange.last - 1] == letter)
    }

    companion object {

        fun of(input: String): PasswordDbEntry {
            val parts = input.split(" ")
            val count = parts[0].split("-").take(2).map { it.toInt() }
            val letter = parts[1][0]
            val password = parts[2]
            return PasswordDbEntry(count[0]..count[1], letter, password)
        }

    }

}