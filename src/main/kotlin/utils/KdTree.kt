package utils

import java.util.*
import kotlin.math.max
import kotlin.math.pow
import kotlin.reflect.KProperty1


/**
 * Implementation of a generic K-Dimensional Tree, with (auto) balancing.
 */
class KdTree<T>(private val dimensionExtractors: Array<(T) -> Double>, private val enableBalancing: Boolean = true) {

    /*
    * TODO:
    *   - test with more than 2 dimensions
    *   - removing items
    */

    constructor(vararg dimensionExtractors: KProperty1<T, Number>, enableBalancing: Boolean = true) :
            this(dimensionExtractors.map { extractor -> { input: T -> extractor.get(input).toDouble() } }
                .toTypedArray(), enableBalancing)

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

    // INSERTION

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
        val cmp = comparePoints1D(item, node.item, level)

        val dimensionIndex = level % dimensionExtractors.size
        val dimension = dimensionExtractors[dimensionIndex](node.item)

        val subRanges = nodeRanges.map { it.copy() }.toTypedArray()
        if (cmp < 0) {
            subRanges[dimensionIndex].endInclusive = dimension
            node.leftBranch = insert(node.leftBranch, item, level + 1, subRanges)
        } else if (cmp > 0) {
            subRanges[dimensionIndex].start = dimension
            node.rightBranch = insert(node.rightBranch, item, level + 1, subRanges)
        } else if (node.item != item) {
            node.rightBranch = insert(node.rightBranch, item, level + 1, subRanges)
        }
        return if(enableBalancing) balance(node) else node
    }

    // CONTAINS

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
        if (node.item == itemToLookFor) return true
        val cmp = comparePoints1D(itemToLookFor, node.item, level)

        // Traverse the left path when necessary
        return if (cmp < 0) contains(node.leftBranch, itemToLookFor, level + 1) else contains(
            node.rightBranch,
            itemToLookFor,
            level + 1
        )
    }

    // FIND IN RANGE

    /**
     * All items that are inside the given ranges.
     * First range will be interpreted as range of dimension 1, second range as range of dimension 2 and so on.
     *
     * @param ranges The ranges of the
     * @return an iterator to all the points within the given RectHV
     */
    fun findInRange(vararg ranges: ClosedFloatingPointRange<Double>): List<T> {
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
            val pointN = extractPointN(tmp!!.item)
            if (range.contains(pointN)) {
                points.add(tmp.item)
            }
            if (tmp.leftBranch != null && range.intersects(tmp.leftBranch!!.rect)) {
                nodes.push(tmp.leftBranch)
            }
            if (tmp.rightBranch != null && range.intersects(tmp.rightBranch!!.rect)) {
                nodes.push(tmp.rightBranch)
            }
        }
        return points
    }

    // FIND CLOSEST

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
        return if (isEmpty) null else findClosest(root, item, root!!.item, 0)
    }

    private fun findClosest(node: Node<T>?, itemToLookFor: T, bestMatch: T, level: Int): T {

        // Handle reaching the end of the tree
        var bestMatchSoFar = bestMatch
        if (node == null) return bestMatchSoFar

        // Handle the given point exactly overlapping a point in the BST
        if (node.item == itemToLookFor) return itemToLookFor

        // Determine if the current Node's point beats the existing champion
        val currentPointN = extractPointN(itemToLookFor)
        val bestPointN = extractPointN(bestMatchSoFar)
        val nodePointN = extractPointN(node.item)
        if (nodePointN.distanceSquaredTo(currentPointN) < bestPointN.distanceSquaredTo(currentPointN))
            bestMatchSoFar = node.item

        // Calculate the distance from the search point to the current node's partition line.
        val toPartitionLine = comparePoints1D(itemToLookFor, node.item, level)
        if (toPartitionLine < 0) {
            bestMatchSoFar = findClosest(node.leftBranch, itemToLookFor, bestMatchSoFar, level + 1)
            val bestPointN = extractPointN(bestMatchSoFar)

            // Since champion may have changed, recalculate distance
            if (bestPointN.distanceSquaredTo(currentPointN) >=
                toPartitionLine * toPartitionLine
            ) {
                bestMatchSoFar = findClosest(node.rightBranch, itemToLookFor, bestMatchSoFar, level + 1)
            }
        } else {
            bestMatchSoFar = findClosest(node.rightBranch, itemToLookFor, bestMatchSoFar, level + 1)
            val championPointN = extractPointN(bestMatchSoFar)

            // Since champion may have changed, recalculate distance
            if (championPointN.distanceSquaredTo(currentPointN) >=
                toPartitionLine * toPartitionLine
            ) {
                bestMatchSoFar = findClosest(node.leftBranch, itemToLookFor, bestMatchSoFar, level + 1)
            }
        }
        return bestMatchSoFar
    }

    // BALANCING

    private fun balance(node: Node<T>): Node<T> {
        return if (node.balanceFactor >= 2) {
            if (node.leftBranch?.balanceFactor == -1) {
                rotateLeftRight(node)
            } else {
                rotateRight(node)
            }
        } else if (node.balanceFactor <= -2) {
            if (node.rightBranch?.balanceFactor == 1) {
                rotateRightLeft(node)
            } else {
                rotateLeft(node)
            }
        } else
            node
    }

    private fun rotateLeft(node: Node<T>): Node<T> {
        val pivot = node.rightBranch!!
        node.rightBranch = pivot.leftBranch
        pivot.leftBranch = node
        return pivot
    }

    private fun rotateRight(node: Node<T>): Node<T> {
        val pivot = node.leftBranch!!
        node.leftBranch = pivot.rightBranch
        pivot.rightBranch = node
        return pivot
    }

    private fun rotateRightLeft(node: Node<T>): Node<T> {
        val rightChild = node.rightBranch ?: return node
        node.rightBranch = rotateRight(rightChild)
        return rotateLeft(node)
    }

    private fun rotateLeftRight(node: Node<T>): Node<T> {
        val leftChild = node.leftBranch ?: return node
        node.leftBranch = rotateLeft(leftChild)
        return rotateRight(node)
    }

