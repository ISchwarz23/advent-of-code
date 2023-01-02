package utils


fun <T> List<T>.combinations(itemsPerCombination: Int): List<List<T>> {
    if (itemsPerCombination == 1) return chunked(1)
    return flatMapIndexed { index, item ->
        subList(index + 1, size).combinations(itemsPerCombination - 1).map { listOf<T>() + item + it }
    }
}