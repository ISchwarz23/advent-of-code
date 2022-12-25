package utils

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class KdTreeTest {

    //            (*70*, 20)
    //              /    \
    //      (50, *90*)   (80, *10*)
    //            /            \
    //     (60, 80)            (40, 95)

    private val tree = KdTree(Vector2::x, Vector2::y)

    @BeforeEach
    internal fun setup() {
        tree += Vector2(70, 20)
        tree += Vector2(50, 90)
        tree += Vector2(80, 10)
        tree += Vector2(60, 80)
        tree += Vector2(40, 95)
    }

    @AfterEach
    internal fun tearDown() {
        tree.clear()
    }

    @Test
    internal fun shouldGiveCorrectSize() {
        assertFalse(tree.isEmpty)
        assertEquals(5, tree.size)
    }

    @Test
    internal fun shouldFindClosest() {
        val nearest = tree.findClosest(Vector2(60, 30))
        assertEquals(Vector2(70, 20), nearest)
    }

    @Test
    internal fun shouldFindItemsInRange() {
        val itemsInRange = tree.findInRange(50.0..80.0, 80.0..90.0)
        assertEquals(listOf(Vector2(50, 90), Vector2(60, 80)), itemsInRange)
    }

    @Test
    internal fun shouldCheckIfItemContained() {
        assertTrue(Vector2(50, 90) in tree)
        assertFalse(Vector2(50, 80) in tree)
    }

}