// UTILS

    private fun comparePoints1D(first: T, second: T, level: Int): Double {
        return extractDimension(first, level) - extractDimension(second, level)
    }

    private fun extractPointN(data: T): PointN {
        return PointN(dimensionExtractors.map { it(data) }.toDoubleArray())
    }

    private fun extractDimension(data: T, level: Int): Double {
        return dimensionExtractors[level % dimensionExtractors.size](data)
    }

    override fun toString() = diagram(this.root)

    private fun diagram(
        node: Node<T>?,
        top: String = "",
        root: String = "",
        bottom: String = ""
    ): String {
        return node?.let {
            if (node.leftBranch == null && node.rightBranch == null) {
                "$root${node.item}\n"
            } else {
                diagram(node.rightBranch, "$top ", "$top┌──", "$top│ ") +
                        root + "${node.item}\n" + diagram(node.leftBranch, "$bottom│ ", "$bottom└──", "$bottom ")
            }
        } ?: "${root}null\n"
    }

// SUB-CONSTRUCTS

    private class Node<T>(val item: T, val dimensionRanges: Array<MutableDoubleRange>) {
        // the axis-aligned rectangle corresponding to this node
        val rect: RangeN = RangeN(dimensionRanges as Array<ClosedFloatingPointRange<Double>>)

        // subtree heights
        val leftBranchHeight: Int
            get() = leftBranch?.height ?: -1
        val rightBranchHeight: Int
            get() = rightBranch?.height ?: -1

        // height of the tree node
        val height: Int
            get() = max(leftBranchHeight, rightBranchHeight) + 1

        // balance factor
        val balanceFactor: Int
            get() = leftBranchHeight - rightBranchHeight

        // the left/bottom subtree
        var leftBranch: Node<T>? = null

        // the right/top subtree
        var rightBranch: Node<T>? = null
    }

    private class PointN(val dimensionValue: DoubleArray) {

        val numberOfDimensions = dimensionValue.size

        fun distanceSquaredTo(other: PointN): Double {
            if (numberOfDimensions != other.numberOfDimensions) error("Points have different number of dimensions")
            return dimensionValue.zip(other.dimensionValue).sumOf { (it.first - it.second).pow(2) }
        }

    }

    private class RangeN(val dimensionRanges: Array<ClosedFloatingPointRange<Double>>) {

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
