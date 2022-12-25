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

    private val tree2d = KdTree(Vector2::x, Vector2::y, enableBalancing = false)
    private val tree3d = KdTree(Vector3::x, Vector3::y, Vector3::z, enableBalancing = false)

    @BeforeEach
    internal fun setup() {
        tree2d += Vector2(70, 20)
        tree2d += Vector2(50, 90)
        tree2d += Vector2(80, 10)
        tree2d += Vector2(60, 80)
        tree2d += Vector2(40, 95)
//        println(tree)
    }

    @AfterEach
    internal fun tearDown() {
        tree2d.clear()
    }

    @Test
    internal fun shouldGiveCorrectSize() {
        assertFalse(tree2d.isEmpty)
        assertEquals(5, tree2d.size)
    }

    @Test
    internal fun shouldFindClosest() {
        val nearest = tree2d.findClosest(Vector2(60, 30))
        assertEquals(Vector2(70, 20), nearest)
    }

    @Test
    internal fun shouldFindItemsInRange() {
        val itemsInRange = tree2d.findInRange(50.0..80.0, 80.0..90.0)
        assertEquals(listOf(Vector2(50, 90), Vector2(60, 80)), itemsInRange)
    }

    @Test
    internal fun shouldCheckIfItemContained() {
        assertTrue(Vector2(50, 90) in tree2d)
        assertFalse(Vector2(50, 80) in tree2d)
    }

}