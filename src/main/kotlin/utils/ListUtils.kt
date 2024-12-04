package utils


fun <T> List<T>.combinations(itemsPerCombination: Int): List<List<T>> {
    if (itemsPerCombination == 1) return chunked(1)
    return flatMapIndexed { index, item ->
        subList(index + 1, size).combinations(itemsPerCombination - 1).map { listOf<T>() + item + it }
    }
}

fun <T> List<T>.split(predicate: (T) -> Boolean): List<List<T>> {
    val idx = this.indexOfFirst(predicate)
    return if (idx == -1) {
        listOf(this)
    } else {
        return listOf(this.take(idx)) + this.drop(idx + 1).split(predicate)
    }
}

fun <E> Iterable<E>.indexesOf(e: E) = mapIndexedNotNull{ index, elem -> index.takeIf{ elem == e } }