package net.coplanar.rougeTwo

import java.awt.Point
import com.valkryst.VTerminal.misc.ShapeAlgorithms
import java.awt.Dimension
import jdk.nashorn.internal.objects.NativeArray.forEach





class LineOfSight(radius: Int, centerPoint: Point) {
    var visibleLines: ArrayList<List<Point>> = ArrayList()
    var radius: Int

    init {
        this.radius =
        if (radius > 0) {
            radius
        } else 1

        /*
         * Retrieve the circumference points, then calculate the line of points that leads from the center
         * point to each edge point. These are the visible points of the LOS.
         */
        val circumferencePoints = ShapeAlgorithms.getEllipse(centerPoint, Dimension(radius, radius))

        for (point in circumferencePoints) {
            visibleLines.add(ShapeAlgorithms.getLine(centerPoint.x, centerPoint.y, point.x, point.y))
        }
    }

    fun move(dx: Int, dy: Int) {
        visibleLines.forEach { list ->
            list.forEach { point ->
                point.x += dx
                point.y += dy
            }
        }
    }

    fun hideLOSOnMap(map: Map) {
        val tiles = map.mapTiles

        for (line in visibleLines) {
            for (point in line) {
                tiles[point.y][point.x]!!.visible = false
            }
        }
    }

    fun showLOSOnMap(map: Map) {
        val tiles = map.mapTiles

        for (line in visibleLines) {
            for (point in line) {
                val tile = tiles[point.y][point.x]!!
                tile.visible = true
                tile.visited = true

                // If we hit a solid tile, then the rest of the line cannot be visible.
                if (tile.solid) {
                    break
                }
            }
        }
    }

    fun isInLOS(point: Point): Boolean {
        for (line in visibleLines) {
            if (line.contains(point)) {
                return true
            }
        }
        return false
    }
}