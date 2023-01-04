package aoc2020.day04

data class Passport(private val properties: Map<String, String>) {

    val fieldNames: Set<String>
        get() = properties.keys

    fun getFieldValue(fieldName: String): String? {
        return properties[fieldName]
    }

    companion object {

        fun from(input: List<String>): Passport {
            val fields = input.flatMap { sublist -> sublist.split(" ") }
                .map { entry -> entry.split(":") }
                .associate { entryParts -> entryParts[0] to entryParts[1] }
            return Passport(fields)
        }

    }

}