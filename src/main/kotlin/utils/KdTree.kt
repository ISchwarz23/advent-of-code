package utils

import java.util.*
import kotlin.math.pow
import kotlin.reflect.KProperty1


/**
 * Implementation of a generic K-Dimensional Tree, with (auto) re-balancing.
 */
class KdTree<T>(val dimensionExtractors: Array<(T) -> Double>) {

    /*
    * TODO:
    *   - add (auto) re-balancing
    */

    constructor(vararg dimensionExtractors: KProperty1<T, Number>) :
            this(dimensionExtractors.map { extractor -> { input: T -> extractor.get(input).toDouble() } }
                .toTypedArray())

    private var root: Node<T>? = null
    private var _size = 0
    private val numberOfDimensions = dimensionExtractors.size

    val isEmpty: Boolean
        get() = root == null

    val size: Int
        get() = _size

    fun clear() {
        this.root = null
        this._size = 0
    }

    operator fun plusAssign(item: T) {
        insert(item)
    }

    /**
     * Adds the item to the tree (if it is not already included).
     *
     * @param item the item to add
     * @return true if added successfully, false if already inside tree
     */
    fun insert(item: T): Boolean {
        val oldSize = _size
        root = insert(
            root,
            item,
            0,
            Array(numberOfDimensions) { MutableDoubleRange(Double.MIN_VALUE, Double.MAX_VALUE) })
        return oldSize < size
    }

    private fun insert(node: Node<T>?, item: T, level: Int, nodeRanges: Array<MutableDoubleRange>): Node<T> {
        if (node == null) {
            _size++
            return Node(item, nodeRanges)
        }
        val cmp = comparePoints1D(item, node.p, level)

        val dimensionIndex = level % dimensionExtractors.size
        val dimension = dimensionExtractors[dimensionIndex](node.p)

        val subRanges = nodeRanges.map { it.copy() }.toTypedArray()
        if (cmp < 0) {
            subRanges[dimensionIndex].endInclusive = dimension
            node.lb = insert(node.lb, item, level + 1, subRanges)
        } else if (cmp > 0) {
            subRanges[dimensionIndex].start = dimension
            node.rt = insert(node.rt, item, level + 1, subRanges)
        } else if (node.p != item) {
            node.rt = insert(node.rt, item, level + 1, subRanges)
        }
        return node
    }

    /**
     * Checks if the given item is part of the tree. (Only position is checked)
     *
     * @return boolean indicating if item is part of the tree or not.
     */
    operator fun contains(item: T): Boolean {
        return contains(root, item, 0)
    }

    private fun contains(node: Node<T>?, itemToLookFor: T, level: Int): Boolean {

        // Handle reaching the end of the search
        if (node == null) return false

        // Check whether the search point matches the current Node's point
        if (node.p == itemToLookFor) return true
        val cmp = comparePoints1D(itemToLookFor, node.p, level)

        // Traverse the left path when necessary
        return if (cmp < 0) contains(node.lb, itemToLookFor, level + 1) else contains(node.rt, itemToLookFor, level + 1)
    }

    /**
     * All items that are inside the given ranges.
     * First range will be interpreted as range of dimension 1, second range as range of dimension 2 and so on.
     *
     * @param ranges The ranges of the
     * @return an iterator to all the points within the given RectHV
     */
    fun findInRange(vararg ranges: ClosedRange<Double>): List<T> {
        val points = mutableListOf<T>()
        val range = RangeN(arrayOf(*ranges))

        if (range.numberOfDimensions != numberOfDimensions) error("Number of ranges has to match the dimension of the tree")

        // Handle KdTree without a root node yet
        if (root == null) return points
        val nodes = Stack<Node<T>?>()
        nodes.push(root)
        while (!nodes.isEmpty()) {
            // Examine the next Node
            val tmp = nodes.pop()

            // Add contained points to our points stack
            val pointN = extractPointN(tmp!!.p)
            if (range.contains(pointN)) {
                points.add(tmp.p)
            }
            if (tmp.lb != null && range.intersects(tmp.lb!!.rect)) {
                nodes.push(tmp.lb)
            }
            if (tmp.rt != null && range.intersects(tmp.rt!!.rect)) {
                nodes.push(tmp.rt)
            }
        }
        return points
    }

