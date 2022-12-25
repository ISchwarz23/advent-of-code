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
    private val tree3d = KdTree(Vector3::x, Vector3::y, Vector3::z, enableBalancing = true)

    @BeforeEach
    internal fun setup() {
        tree2d += Vector2(70, 20)
        tree2d += Vector2(50, 90)
        tree2d += Vector2(80, 10)
        tree2d += Vector2(60, 80)
        tree2d += Vector2(40, 95)
//        println(tree2d)

        tree3d += Vector3(10, 10, 10)
        tree3d += Vector3(20, 20, 20)
        tree3d += Vector3(30, 30, 30)
        tree3d += Vector3(40, 40, 40)
        tree3d += Vector3(50, 50, 50)
//        println(tree3d)
    }

    @AfterEach
    internal fun tearDown() {
        tree2d.clear()
        tree3d.clear()
    }

    @Test
    internal fun shouldGiveCorrectSize() {
        assertFalse(tree2d.isEmpty)
        assertEquals(5, tree2d.size)

        assertFalse(tree3d.isEmpty)
        assertEquals(5, tree3d.size)
    }

    @Test
    internal fun shouldFindClosest() {
        val nearest2d = tree2d.findClosest(Vector2(60, 30))
        assertEquals(Vector2(70, 20), nearest2d)

        val nearest3d = tree3d.findClosest(Vector3(22, 22, 22))
        assertEquals(Vector3(20, 20, 20), nearest3d)
    }

    @Test
    internal fun shouldFindItemsInRange() {
        val items2dInRange = tree2d.findInRange(50.0..80.0, 80.0..90.0)
        assertEquals(listOf(Vector2(50, 90), Vector2(60, 80)), items2dInRange)

        val items3dInRange = tree3d.findInRange(20.0..30.0, 20.0..30.0, 20.0..30.0)
        assertEquals(listOf(Vector3(20, 20, 20), Vector3(30, 30, 30)), items3dInRange)
    }

    @Test
    internal fun shouldCheckIfItemContained() {
        assertTrue(Vector2(50, 90) in tree2d)
        assertFalse(Vector2(50, 80) in tree2d)

        assertTrue(Vector3(30, 30, 30) in tree3d)
        assertFalse(Vector3(33, 33, 33) in tree3d)
    }

}