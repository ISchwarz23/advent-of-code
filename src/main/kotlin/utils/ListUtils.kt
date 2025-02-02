package utils


fun <T> List<T>.split(predicate: (T) -> Boolean): List<List<T>> {
    val idx = this.indexOfFirst(predicate)
    return if (idx == -1) {
        listOf(this)
    } else {
        return listOf(this.take(idx)) + this.drop(idx + 1).split(predicate)
    }
}

fun <T> List<T>.permutations(): List<List<T>> {
    if (isEmpty()) return emptyList()

    fun <T> _allPermutations(list: List<T>): List<List<T>> {
        if (list.isEmpty()) return listOf(emptyList())

        val result = mutableListOf<List<T>>()
        for (i in list.indices) {
            _allPermutations(list - list[i]).forEach {
                    item -> result.add(item + list[i])
            }
        }
        return result
    }

    return _allPermutations(this).distinct()
}

fun <T, U> List<T>.combinationsWith(other: List<U>): List<Pair<T, U>> {
    return combinationsWithAsSequence(other).toList()
}

fun <T, U> List<T>.combinationsWithAsSequence(other: List<U>): Sequence<Pair<T, U>> {
    var indexThis = 0
    var indexOther = -1
    return generateSequence {
        indexOther++
        if (indexOther > other.lastIndex) {
            indexOther = 0
            indexThis++
        }
        if (indexThis > this.lastIndex) {
            return@generateSequence null
        }

        Pair(this[indexThis], other[indexOther])
    }
}

fun <T> List<T>.combinations(itemsPerCombination: Int): List<List<T>> {
    return this.combinationsAsSequence(itemsPerCombination).toList()
}

fun <T> List<T>.combinationsAsSequence(itemsPerCombination: Int): Sequence<List<T>> {
    if(itemsPerCombination <= 0) {
        throw Error("The permutations need to contain at least one item (requested items per combination: $itemsPerCombination).")
    }
    if(itemsPerCombination > size) {
        throw Error("The permutations need to be shorter than the list itself (items in list: $size, requested items per combination: $itemsPerCombination).")
    }

    val indexes = MutableList(itemsPerCombination) { it }
    indexes[indexes.lastIndex]--
    return generateSequence {
        var indexOfIndexToIncrement = indexes.size
        do {
            indexOfIndexToIncrement-- // go to next index
            indexes[indexOfIndexToIncrement] += 1 // increase index
            (indexOfIndexToIncrement until indexes.size - 1).forEach {
                indexes[it + 1] = indexes[it] + 1
            }
        } while (indexOfIndexToIncrement > 0 && indexes[indexOfIndexToIncrement] > (size - (indexes.size - indexOfIndexToIncrement)))

        if (indexes.first() > size - indexes.size) {
            return@generateSequence null
        } else {
            return@generateSequence indexes.map { this[it] }
        }
    }
}

fun <E> Iterable<E>.indexesOf(e: E) = mapIndexedNotNull{ index, elem -> index.takeIf{ elem == e } }