package co.q64.stars.util

import net.minecraft.util.math.BlockPos
import kotlin.math.ceil
import kotlin.math.sqrt

const val SPREAD_DISTANCE = 2000
const val SPAWN_HEIGHT = 100

fun getSpawnpoint(index: Int): BlockPos {
    val point = spiral(index)
    val x: Int = point!!.x * SPREAD_DISTANCE
    val y: Int = point.y * SPREAD_DISTANCE
    return BlockPos(x, SPAWN_HEIGHT, y)
}

private fun spiral(index: Int): Point {
    var index = index
    index += 1
    val k = ceil((sqrt(index.toDouble()) - 1) / 2)
    var t = 2 * k + 1
    var m = t * t
    t -= 1
    val x: Double
    val y: Double
    if (index >= m - t) {
        x = k - (m - index)
        y = -k
    } else {
        m -= t
        if (index >= m - t) {
            x = -k
            y = -k + (m - index)
        } else {
            m -= t
            if (index >= m - t) {
                x = -k + (m - index)
                y = k
            } else {
                x = k
                y = k - (m - index - t)
            }
        }
    }
    return Point(x.toInt(), y.toInt())
}

data class Point(val x: Int, val y: Int)