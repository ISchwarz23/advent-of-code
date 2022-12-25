package utils

fun create2dTree(enableBalancing: Boolean = true): KdTree<Vector2> =
    KdTree(Vector2::x, Vector2::y, enableBalancing = enableBalancing)

fun create3dTree(enableBalancing: Boolean = true): KdTree<Vector3> =
    KdTree(Vector3::x, Vector3::y, Vector3::z, enableBalancing = enableBalancing)