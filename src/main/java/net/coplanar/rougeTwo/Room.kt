package net.coplanar.rougeTwo

import java.awt.Dimension
import java.awt.Point
import javax.swing.Spring.height




class Room {

    /** The coordinates of the top-left tile. */
    private var position = Point(0,0)
    /** The width & height.  */
    private var dimensions = Dimension(0,0)

    constructor(position: Point, dimensions: Dimension) {
        this.position = position
        this.dimensions = dimensions
    }

    fun carve(map: Map) {
        val tiles = map.mapTiles

        for (y in position.y until position.y + dimensions.height) {
            for (x in position.x until position.x + dimensions.width) {
                tiles[y][x]!!.sprite = Sprite.DIRT
                tiles[y][x]!!.solid = false
            }
        }

        //map.updateLayerTiles()
    }

}