    /**
     * Finds the closest item to the given item.
     *
     * In the worst case, this implementation takes time
     * proportional to the number of points in the set.
     *
     * @param item the point from which to search for a neighbor
     * @return the nearest neighbor to the given item p, `null` otherwise.
     */
    fun findClosest(item: T): T? {
        return if (isEmpty) null else findClosest(root, item, root!!.p, 0)
    }

    private fun findClosest(node: Node<T>?, itemToLookFor: T, bestMatch: T, level: Int): T {

        // Handle reaching the end of the tree
        var bestMatchSoFar = bestMatch
        if (node == null) return bestMatchSoFar

        // Handle the given point exactly overlapping a point in the BST
        if (node.p == itemToLookFor) return itemToLookFor

        // Determine if the current Node's point beats the existing champion
        val currentPointN = extractPointN(itemToLookFor)
        val bestPointN = extractPointN(bestMatchSoFar)
        val nodePointN = extractPointN(node.p)
        if (nodePointN.distanceSquaredTo(currentPointN) < bestPointN.distanceSquaredTo(currentPointN))
            bestMatchSoFar = node.p

        // Calculate the distance from the search point to the current node's partition line.
        val toPartitionLine = comparePoints1D(itemToLookFor, node.p, level)
        if (toPartitionLine < 0) {
            bestMatchSoFar = findClosest(node.lb, itemToLookFor, bestMatchSoFar, level + 1)
            val championPointN = extractPointN(bestMatchSoFar)

            // Since champion may have changed, recalculate distance
            if (championPointN.distanceSquaredTo(currentPointN) >=
                toPartitionLine * toPartitionLine
            ) {
                bestMatchSoFar = findClosest(node.rt, itemToLookFor, bestMatchSoFar, level + 1)
            }
        } else {
            bestMatchSoFar = findClosest(node.rt, itemToLookFor, bestMatchSoFar, level + 1)
            val championPointN = extractPointN(bestMatchSoFar)

            // Since champion may have changed, recalculate distance
            if (championPointN.distanceSquaredTo(currentPointN) >=
                toPartitionLine * toPartitionLine
            ) {
                bestMatchSoFar = findClosest(node.lb, itemToLookFor, bestMatchSoFar, level + 1)
            }
        }
        return bestMatchSoFar
    }

    private fun comparePoints1D(first: T, second: T, level: Int): Double {
        return extractDimension(first, level) - extractDimension(second, level)
    }

    private fun extractPointN(data: T): PointN {
        return PointN(dimensionExtractors.map { it(data) }.toDoubleArray())
    }

    private fun extractDimension(data: T, level: Int): Double {
        return dimensionExtractors[level % dimensionExtractors.size](data)
    }

    private data class Node<T>(val p: T, val dimensionRanges: Array<MutableDoubleRange>) {
        // the axis-aligned rectangle corresponding to this node
        val rect: RangeN = RangeN(dimensionRanges as Array<ClosedRange<Double>>)

        // the left/bottom subtree
        var lb: Node<T>? = null

        // the right/top subtree
        var rt: Node<T>? = null
    }

    private data class PointN(val dimensionValue: DoubleArray) {

        val numberOfDimensions = dimensionValue.size

        fun distanceSquaredTo(other: PointN): Double {
            if (numberOfDimensions != other.numberOfDimensions) error("Points have different number of dimensions")
            return dimensionValue.zip(other.dimensionValue).sumOf { (it.first - it.second).pow(2) }
        }

    }

    private data class RangeN(val dimensionRanges: Array<ClosedRange<Double>>) {

        val numberOfDimensions = dimensionRanges.size

        fun intersects(that: RangeN): Boolean {
            if (that.numberOfDimensions != this.numberOfDimensions) error("RangeN does have differing number of dimension")
            for (i in 0 until this.numberOfDimensions) {
                if (!(this.dimensionRanges[i].endInclusive >= that.dimensionRanges[i].start && that.dimensionRanges[i].endInclusive >= this.dimensionRanges[i].start)) {
                    return false
                }
            }
            return true
        }

        fun contains(point: PointN): Boolean {
            if (point.numberOfDimensions != this.numberOfDimensions) error("RangeN and PointN have differing number of dimension")
            return dimensionRanges.mapIndexed { index, range -> range.contains(point.dimensionValue[index]) }.all { it }
        }

    }

}
