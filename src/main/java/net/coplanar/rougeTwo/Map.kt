package net.coplanar.rougeTwo

import com.valkryst.VTerminal.Tile
import com.valkryst.VTerminal.component.Layer
import java.awt.Color
import java.awt.Dimension
import java.awt.Point
import java.util.ArrayList




class Map() : Layer(Dimension(80,40)) {
    var mapTiles: Array<Array<MapTile?>>
    var entities: ArrayList<Entity> = ArrayList()

    init {
       // super(Dimension(80, 40))

        val viewWidth = getViewWidth()
        val viewHeight = getViewHeight()


        // Set the Layer to display all tiles as empty and black.
        for (y in 0 until viewHeight) {
            for (x in 0 until viewWidth) {
                val tile = super.tiles.getTileAt(x, y)
                tile.character = ' '
                tile.backgroundColor =Color.BLACK
            }
        }

        // Initialize the MapTiles array.
        mapTiles = Array(viewHeight) { arrayOfNulls<MapTile>(viewWidth) }

        for (y in 0 until viewHeight) {
            for (x in 0 until viewWidth) {
                mapTiles[y][x] = MapTile(Sprite.UNKNOWN)
            }
        }

        //this.updateLayerTiles()
    }

    fun updateLayerTiles() {
        var count = 0
        for (y in 0 until getViewHeight()) {
            for (x in 0 until getViewWidth()) {
                val mapTile = mapTiles[y][x]
                val mapTileSprite = mapTile?.sprite ?: Sprite.UNKNOWN

                val layerTile = super.tiles.getTileAt(x, y)
                layerTile.character = mapTileSprite.character

                if (mapTile?.visible ?: false) {
                    layerTile.backgroundColor = mapTileSprite.backgroundColor
                    layerTile.foregroundColor = mapTileSprite.foregroundColor
                } else if (mapTile?.visited ?: false) {
                    layerTile.backgroundColor = mapTileSprite.darkBackgroundColor
                    layerTile.foregroundColor = mapTileSprite.darkForegroundColor
                } else {
                    layerTile.backgroundColor = mapTileSprite.darkBackgroundColor
                    layerTile.foregroundColor = mapTileSprite.darkForegroundColor
                }
                count ++
            }
        }
       // println("updated " + count)
    }


    fun getMapWidth(): Int {
        return mapTiles.size
    }

    fun getMapHeight(): Int {
        return mapTiles[0].size
    }

    fun getViewWidth(): Int {
        return super.tiles.width
    }

    fun getViewHeight(): Int {
        return super.tiles.height
    }

    fun isPositionFree(position: Point): Boolean {
        var valid = true
        // Ensure position isn't outside the bounds of the map.
        if (    position.x < 0 ||
                position.y < 0 ||
                position.x >= getMapWidth() ||
                position.y >= getMapHeight()
        )
                {
            valid = false
        }

        // Check for entities at the position.
        for (entity in entities) {
            if (entity.position == position) {
                return false
            }
        }

        // Check if the tile at the position is solid.
        if (mapTiles[position.y][position.x]!!.solid ) {
           return false
        }
        return true
    }

}

